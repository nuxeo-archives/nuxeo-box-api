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
package com.nuxeo.box.api.service;

import com.nuxeo.box.api.dao.BoxCollection;
import com.nuxeo.box.api.dao.BoxComment;
import com.nuxeo.box.api.dao.BoxFile;
import com.nuxeo.box.api.dao.BoxFolder;
import com.nuxeo.box.api.dao.BoxItem;
import com.nuxeo.box.api.dao.BoxObject;
import com.nuxeo.box.api.dao.BoxTypedObject;
import com.nuxeo.box.api.dao.BoxUser;
import com.nuxeo.box.api.exceptions.BoxJSONException;
import com.nuxeo.box.api.jsonparsing.BoxJSONParser;
import com.nuxeo.box.api.jsonparsing.BoxResourceHub;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Box Service Utils
 *
 * @since 5.9.3
 */
public class BoxServiceImpl implements BoxService {

    @Override
    public BoxCollection searchBox(String term, CoreSession session,
            String limit, String offset) throws ClientException {
        final Map<String, Object> collectionProperties = new HashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM " +
                "Document where ecm:fulltext = '" + term + "'");
        DocumentModelList documentModels = session.query(query.toString(),
                null, Long.parseLong(limit), Long.parseLong(offset), false);
        collectionProperties.put(BoxCollection.FIELD_ENTRIES,
                getBoxCollection(documentModels, null));
        collectionProperties.put(BoxCollection.FIELD_TOTAL_COUNT,
                documentModels.size());
        return new BoxCollection(Collections.unmodifiableMap
                (collectionProperties));
    }

    @Override
    public List<BoxTypedObject> getBoxCollection(DocumentModelList
            documentModels, String fields) throws ClientException {

        final List<BoxTypedObject> boxObject = new ArrayList<>();
        for (DocumentModel documentModel : documentModels) {
            final Map<String, Object> documentProperties = new HashMap<>();
            documentProperties.put(BoxTypedObject.FIELD_ID,
                    getBoxId(documentModel));
            documentProperties.put(BoxItem.FIELD_SEQUENCE_ID,
                    getBoxSequenceId(documentModel));
            documentProperties.put(BoxItem.FIELD_ETAG, getBoxEtag
                    (documentModel));
            documentProperties.put(BoxItem.FIELD_NAME,
                    getBoxName(documentModel));
            //NX MD5 -> Box SHA1
            if (documentModel.hasSchema("file")) {
                Blob blob = (Blob) documentModel.getPropertyValue
                        ("file:content");
                if (blob != null) {
                    documentProperties.put(BoxFile.FIELD_SHA1,
                            blob.getDigest());
                }
            }
            // This different instantiation is related to the param type
            // which is automatically added in json payload by Box marshaller
            // following the box object type
            BoxTypedObject boxChild;
            boxChild = documentModel.isFolder() ? new BoxFolder() : new
                    BoxFile();
            // Depending of fields filter provided in the REST call:
            // Properties setup (* -> all)
            if (!"*".equals(fields) && fields != null) {
                for (String field : fields.split(",")) {
                    boxChild.put(field, documentProperties.get(field));
                }
            } else {
                boxChild.putAll(documentProperties);
            }
            boxObject.add(boxChild);
        }
        return boxObject;
    }

    /**
     * Marshalling the box object to JSON
     */
    public String toJSONString(BoxObject boxObject) throws BoxJSONException {
        BoxJSONParser boxJSONParser = new BoxJSONParser(new
                BoxResourceHub());
        return boxObject.toJSONString(boxJSONParser);
    }

    /**
     * Helpers to get Ids for sequence, etag and id itself.
     * In case of root, sequence and etag are null and id = 0 according to
     * the box
     * documentation.
     */

    @Override
    public String getBoxId(DocumentModel doc) {
        return doc.getName() != null ? doc.getId() : "0";
    }

    @Override
    public String getBoxSequenceId(DocumentModel doc) {
        return doc.getName() != null ? doc.getId() : null;
    }

    @Override
    public String getBoxEtag(DocumentModel doc) {
        return doc.getName() != null ? doc.getId() + "_" + doc
                .getVersionLabel() : null;
    }

    @Override
    public String getBoxName(DocumentModel doc) {
        return doc.getName() != null ? doc.getName() : "/";
    }

    /**
     * Fill box object user
     *
     * @param creator
     * @return a box User
     */
    @Override
    public BoxUser fillUser(NuxeoPrincipal creator) {
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

    @Override
    public BoxFolder getBoxFolder(String jsonBoxFolder) throws
            BoxJSONException {
        return new BoxJSONParser(new BoxResourceHub())
                .parseIntoBoxObject(jsonBoxFolder, BoxFolder.class);
    }

    @Override
    public BoxFile getBoxFile(String jsonBoxFile) throws
            BoxJSONException {
        return new BoxJSONParser(new BoxResourceHub())
                .parseIntoBoxObject(jsonBoxFile, BoxFile.class);
    }

    @Override
    public BoxComment getBoxComment(String jsonBoxComment) throws
            BoxJSONException {
        return new BoxJSONParser(new BoxResourceHub())
                .parseIntoBoxObject(jsonBoxComment, BoxComment.class);
    }

    @Override
    public String getJSONFromBox(BoxTypedObject boxTypedObject) throws
            BoxJSONException {
        return boxTypedObject.toJSONString(new BoxJSONParser(new
                BoxResourceHub()));
    }
}
