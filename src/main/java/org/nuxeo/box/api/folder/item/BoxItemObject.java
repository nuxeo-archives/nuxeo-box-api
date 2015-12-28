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
 *     dmetzler <dmetzler@nuxeo.com>
 */
package org.nuxeo.box.api.folder.item;

import com.google.common.base.Objects;
import org.nuxeo.box.api.BoxConstants;
import org.nuxeo.box.api.adapter.BoxAdapter;
import org.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import org.nuxeo.box.api.marshalling.dao.BoxCollection;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.webengine.WebException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * WebObject for a Box Folder Item
 *
 * @since 5.9.2
 */
@WebObject(type = "item")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxItemObject extends AbstractResource<ResourceTypeImpl> {

    BoxFolderAdapter folderAdapter;

    BoxService boxService;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
        try {
            String folderId = (String) args[0];
            CoreSession session = ctx.getCoreSession();
            DocumentModel folder = session.getDocument(new IdRef(folderId));
            folderAdapter = (BoxFolderAdapter) folder.getAdapter(BoxAdapter.class);
        } catch (NuxeoException e) {
            throw WebException.wrap(e);
        }
        setRoot(true);
    }

    @GET
    public String doGetItems(@QueryParam("offset") String offset, @QueryParam("limit") String limit,
            @QueryParam("fields") String fields) throws BoxJSONException {
        CoreSession session = ctx.getCoreSession();
        BoxCollection itemCollection = folderAdapter.getItemCollection(session,
                Objects.firstNonNull(limit, BoxConstants.BOX_LIMIT),
                Objects.firstNonNull(offset, BoxConstants.BOX_OFFSET),
                Objects.firstNonNull(fields, BoxConstants.BOX_FIELDS));
        return boxService.toJSONString(itemCollection);
    }

}
