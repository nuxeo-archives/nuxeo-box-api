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
package com.nuxeo.box.api;

import com.nuxeo.box.api.dao.BoxCollection;
import com.nuxeo.box.api.dao.BoxFile;
import com.nuxeo.box.api.dao.BoxFolder;
import com.nuxeo.box.api.dao.BoxItem;
import com.nuxeo.box.api.dao.BoxObject;
import com.nuxeo.box.api.dao.BoxTypedObject;
import com.nuxeo.box.api.dao.BoxUser;
import com.nuxeo.box.api.exceptions.BoxJSONException;
import com.nuxeo.box.api.jsonparsing.BoxJSONParser;
import com.nuxeo.box.api.jsonparsing.BoxResourceHub;
import com.nuxeo.box.api.service.BoxService;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.tag.Tag;
import org.nuxeo.ecm.platform.tag.TagService;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.ecm.quota.size.QuotaAware;
import org.nuxeo.ecm.quota.size.QuotaAwareDocument;
import org.nuxeo.runtime.api.Framework;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract Box Adapter
 *
 * @since 5.9.2
 */
public abstract class BoxAdapter {

    protected final DocumentModel doc;

    protected final Map<String, Object> boxProperties = new HashMap<>();

    protected BoxItem boxItem;

    protected final BoxService boxService = Framework.getLocalService(BoxService.class);

    public BoxAdapter(DocumentModel doc) throws ClientException {
        this.doc = doc;
        CoreSession session = doc.getCoreSession();

        boxProperties.put(BoxItem.FIELD_ID, boxService.getBoxId(doc));
        boxProperties.put(BoxItem.FIELD_SEQUENCE_ID,
                boxService.getBoxSequenceId(doc));
        boxProperties.put(BoxItem.FIELD_ETAG, boxService.getBoxEtag(doc));

        boxProperties.put(BoxItem.FIELD_NAME, doc.getName());
        boxProperties.put(BoxItem.FIELD_CREATED_AT,
                ISODateTimeFormat.dateTime().print(
                        new DateTime(doc.getPropertyValue("dc:created"))));
        boxProperties.put(BoxItem.FIELD_MODIFIED_AT,
                ISODateTimeFormat.dateTime().print(
                        new DateTime(doc.getPropertyValue("dc:modified"))));
        boxProperties.put(BoxItem.FIELD_DESCRIPTION,
                doc.getPropertyValue("dc:description"));

        // size
        final QuotaAwareDocument quotaAwareDocument = (QuotaAwareDocument)
                doc.getAdapter(QuotaAware.class);
        boxProperties.put(BoxItem.FIELD_SIZE, quotaAwareDocument != null ?
                quotaAwareDocument.getInnerSize() : -1.0);

        // path_collection
        final DocumentModel parentDoc = session.getDocument(doc.getParentRef());
        final Map<String, Object> pathCollection = new HashMap<>();
        pathCollection.put(BoxCollection.FIELD_TOTAL_COUNT,
                doc.getPathAsString().split("/").length);
        pathCollection.put(BoxCollection.FIELD_ENTRIES,
                getParentsHierarchy(session, parentDoc));
        BoxCollection boxPathCollection = new BoxCollection(Collections
                .unmodifiableMap(pathCollection));
        boxProperties.put(BoxItem.FIELD_PATH_COLLECTION, boxPathCollection);

        // parent
        final Map<String, Object> parentProperties = new HashMap<>();
        parentProperties.put(BoxItem.FIELD_ID, boxService.getBoxId(parentDoc));
        parentProperties.put(BoxItem.FIELD_SEQUENCE_ID,
                boxService.getBoxSequenceId(parentDoc));
        parentProperties.put(BoxItem.FIELD_NAME, boxService.getBoxName
                (parentDoc));
        parentProperties.put(BoxItem.FIELD_ETAG, boxService.getBoxEtag
                (parentDoc));
        BoxFolder parentFolder = new BoxFolder(Collections.unmodifiableMap
                (parentProperties));
        boxProperties.put(BoxItem.FIELD_PARENT, parentFolder);

        // Users
        // Creator
        final UserManager userManager = Framework.getLocalService(UserManager
                .class);
        final NuxeoPrincipal creator = userManager.getPrincipal((String) doc
                .getPropertyValue("dc:creator"));
        final BoxUser boxCreator = fillUser(creator);
        boxProperties.put(BoxItem.FIELD_CREATED_BY, boxCreator);

        //Last Contributor
        final NuxeoPrincipal lastContributor = userManager.getPrincipal(
                (String) doc.getPropertyValue("dc:lastContributor"));
        final BoxUser boxContributor = fillUser(lastContributor);
        boxProperties.put(BoxItem.FIELD_MODIFIED_BY, boxContributor);

        // Owner
        boxProperties.put(BoxItem.FIELD_OWNED_BY, boxCreator);

        // Shared Link
        boxProperties.put(BoxItem.FIELD_SHARED_LINK, null);

        // Status
        boxProperties.put(BoxItem.FIELD_ITEM_STATUS,
                doc.getCurrentLifeCycleState());

        // Tags
        boxProperties.put(BoxItem.FIELD_TAGS, getTags(session));

    }

