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
package com.nuxeo.box.api.collaboration.comment.adapter;

import com.nuxeo.box.api.adapter.BoxAdapter;
import com.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import com.nuxeo.box.api.marshalling.dao.BoxCollaboration;
import com.nuxeo.box.api.marshalling.dao.BoxUser;
import com.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Box Comment Adapter
 *
 * @since 5.9.3
 */
public class BoxCollaborationAdapter {

    protected BoxCollaboration boxCollaboration;

    protected final Map<String, Object> boxProperties = new HashMap<>();

    protected DocumentModel collaboration;

    /**
     * Instantiate the adapter of the Box Collaboration and
     * load its properties into json format
     */
    public BoxCollaborationAdapter(DocumentModel doc) throws ClientException {
        BoxService boxService = Framework.getLocalService(BoxService.class);

        collaboration = doc;

        // Nuxeo acl doesn't provide id yet
        boxProperties.put(BoxCollaboration.FIELD_ID, null);
        // Nuxeo acl doesn't provide created date yet
        boxProperties.put(BoxCollaboration.FIELD_CREATED_AT, null);
        // Nuxeo acl doesn't provide modified date yet
        boxProperties.put(BoxCollaboration.FIELD_MODIFIED_AT, null);

        // Creator
        final UserManager userManager = Framework.getLocalService(UserManager
                .class);
        String creator = doc.getPropertyValue("dc:creator") != null
                ? (String) doc.getPropertyValue("dc:creator") : "system";
        NuxeoPrincipal principalCreator = userManager.getPrincipal(creator);
        final BoxUser boxCreator = boxService.fillUser(principalCreator);
        boxProperties.put(BoxCollaboration.FIELD_CREATED_BY, boxCreator);

        // Nuxeo doesn't provide expiration date yet
        boxProperties.put(BoxCollaboration.FIELD_EXPIRES_AT, null);
        // Nuxeo doesn't provide status on ACL setup (accepted...)
        boxProperties.put(BoxCollaboration.FIELD_STATUS, null);
        // Nuxeo doesn't provide acknowledge date on status (see just above)
        boxProperties.put(BoxCollaboration.FIELD_ACKNOWLEGED_AT, null);

        // Document itself -> a mandatory folder
        BoxFolderAdapter folderAdapter = (BoxFolderAdapter) doc.getAdapter
                (BoxAdapter.class);
        boxProperties.put(BoxCollaboration.FIELD_FOLDER,
                folderAdapter.getMiniItem());

        // User whom can access to the document
        boxProperties.put(BoxCollaboration.FIELD_ACCESSIBLE_BY,
                boxService.fillUsers(doc, userManager));

        boxCollaboration = new BoxCollaboration(boxProperties);
    }

    public void setBoxCollaboration(BoxCollaboration boxCollaboration) {
        this.boxCollaboration = boxCollaboration;
    }

    public BoxCollaboration getBoxCollaboration() {
        return boxCollaboration;
    }

}