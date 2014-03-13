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

import com.google.common.collect.BiMap;
import com.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import com.nuxeo.box.api.marshalling.dao.BoxCollaboration;
import com.nuxeo.box.api.marshalling.dao.BoxCollection;
import com.nuxeo.box.api.marshalling.dao.BoxFile;
import com.nuxeo.box.api.marshalling.dao.BoxFolder;
import com.nuxeo.box.api.marshalling.dao.BoxGroup;
import com.nuxeo.box.api.marshalling.dao.BoxObject;
import com.nuxeo.box.api.marshalling.dao.BoxTypedObject;
import com.nuxeo.box.api.marshalling.dao.BoxUser;
import com.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoGroup;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.api.security.ACE;

import java.util.List;

/**
 * Box Service Utils
 *
 * @since 5.9.3
 */
public interface BoxService {

    BiMap<String, String> getNxBoxRole();

    BoxCollection searchBox(String term, CoreSession session,
            String limit, String offset) throws ClientException;

    List<BoxTypedObject> getBoxDocumentCollection(DocumentModelList
            documentModels, String fields) throws ClientException;

    BoxCollaboration getBoxCollaboration(BoxFolderAdapter boxItem,
            ACE ace, String collaborationId) throws ClientException;

    String toJSONString(BoxObject boxObject) throws BoxJSONException;

    String getBoxId(DocumentModel doc);

    String getBoxSequenceId(DocumentModel doc);

    String getBoxEtag(DocumentModel doc);

    String getBoxName(DocumentModel doc);

    BoxUser fillUser(NuxeoPrincipal creator);

    BoxGroup fillGroup(NuxeoGroup group);

    BoxFolder getBoxFolder(String jsonBoxFolder) throws
            BoxJSONException;

    BoxFile getBoxFile(String jsonBoxFile) throws
            BoxJSONException;

    BoxCollaboration getBoxCollaboration(String jsonBoxCollaboration) throws
            BoxJSONException;

    String getJSONFromBox(BoxTypedObject boxTypedObject) throws
            BoxJSONException;

    String getJSONBoxException(Exception e, int status);

    String[] getCollaborationArrayIds(String collaborationId);
}
