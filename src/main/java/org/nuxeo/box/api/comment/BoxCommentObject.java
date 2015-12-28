/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     vpasquier <vpasquier@nuxeo.com>
 */
package org.nuxeo.box.api.comment;

import org.nuxeo.box.api.adapter.BoxAdapter;
import org.nuxeo.box.api.comment.adapter.BoxCommentAdapter;
import org.nuxeo.box.api.file.adapter.BoxFileAdapter;
import org.nuxeo.box.api.marshalling.dao.BoxComment;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.marshalling.exceptions.BoxRestException;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentNotFoundException;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.platform.comment.api.CommentableDocument;
import org.nuxeo.ecm.platform.comment.workflow.utils.CommentsConstants;
import org.nuxeo.ecm.webengine.WebException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;

/**
 * WebObject for a Box Comment
 *
 * @since 5.9.3
 */
@WebObject(type = "comment")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxCommentObject extends AbstractResource<ResourceTypeImpl> {

    BoxService boxService;

    BoxFileAdapter boxFile;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
        if (args != null && args.length == 1) {
            try {
                String fileId = (String) args[0];
                CoreSession session = ctx.getCoreSession();
                DocumentModel file = session.getDocument(new IdRef(fileId));
                boxFile = (BoxFileAdapter) file.getAdapter(BoxAdapter.class);
            } catch (NuxeoException e) {
                throw WebException.wrap(e);
            }
            setRoot(true);
        }
    }

    @GET
    @Path("{commentId}")
    public String doGetComment(@PathParam("commentId") final String commentId) throws DocumentNotFoundException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        final DocumentModel comment = session.getDocument(new IdRef(commentId));
        // Adapt nx document to box comment adapter
        final BoxCommentAdapter commentAdapter = comment.getAdapter(BoxCommentAdapter.class);
        return boxService.toJSONString(commentAdapter.getBoxComment());
    }

    @POST
    public String doPostComment(String jsonBoxComment) throws DocumentNotFoundException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        BoxComment boxComment = boxService.getBoxComment(jsonBoxComment);
        // Fetch the target
        DocumentModel target = session.getDocument(new IdRef(boxComment.getItem().getId()));
        // Create the nx document from box comment information
        DocumentModel comment = session.createDocumentModel(CommentsConstants.COMMENT_DOC_TYPE);
        comment.setProperty("comment", "text", boxComment.getMessage());
        comment.setProperty("comment", "author", session.getPrincipal().getName());
        comment.setProperty("comment", "creationDate", new Date());
        // create the comment
        CommentableDocument commentableDocument = target.getAdapter(CommentableDocument.class);
        if (commentableDocument == null) {
            throw new BoxRestException("This document cannot be commented",
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        DocumentModel newComment = commentableDocument.addComment(comment);
        newComment.attach(session.getSessionId());
        final BoxCommentAdapter commentAdapter = newComment.getAdapter(BoxCommentAdapter.class);
        return boxService.toJSONString(commentAdapter.getBoxComment());
    }

    @PUT
    @Path("{commentId}")
    public String doPutComment(@PathParam("commentId") String commentId, String jsonBoxComment) throws
            BoxJSONException, ParseException, IllegalAccessException, InvocationTargetException,
            DocumentNotFoundException {
        final CoreSession session = ctx.getCoreSession();
        // Fetch the nx document comment
        final DocumentModel nxComment = session.getDocument(new IdRef(commentId));
        // Create box comment from json payload
        BoxComment boxCommentUpdated = boxService.getBoxComment(jsonBoxComment);
        // Adapt nx document to box comment adapter
        final BoxCommentAdapter nxDocumentAdapter = nxComment.getAdapter(BoxCommentAdapter.class);
        // Update both nx document and box comment adapter
        nxDocumentAdapter.setBoxComment(boxCommentUpdated);
        nxDocumentAdapter.save(session);
        // Return the new box comment json
        return boxService.toJSONString(nxDocumentAdapter.getBoxComment());
    }

    @DELETE
    @Path("{commentId}")
    public void doDeleteComment(@PathParam("commentId") String commentId) {
        final CoreSession session = ctx.getCoreSession();
        session.removeDocument(new IdRef(commentId));
        session.save();
    }

    @GET
    public String doGetComments() throws DocumentNotFoundException, BoxJSONException {
        return boxService.toJSONString(boxFile.getComments());
    }
}
