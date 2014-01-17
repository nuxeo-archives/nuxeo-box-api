/*
 * (C) Copyright 2013 Nuxeo SA (http://nuxeo.com/) and contributors.
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
 *     dmetzler
 */
package com.nuxeo.box.api;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.core.rest.DocumentObject;
import org.nuxeo.ecm.webengine.model.WebObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This object basically overrides the default DocumentObject that doesn't know
 * how to produce/consume JSON
 *
 * @since 5.9.2
 */
@WebObject(type = "Folder")
@Produces({ "application/json+nxentity", MediaType.APPLICATION_JSON })
public class JSONFolderObject extends DocumentObject {

    @Override
    @GET
    public DocumentModelList doGet() throws ClientException {
        return ctx.getCoreSession().getChildren(doc.getRef());
    }

    @Path("/")
    public Object doGetRepository(@PathParam("id")
    String folderId) throws NoSuchDocumentException, ClientException {
        return ctx.getCoreSession().getDocument(new IdRef(folderId));
    }

}