    public BoxItem getBoxItem() {
        return boxItem;
    }

    /**
     * Update the box item properties
     *
     * @param boxItem containing values updated
     */
    public void setBoxItem(BoxItem boxItem) {
        for (String field : boxItem.getKeySet()) {
            this.boxItem.put(field, boxItem.getValue(field));
        }
    }

    public DocumentModel getDoc() {
        return doc;
    }

    protected List<BoxTypedObject> getParentsHierarchy(CoreSession session,
            DocumentModel parentDoc) throws ClientException {
        final List<BoxTypedObject> pathCollection = new ArrayList<>();
        while (parentDoc != null) {
            final Map<String, Object> parentCollectionProperties = new
                    HashMap<>();
            parentCollectionProperties.put(BoxItem.FIELD_ID,
                    boxService.getBoxId(parentDoc));
            parentCollectionProperties.put(BoxItem.FIELD_SEQUENCE_ID,
                    boxService.getBoxSequenceId(parentDoc));
            parentCollectionProperties.put(BoxItem.FIELD_ETAG,
                    boxService.getBoxEtag(parentDoc));
            parentCollectionProperties.put(BoxItem.FIELD_NAME,
                    boxService.getBoxName(parentDoc));
            BoxTypedObject boxParent;
            // This different instantiation is related to the param type
            // which is automatically added in json payload by Box marshaller
            // following the box object type
            if (parentDoc.isFolder()) {
                boxParent = new BoxFolder(Collections
                        .unmodifiableMap(parentCollectionProperties));
            } else {
                boxParent = new BoxFile(Collections
                        .unmodifiableMap(parentCollectionProperties));
            }
            pathCollection.add(boxParent);
            parentDoc = session.getParentDocument(parentDoc.getParentRef());
        }
        return pathCollection;
    }

    protected String[] getTags(CoreSession session) throws ClientException {
        final TagService tagService = Framework.getLocalService(TagService
                .class);
        final List<Tag> tags = tagService.getDocumentTags(session,
                doc.getId(), session.getPrincipal().getName());
        final String[] tagNames = new String[tags.size()];
        int index = 0;
        for (Tag tag : tags) {
            tagNames[index] = tag.getLabel();
            index++;
        }
        return tagNames;
    }

    /**
     * Update the document (nx/box sides) thanks to a box item
     */
    public void save(CoreSession session) throws ClientException,
            ParseException, InvocationTargetException,
            IllegalAccessException, BoxJSONException {

        setDescription(boxItem.getDescription());
        setCreator(boxItem.getOwnedBy().getId());

        String id = boxItem.getParent().getId();
        // check if id is root's one
        String newParentId = "0".equals(id) ? session.getRootDocument().getId
                () : id;
        IdRef documentIdRef = new IdRef(doc.getId());

        // If the name has changed, update location in Nuxeo repository
        // OR if parent id has been updated -> move the document
        String oldParentId = session.getParentDocument(documentIdRef).getId();
        if (!oldParentId.equals(newParentId) || !doc.getName().equals(boxItem
                .getName())) {

            session.move(documentIdRef, new IdRef(newParentId),
                    boxItem.getName());
            // Title and name are same here
            setTitle(boxItem.getName());
        }

        // Tags
        TagService tagService = Framework.getLocalService(TagService.class);
        if (tagService != null) {
            if (boxItem.getTags().length != 0) {
                tagService.removeTags(session, doc.getId());
                for (String tag : boxItem.getTags()) {
                    tagService.tag(session, doc.getId(), tag,
                            session.getPrincipal().getName());
                }
            }
        }
        session.saveDocument(doc);
        session.save();
    }

    /**
     * Fill box object user
     *
     * @param creator
     * @return a box User
     */
    protected BoxUser fillUser(NuxeoPrincipal creator) {
        final Map<String, Object> mapUser = new HashMap<>();
        mapUser.put(BoxItem.FIELD_ID, creator != null ? creator
                .getPrincipalId() : "system");
        mapUser.put(BoxItem.FIELD_NAME, creator != null ? creator
                .getFirstName() + " " + creator
                .getLastName() : "system");
        mapUser.put(BoxUser.FIELD_LOGIN, creator != null ? creator.getName()
                : "system");
        return new BoxUser(Collections.unmodifiableMap(mapUser));
    }

    public void setTitle(String value) throws ClientException {
        doc.setPropertyValue("dc:title", value);
    }

    public void setDescription(String value) throws ClientException {
        doc.setPropertyValue("dc:description", value);
    }

    public void setCreator(String value) throws ClientException {
        doc.setPropertyValue("dc:creator", value);
    }

    public String toJSONString(BoxObject boxObject) throws BoxJSONException {
        BoxJSONParser boxJSONParser = new BoxJSONParser(new
                BoxResourceHub());
        return boxObject.toJSONString(boxJSONParser);
    }
}
