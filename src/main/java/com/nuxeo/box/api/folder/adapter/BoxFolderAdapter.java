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
package com.nuxeo.box.api.folder.adapter;

import com.nuxeo.box.api.BoxConstants;
import com.nuxeo.box.api.adapter.BoxAdapter;
import com.nuxeo.box.api.marshalling.dao.BoxCollaboration;
import com.nuxeo.box.api.marshalling.dao.BoxCollection;
import com.nuxeo.box.api.marshalling.dao.BoxEmail;
import com.nuxeo.box.api.marshalling.dao.BoxFolder;
import com.nuxeo.box.api.marshalling.dao.BoxItem;
import com.nuxeo.box.api.marshalling.dao.BoxUser;
import com.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Box Folder Adapter
 *
 * @since 5.9.2
 */
public class BoxFolderAdapter extends BoxAdapter {

    /**
     * Instantiate the adapter and the Box Folder from Nuxeo Document and
     * load its properties into json format
     */
    public BoxFolderAdapter(DocumentModel doc) throws ClientException {
        super(doc);
        CoreSession session = doc.getCoreSession();
        // Email update
        final Map<String, Object> boxEmailProperties = new HashMap<>();
        boxEmailProperties.put(BoxEmail.FIELD_ACCESS, null);
        boxEmailProperties.put(BoxEmail.FIELD_EMAIL, null);
        final BoxEmail boxEmail = new BoxEmail(Collections.unmodifiableMap
                (boxEmailProperties));
        boxProperties.put(BoxFolder.FIELD_FOLDER_UPLOAD_EMAIL, boxEmail);

        // Children
        boxProperties.put(BoxFolder.FIELD_ITEM_COLLECTION,
                getItemCollection(session, BoxConstants.BOX_LIMIT,
                        BoxConstants.BOX_OFFSET, BoxConstants.BOX_FIELDS));

        boxItem = new BoxFolder(Collections.unmodifiableMap(boxProperties));
    }

    @Override
    public BoxItem getMiniItem() {
        Map<String, Object> boxProperties = new HashMap<>();
        boxProperties.put(BoxItem.FIELD_ID, boxItem.getId());
        boxProperties.put(BoxItem.FIELD_SEQUENCE_ID, boxItem.getSequenceId());
        boxProperties.put(BoxItem.FIELD_NAME, boxItem.getName());
        return new BoxFolder(boxProperties);
    }

    /**
     * Fill item collection entries box object
     *
     * @return the list of children in item collection
     */
    public BoxCollection getItemCollection(CoreSession session,
            String limit, String offset, String fields) throws
            ClientException {
        final Map<String, Object> collectionProperties = new HashMap<>();
        // Fetch items
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM Document WHERE ecm:parentId=");
        query.append("'" + doc.getId() + "'");
        DocumentModelList children = session.query(query.toString(),
                null, Long.parseLong(limit), Long.parseLong(offset), false);
        collectionProperties.put(BoxCollection.FIELD_ENTRIES,
                boxService.getBoxCollection(children, fields));
        collectionProperties.put(BoxCollection.FIELD_TOTAL_COUNT,
                children.size());
        return new BoxCollection(Collections.unmodifiableMap
                (collectionProperties));
    }

    public BoxCollaboration getBoxCollaboration(DocumentModel doc) throws ClientException {
        BoxService boxService = Framework.getLocalService(BoxService.class);

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

        return new BoxCollaboration(boxProperties);
    }
}