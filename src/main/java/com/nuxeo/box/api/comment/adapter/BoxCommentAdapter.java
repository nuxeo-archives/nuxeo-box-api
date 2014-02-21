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

import com.nuxeo.box.api.dao.BoxComment;
import com.nuxeo.box.api.dao.BoxFile;
import com.nuxeo.box.api.dao.BoxFolder;
import com.nuxeo.box.api.dao.BoxTypedObject;
import com.nuxeo.box.api.dao.BoxUser;
import com.nuxeo.box.api.service.BoxService;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.comment.api.CommentManager;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

import java.util.ArrayList;
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

    /**
     * Instantiate the adapter and the Box Comment from Nuxeo Document and
     * load its properties into json format
     */
    public BoxCommentAdapter(DocumentModel doc) throws ClientException {
        BoxService boxService = Framework.getLocalService(BoxService.class);

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

    }

    private List<BoxTypedObject> fillItem(DocumentModel doc) throws
            ClientException {
        List<BoxTypedObject> boxTypedObjects = new ArrayList<>();
        CommentManager commentManager = Framework.getLocalService
                (CommentManager.class);
        DocumentModelList targetList = (DocumentModelList) commentManager
                .getDocumentsForComment(doc);
        for (DocumentModel target : targetList) {
            BoxTypedObject boxItem = target.isFolder() ? new BoxFolder() : new
                    BoxFile();
            boxTypedObjects.add(boxItem);
        }
        return boxTypedObjects;
    }

    public BoxComment getBoxComment() {
        return boxComment;
    }

}