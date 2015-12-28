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
package org.nuxeo.box.api.service;

import com.google.common.collect.BiMap;
import org.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import org.nuxeo.box.api.marshalling.dao.BoxCollaboration;
import org.nuxeo.box.api.marshalling.dao.BoxCollection;
import org.nuxeo.box.api.marshalling.dao.BoxComment;
import org.nuxeo.box.api.marshalling.dao.BoxFile;
import org.nuxeo.box.api.marshalling.dao.BoxFolder;
import org.nuxeo.box.api.marshalling.dao.BoxGroup;
import org.nuxeo.box.api.marshalling.dao.BoxObject;
import org.nuxeo.box.api.marshalling.dao.BoxTypedObject;
import org.nuxeo.box.api.marshalling.dao.BoxUser;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
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

    BoxCollection searchBox(String term, CoreSession session, String limit, String offset);

    List<BoxTypedObject> getBoxDocumentCollection(DocumentModelList documentModels, String fields);

    BoxCollaboration getBoxCollaboration(BoxFolderAdapter boxItem, ACE ace, String collaborationId);

    String toJSONString(BoxObject boxObject) throws BoxJSONException;

    String getBoxId(DocumentModel doc);

    String getBoxSequenceId(DocumentModel doc);

    String getBoxEtag(DocumentModel doc);

    String getBoxName(DocumentModel doc);

    BoxUser fillUser(NuxeoPrincipal creator);

    BoxGroup fillGroup(NuxeoGroup group);

    BoxFolder getBoxFolder(String jsonBoxFolder) throws BoxJSONException;

    BoxFile getBoxFile(String jsonBoxFile) throws BoxJSONException;

    BoxComment getBoxComment(String jsonBoxComment) throws BoxJSONException;

    BoxCollaboration getBoxCollaboration(String jsonBoxCollaboration) throws BoxJSONException;

    String getJSONFromBox(BoxTypedObject boxTypedObject) throws BoxJSONException;

    String getJSONBoxException(Exception e, int status);

    String[] getCollaborationArrayIds(String collaborationId);

}
