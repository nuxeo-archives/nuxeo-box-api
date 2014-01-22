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

import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxEmail;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.dao.BoxSharedLink;
import com.box.boxjavalibv2.dao.BoxTypedObject;
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
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.tag.Tag;
import org.nuxeo.ecm.platform.tag.TagService;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    /**
     * Instantiate a new Box Folder from Nuxeo Document and load its properties into json format
     *
     * @param session
     * @throws ClientException
     * @throws BoxJSONException
     */
    public void newBoxInstance(CoreSession session) throws ClientException, BoxJSONException {
        DocumentModel parent = session.getParentDocument(doc.getRef());
        DocumentModelList children = session.getChildren(doc.getRef());

        Map<String, Object> boxProperties = new LinkedHashMap<>();

        boxProperties.put(BoxItem.FIELD_TYPE, doc.getType());
        boxProperties.put(BoxItem.FIELD_ID, doc.getId());
        boxProperties.put(BoxItem.FIELD_SEQUENCE_ID, "sequence_id");
        boxProperties.put(BoxItem.FIELD_ETAG, "etag");
        boxProperties.put(BoxItem.FIELD_NAME, doc.getName());
        boxProperties.put(BoxItem.FIELD_CREATED_AT, ISODateTimeFormat.dateTime().print(
                new DateTime(doc.getPropertyValue("dc:created"))));
        boxProperties.put(BoxItem.FIELD_MODIFIED_AT, ISODateTimeFormat.dateTime().print(
                new DateTime(doc.getPropertyValue("dc:modified"))));
        boxProperties.put(BoxItem.FIELD_DESCRIPTION, doc.getPropertyValue("dc:description").toString());
        // size -> quota
        boxProperties.put(BoxItem.FIELD_SIZE, 12.0);

        // path_collection -> Quota service?
        Map<String, Object> boxCollectionProperties = new LinkedHashMap<>();
        boxCollectionProperties.put(BoxCollection.FIELD_TOTAL_COUNT, doc.getPathAsString().split("\\\\").length - 1);
        boxCollectionProperties.put(BoxCollection.FIELD_ENTRIES, new ArrayList<BoxTypedObject>());
        BoxCollection boxCollection = new BoxCollection(boxCollectionProperties);
        boxProperties.put(BoxItem.FIELD_PATH_COLLECTION, boxCollection);

        // Users
        // Creator
        UserManager userManager = Framework.getLocalService(UserManager.class);
        NuxeoPrincipal creator = userManager.getPrincipal(doc.getPropertyValue("dc:creator").toString());
        BoxUser boxCreator = fillUser(creator);
        boxProperties.put(BoxItem.FIELD_CREATED_BY, boxCreator);

        //Last Contributor
        NuxeoPrincipal lastContributor = userManager.getPrincipal(doc.getPropertyValue("dc:lastContributor").toString());
        BoxUser boxContributor = fillUser(lastContributor);
        boxProperties.put(BoxItem.FIELD_MODIFIED_BY, boxContributor);

        // Owner
        boxProperties.put(BoxItem.FIELD_OWNED_BY, boxCreator);

        // iscanpreview -> BoxSharedLinkPermissions?
        boxProperties.put(BoxItem.FIELD_SHARED_LINK, new BoxSharedLink());

        LinkedHashMap<String, Object> boxEmailProperties = new LinkedHashMap<>();
        boxEmailProperties.put(BoxEmail.FIELD_ACCESS, "open");
        boxEmailProperties.put(BoxEmail.FIELD_EMAIL, "email");
        BoxEmail boxEmail = new BoxEmail(boxEmailProperties);
        boxProperties.put(BoxFolder.FIELD_FOLDER_UPLOAD_EMAIL, boxEmail);

        // Status
        boxProperties.put(BoxItem.FIELD_ITEM_STATUS, doc.getCurrentLifeCycleState());

        // Children
        LinkedHashMap<String, Object> boxItemCollectionProperties = new LinkedHashMap<>();
        boxItemCollectionProperties.put(BoxCollection.FIELD_TOTAL_COUNT, doc.getPathAsString().split("\\\\").length - 1);
        ArrayList<BoxTypedObject> boxTypedObjects = fillChildren(session.getChildren(doc.getRef()));
        boxItemCollectionProperties.put(BoxCollection.FIELD_ENTRIES, boxTypedObjects);
        // offset and limits are missing?
        BoxCollection boxItemCollection = new BoxCollection(boxItemCollectionProperties);
        boxProperties.put(BoxFolder.FIELD_ITEM_COLLECTION, boxItemCollection);

        // Tags
        boxProperties.put(BoxItem.FIELD_TAGS, getTags(session));

        boxFolder = new BoxFolder(boxProperties);
        JSONBoxFolder = boxFolder.toJSONString(new BoxJSONParser(new BoxResourceHub()));
    }

    protected String[] getTags(CoreSession session) throws ClientException {
        TagService tagService = Framework.getLocalService(TagService.class);
        List<Tag> tags = tagService.getDocumentTags(session, doc.getId(), session.getPrincipal().getName());
        String[] tagNames = new String[tags.size()];
        int index = 0;
        for (Tag tag : tags) {
            tagNames[index] = tag.getLabel();
            index++;
        }
        return tagNames;
    }

    /**
     * Fill item collection entries box object (missing some attributes -> see box doc)
     *
     * @param children
     * @return the list of children in item collection
     */
    protected ArrayList<BoxTypedObject> fillChildren(DocumentModelList children) throws ClientException {
        ArrayList<BoxTypedObject> boxTypedObjects = new ArrayList<>();
        LinkedHashMap<String, Object> childrenProperties = new LinkedHashMap<>();
        for (DocumentModel child : children) {
            childrenProperties.put(BoxTypedObject.FIELD_TYPE, child.getType());
            childrenProperties.put(BoxTypedObject.FIELD_ID, child.getId());
            childrenProperties.put(BoxTypedObject.FIELD_CREATED_AT, ISODateTimeFormat.dateTime().print(
                    new DateTime(child.getPropertyValue("dc:created"))));
            childrenProperties.put(BoxItem.FIELD_MODIFIED_AT, ISODateTimeFormat.dateTime().print(
                    new DateTime(child.getPropertyValue("dc:modified"))));
            boxTypedObjects.add(new BoxTypedObject(childrenProperties));
        }
        return boxTypedObjects;
    }

    /**
     * Fill box object user
     *
     * @param creator
     * @return a box User
     */
    protected BoxUser fillUser(NuxeoPrincipal creator) {
        LinkedHashMap<String, Object> mapUser = new LinkedHashMap<>();
        mapUser.put("type", "user");
        mapUser.put("id", creator.getPrincipalId());
        mapUser.put("name", creator.getFirstName() + " " + creator.getLastName());
        mapUser.put("login", creator.getName());
        return new BoxUser(mapUser);
    }

    /**
     * @return the folder properties into json format
     */
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
