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
 *     Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package com.nuxeo.box.api.comment.adapter;

import com.nuxeo.box.api.marshalling.dao.BoxComment;
import com.nuxeo.box.api.marshalling.dao.BoxFile;
import com.nuxeo.box.api.marshalling.dao.BoxItem;
import com.nuxeo.box.api.marshalling.dao.BoxTypedObject;
import com.nuxeo.box.api.marshalling.dao.BoxUser;
import com.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import com.nuxeo.box.api.service.BoxService;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.comment.api.CommentManager;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Box Comment Adapter
 *
 * @since 5.9.3
 */
public class BoxCommentAdapter {

    protected BoxComment boxComment;

    protected final Map<String, Object> boxProperties = new HashMap<>();

    protected DocumentModel comment;

    /**
     * Instantiate the adapter and the Box Comment from Nuxeo Document and
     * load its properties into json format
     */
    public BoxCommentAdapter(DocumentModel doc) throws ClientException {
        BoxService boxService = Framework.getLocalService(BoxService.class);

        comment = doc;

        boxProperties.put(BoxComment.FIELD_ID, doc.getId());
        boxProperties.put(BoxComment.FIELD_CREATED_AT,
                ISODateTimeFormat.dateTime().print(
                        new DateTime(doc.getPropertyValue
                                ("comment:creationDate"))));

        // Nuxeo comment doesn't provide modified date
        boxProperties.put(BoxComment.FIELD_MODIFIED_AT, null);

        // Comment Author
        final UserManager userManager = Framework.getLocalService(UserManager
                .class);
        final NuxeoPrincipal creator = userManager.getPrincipal((String) doc
                .getPropertyValue("comment:author"));
        final BoxUser boxCreator = boxService.fillUser(creator);
        boxProperties.put(BoxComment.FIELD_CREATED_BY, boxCreator);

        boxProperties.put(BoxComment.FIELD_MESSAGE,
                doc.getPropertyValue("comment:text"));
        boxProperties.put(BoxComment.FIELD_IS_REPLY_COMMENT, null);
        boxProperties.put(BoxComment.FIELD_ITEM, fillItem(doc));
        boxComment = new BoxComment(boxProperties);
    }

    public void setBoxComment(BoxComment boxComment) {
        for (String field : boxComment.getKeySet()) {
            this.boxComment.put(field, boxComment.getValue(field));
        }
    }

    private BoxTypedObject fillItem(DocumentModel doc) throws
            ClientException {
        CommentManager commentManager = Framework.getLocalService
                (CommentManager.class);
        List<DocumentModel> targetList = commentManager
                .getDocumentsForComment(doc);
        if (targetList.isEmpty()) {
//            throw new WebResourceNotFoundException("Cannot find any document " +
//                    "bound to the comment " + doc.getId());
            return null;
        }
        DocumentModel target = targetList.get(0);
        Map<String, Object> itemProperties = new HashMap<>();
        itemProperties.put(BoxItem.FIELD_ID, target.getId());
        BoxTypedObject boxItem = new BoxFile(itemProperties);
        return boxItem;
    }

    /**
     * Update the comment (nx/box sides)
     */
    public void save(CoreSession session) throws ClientException,
            ParseException, InvocationTargetException,
            IllegalAccessException, BoxJSONException {
        comment.setPropertyValue("comment:text", boxComment.getMessage());
        session.saveDocument(comment);
        session.save();
    }

    public BoxComment getBoxComment() {
        return boxComment;
    }

}