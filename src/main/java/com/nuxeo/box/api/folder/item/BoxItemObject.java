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
 *     dmetzler <dmetzler@nuxeo.com>
 */
package com.nuxeo.box.api.folder.item;

import com.google.common.base.Objects;
import com.nuxeo.box.api.BoxConstants;
import com.nuxeo.box.api.adapter.BoxAdapter;
import com.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import com.nuxeo.box.api.marshalling.dao.BoxCollection;
import com.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import com.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
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
            folderAdapter = (BoxFolderAdapter) folder.getAdapter(BoxAdapter
                    .class);
        } catch (Exception e) {
            throw WebException.wrap(e);
        }
        setRoot(true);
    }

    @GET
    public String doGetItems(@QueryParam("offset") String offset,
            @QueryParam("limit") String limit, @QueryParam("fields") String
            fields) throws
            BoxJSONException, ClientException {
        CoreSession session = ctx.getCoreSession();
        BoxCollection itemCollection = folderAdapter.getItemCollection
                (session, Objects.firstNonNull(limit, BoxConstants.BOX_LIMIT)
                        , Objects.firstNonNull(offset,
                        BoxConstants.BOX_OFFSET), Objects.firstNonNull
                        (fields, BoxConstants.BOX_FIELDS));
        return boxService.toJSONString(itemCollection);
    }

}
