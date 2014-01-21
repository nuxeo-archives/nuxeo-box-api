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
package com.nuxeo.box.api.folder.io;

import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;

import java.util.HashMap;
import java.util.Map;

/**
 * Box Folder Adapter
 *
 * @since 5.9.2
 */
public class BoxFolderAdapter {

    protected BoxFolder boxFolder;

    protected final DocumentModel doc;

    protected String JSONBoxFolder;

    public BoxFolderAdapter(DocumentModel doc) {
        this.doc = doc;
    }

    public void newBoxInstance(CoreSession session) throws ClientException, BoxJSONException {
        Map<String, Object> boxProperties = new HashMap<>();
        Map<String, Object> boxSubProperties = new HashMap<>();

        DocumentModel parent = session.getParentDocument(doc.getRef());
        DocumentModelList children = session.getChildren(doc.getRef());

        // Folder properties
        boxProperties.put("type", doc.getType());
        boxProperties.put("id", doc.getId());
        boxProperties.put("sequence_id", "sequence_id");
        boxProperties.put("etag", "etag");
        boxProperties.put("created_at", ISODateTimeFormat.dateTime().print(
                new DateTime(doc.getPropertyValue("dc:created"))));
        boxProperties.put("modified_at", ISODateTimeFormat.dateTime().print(
                new DateTime(doc.getPropertyValue("dc:modified"))));
        boxProperties.put("description", doc.getPropertyValue("dc:description").toString());
        boxProperties.put("size", 12.0);
        //boxProperties.put("path_collection", "path_collection");

        // Users
        HashMap<String, Object> mapUser = new HashMap<>();
        mapUser.put("name", doc.getPropertyValue("dc:creator").toString());
        BoxUser boxUser = new BoxUser(mapUser);
        boxProperties.put("created_by", boxUser);
        mapUser.put("name", doc.getPropertyValue("dc:lastContributor").toString());
        BoxUser boxContributor = new BoxUser(mapUser);
        boxProperties.put("modified_by", boxContributor);
        boxProperties.put("owned_by", boxUser);
        //boxProperties.put("shared_link", "shared_link");
        //boxProperties.put("folder_upload_email", "folder_upload_email");
        boxProperties.put("item_status", doc.getCurrentLifeCycleState());
        //boxProperties.put("tags", "tosee");

        // Children properties
        //boxProperties.put("item_collection", "collection");

        // Parent properties
        /*boxProperties.put("type", parent.getType());
        boxProperties.put("id", parent.getId());
        boxProperties.put("sequence_id", parent.getId());
        boxProperties.put("etag", parent.getId());
        boxProperties.put("name", parent.getName());
        boxProperties.put("parent", boxSubProperties);
        boxSubProperties.clear();*/

        boxFolder = new BoxFolder(boxProperties);
        JSONBoxFolder = boxFolder.toJSONString(new BoxJSONParser(new BoxResourceHub()));
    }

    public String getJSONBoxFolder() {
        return JSONBoxFolder;
    }

    public void save(CoreSession session) throws ClientException {
        session.saveDocument(doc);
    }

    public String getId() {
        return doc.getId();
    }

    public BoxFolder getBoxFolder() {
        return boxFolder;
    }
}
