/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     vpasquier <vpasquier@nuxeo.com>
 */
package com.nuxeo.box.api.comment;

import com.nuxeo.box.api.comment.adapter.BoxCommentAdapter;
import com.nuxeo.box.api.dao.BoxComment;
import com.nuxeo.box.api.exceptions.BoxJSONException;
import com.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.platform.comment.api.CommentManager;
import org.nuxeo.ecm.platform.comment.workflow.utils.CommentsConstants;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * WebObject for a Box Comment
 *
 * @since 5.9.3
 */
@WebObject(type = "comment")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxCommentObject extends AbstractResource<ResourceTypeImpl> {

    BoxService boxService;

    CommentManager commentManager;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
        commentManager = Framework.getLocalService(CommentManager.class);
    }

    @GET
    public Object doGet() {
        return getView("index");
    }

    @GET
    @Path("{commentId}")
    public String doGetComment(@PathParam("commentId")
    final String commentId) throws NoSuchDocumentException, ClientException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        final DocumentModel comment = session.getDocument(new IdRef(commentId));
        // Adapt nx document to box comment adapter
        final BoxCommentAdapter commentAdapter = comment.getAdapter
                (BoxCommentAdapter.class);
        return boxService.toJSONString(commentAdapter.getBoxComment());
    }

    @POST
    public String doPostComment(String jsonBoxComment) throws
            NoSuchDocumentException, ClientException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        BoxComment boxComment = boxService.getBoxComment(jsonBoxComment);
        // Fetch the target
        DocumentModel target = session.getDocument(new IdRef(boxComment
                .getItem().getId()));
        // Create the nx document from box comment information
        DocumentModel comment = session.createDocumentModel
                (CommentsConstants.COMMENT_DOC_TYPE);
        comment.setProperty("comment", "text", boxComment.getMessage());
        comment.setProperty("comment", "author", boxComment.getCreatedBy()
                .getName());
        comment.setProperty("comment", "creationDate",
                boxComment.getCreatedAt());
        // create the comment
        DocumentModel newComment = commentManager.createComment(target,
                comment);
        final BoxCommentAdapter commentAdapter = newComment.getAdapter
                (BoxCommentAdapter.class);
        return boxService.toJSONString(commentAdapter.getBoxComment());
    }

    @PUT
    @Path("{commentId}")
    public String doPutFolder(@PathParam("commentId") String commentId,
            String jsonBoxComment) throws ClientException, BoxJSONException,
            ParseException, IllegalAccessException, InvocationTargetException {
        final CoreSession session = ctx.getCoreSession();
        // Fetch the nx document with given id
        final DocumentModel nxDocument = session.getDocument(new IdRef
                (commentId));
        // Create box folder from json payload
        BoxComment boxCommentUpdated = boxService.getBoxComment(jsonBoxComment);
        // Adapt nx document to box folder adapter
        final BoxCommentAdapter nxDocumentAdapter = nxDocument.getAdapter
                (BoxCommentAdapter.class);
        // Update both nx document and box folder adapter
//        nxDocumentAdapter.setBoxItem(boxCommentUpdated);
//        nxDocumentAdapter.save(session);
//        // Return the new box folder json
//        return boxService.toJSONString(nxDocumentAdapter.getBoxItem());
        return null;
    }
}
