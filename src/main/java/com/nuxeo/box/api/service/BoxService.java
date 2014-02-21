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
import com.nuxeo.box.api.dao.BoxObject;
import com.nuxeo.box.api.dao.BoxTypedObject;
import com.nuxeo.box.api.dao.BoxUser;
import com.nuxeo.box.api.exceptions.BoxJSONException;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;

import java.util.List;

/**
 * Box Service Utils
 *
 * @since 5.9.3
 */
public interface BoxService {

    BoxCollection searchBox(String term, CoreSession session,
            String limit, String offset) throws ClientException;

    List<BoxTypedObject> getBoxCollection(DocumentModelList
            documentModels, String fields) throws ClientException;

    String toJSONString(BoxObject boxObject) throws BoxJSONException;

    String getBoxId(DocumentModel doc);

    String getBoxSequenceId(DocumentModel doc);

    String getBoxEtag(DocumentModel doc);

    String getBoxName(DocumentModel doc);

    BoxUser fillUser(NuxeoPrincipal creator);

    BoxFolder getBoxFolder(String jsonBoxFolder) throws
            BoxJSONException;

    BoxFile getBoxFile(String jsonBoxFile) throws
            BoxJSONException;

    BoxComment getBoxComment(String jsonBoxComment) throws
            BoxJSONException;
}
