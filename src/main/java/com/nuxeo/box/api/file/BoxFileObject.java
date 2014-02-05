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
package com.nuxeo.box.api.file;

import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.nuxeo.box.api.BoxAdapter;
import com.nuxeo.box.api.file.adapter.BoxFileAdapter;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * WebObject for a Box File
 *
 * @since 5.9.2
 */
@WebObject(type = "file")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxFileObject extends AbstractResource<ResourceTypeImpl> {

    @GET
    public Object doGet() {
        return getView("index");
    }

    @GET
    @Path("{fileId}")
    public String doGetFile(@PathParam("fileId")
    final String fileId) throws NoSuchDocumentException, ClientException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        final DocumentModel file = session.getDocument(new IdRef(fileId));
        // Adapt nx document to box folder adapter
        final BoxFileAdapter fileAdapter = (BoxFileAdapter) file.getAdapter
                (BoxAdapter.class);
        return fileAdapter.toJSONString(fileAdapter.getBoxItem());
    }

    @DELETE
    @Path("{fileId}")
    public void doDeleteFile(@PathParam("fileId") String fileId) throws
            ClientException {
        final CoreSession session = ctx.getCoreSession();
        session.removeDocument(new IdRef(fileId));
        session.save();
    }

    @PUT
    @Path("{fileId}")
    public String doUpdateFile(@PathParam("fileId") String fileId,
            String jsonBoxFile) throws ClientException, BoxJSONException,
            ParseException, IllegalAccessException, InvocationTargetException {
        final CoreSession session = ctx.getCoreSession();
        // Fetch the nx document with given id
        final DocumentModel nxDocument = session.getDocument(new IdRef
                (fileId));
        // Create box File from json payload
        BoxFile boxFileUpdated = new BoxJSONParser(new BoxResourceHub())
                .parseIntoBoxObject(jsonBoxFile, BoxFile.class);
        // Adapt nx document to box File adapter
        final BoxFileAdapter nxDocumentAdapter = (BoxFileAdapter)
                nxDocument.getAdapter
                        (BoxAdapter.class);
        // Update both nx document and box File adapter
        nxDocumentAdapter.setBoxItem(boxFileUpdated);
        nxDocumentAdapter.save(session);
        // Return the new box File json
        return nxDocumentAdapter.toJSONString(nxDocumentAdapter.getBoxItem());
    }
}
