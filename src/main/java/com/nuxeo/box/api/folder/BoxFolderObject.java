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
 *     vpasquier<vpasquier@nuxeo.com>
 *     dmetzler <dmetzler@nuxeo.com>
 */
package com.nuxeo.box.api.folder;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.nuxeo.box.api.folder.io.BoxFolderAdapter;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * WebObject for a Box Folder
 *
 * @since 5.9.2
 */
@WebObject(type = "folder")
@Produces({ "application/json+nxentity", MediaType.APPLICATION_JSON })
public class BoxFolderObject extends AbstractResource<ResourceTypeImpl> {

    @GET
    public Object doGet() {
        return getView("index");
    }

    @GET
    @Path("{folderId}")
    public Object doGetRepository(@PathParam("folderId")
    String folderId) throws NoSuchDocumentException, ClientException, BoxJSONException {
        DocumentModel folder = ctx.getCoreSession().getDocument(new IdRef(folderId));
        BoxFolderAdapter folderAdapter = folder.getAdapter(BoxFolderAdapter.class);
        folderAdapter.newBoxInstance(ctx.getCoreSession());
        return folderAdapter.getJSONBoxFolder();
    }

}
