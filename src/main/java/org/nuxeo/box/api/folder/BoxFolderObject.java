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
package org.nuxeo.box.api.folder;

import org.nuxeo.box.api.adapter.BoxAdapter;
import org.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import org.nuxeo.box.api.marshalling.dao.BoxFolder;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.marshalling.jsonparsing.BoxJSONParser;
import org.nuxeo.box.api.marshalling.jsonparsing.BoxResourceHub;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * WebObject for a Box Folder
 *
 * @since 5.9.2
 */
@WebObject(type = "folder")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxFolderObject extends AbstractResource<ResourceTypeImpl> {

    BoxService boxService;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
    }

    @GET
    public Object doGet() {
        return getView("index");
    }

    @GET
    @Path("{folderId}")
    public String doGetFolder(@PathParam("folderId") final String folderId) throws NoSuchDocumentException,
            ClientException, BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        final DocumentModel folder = "0".equals(folderId) ? session.getRootDocument() : session.getDocument(new IdRef(
                folderId));
        // Adapt nx document to box folder adapter
        final BoxFolderAdapter folderAdapter = (BoxFolderAdapter) folder.getAdapter(BoxAdapter.class);
        return boxService.toJSONString(folderAdapter.getBoxItem());
    }

    @POST
    public String doPostFolder(String jsonBoxFolder) throws ClientException, BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        BoxFolder boxFolder = boxService.getBoxFolder(jsonBoxFolder);
        // Fetching its parent to get parent id
        String parentId = boxFolder.getParent().getId();
        DocumentModel documentParent;
        if ("0".equals(parentId)) {
            documentParent = session.getRootDocument();
        } else {
            documentParent = session.getDocument(new IdRef(boxFolder.getParent().getId()));
        }
        // Create the nx document from box folder information
        DocumentModel newFolder = session.createDocumentModel(documentParent.getPathAsString(), boxFolder.getName(),
                "Folder");
        newFolder.setPropertyValue("dc:title", boxFolder.getName());
        newFolder = session.createDocument(newFolder);
        // Adapt nx document to box folder adapter
        final BoxFolderAdapter folderAdapter = (BoxFolderAdapter) newFolder.getAdapter(BoxAdapter.class);
        // Return the new box folder json
        return boxService.toJSONString(folderAdapter.getBoxItem());
    }

    @PUT
    @Path("{folderId}")
    public String doPutFolder(@PathParam("folderId") String folderId, String jsonBoxFolder) throws ClientException,
            BoxJSONException, ParseException, IllegalAccessException, InvocationTargetException {
        final CoreSession session = ctx.getCoreSession();
        // Fetch the nx document with given id
        final DocumentModel nxDocument = session.getDocument(new IdRef(folderId));
        // Create box folder from json payload
        BoxFolder boxFolderUpdated = new BoxJSONParser(new BoxResourceHub()).parseIntoBoxObject(jsonBoxFolder,
                BoxFolder.class);
        // Adapt nx document to box folder adapter
        final BoxFolderAdapter nxDocumentAdapter = (BoxFolderAdapter) nxDocument.getAdapter(BoxAdapter.class);
        // Update both nx document and box folder adapter
        nxDocumentAdapter.setBoxItem(boxFolderUpdated);
        nxDocumentAdapter.save(session);
        // Return the new box folder json
        return boxService.toJSONString(nxDocumentAdapter.getBoxItem());
    }

    @DELETE
    @Path("{folderId}")
    public void doDeleteFolder(@PathParam("folderId") String folderId) throws ClientException {
        final CoreSession session = ctx.getCoreSession();
        session.removeDocument(new IdRef(folderId));
        session.save();
    }

    @Path("{folderId}/items")
    public Object doGetItems(@PathParam("folderId") String folderId) {
        return newObject("item", folderId);
    }

    @Path("{folderId}/collaborations")
    public Object doGetCollaboration(@PathParam("folderId") String folderId) {
        return newObject("collaborations", folderId);
    }
}
