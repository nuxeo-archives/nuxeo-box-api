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
package com.nuxeo.box.api.collaboration;

import com.nuxeo.box.api.adapter.BoxAdapter;
import com.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import com.nuxeo.box.api.marshalling.dao.BoxCollaboration;
import com.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import com.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentRef;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.security.ACE;
import org.nuxeo.ecm.core.api.security.ACL;
import org.nuxeo.ecm.core.api.security.ACP;
import org.nuxeo.ecm.core.api.security.impl.ACLImpl;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.webengine.WebException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * WebObject for a Box Collaboration
 *
 * @since 5.9.3
 */
@WebObject(type = "collaborations")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxCollaborationObject extends AbstractResource<ResourceTypeImpl> {


    BoxService boxService;

    BoxFolderAdapter boxFolder;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
        if (args != null && args.length == 1) {
            try {
                String folderId = (String) args[0];
                CoreSession session = ctx.getCoreSession();
                DocumentModel folder = session.getDocument(new IdRef(folderId));
                boxFolder = (BoxFolderAdapter) folder.getAdapter
                        (BoxAdapter.class);
            } catch (Exception e) {
                throw WebException.wrap(e);
            }
            setRoot(true);
        }
    }

    @GET
    public String doGetCollaborations() throws NoSuchDocumentException,
            ClientException,
            BoxJSONException {
        return boxService.toJSONString(boxFolder.getCollaborations());
    }

    @GET
    @Path("/{folderId}")
    public String doGetCollaborations(@PathParam("folderId") String folderId)
            throws NoSuchDocumentException,
            ClientException,
            BoxJSONException {
        CoreSession session = ctx.getCoreSession();
        DocumentModel folder = session.getDocument(new IdRef(folderId));
        boxFolder = (BoxFolderAdapter) folder.getAdapter
                (BoxAdapter.class);
        return doGetCollaborations();
    }

    /**
     * Delete all local ACLs for a given folder id
     */
    @DELETE
    @Path("/{folderId}")
    public void doRemoveCollaboration(@PathParam("folderId") String
            folderId) throws NoSuchDocumentException,
            ClientException,
            BoxJSONException {
        CoreSession session = ctx.getCoreSession();
        DocumentRef docRef = new IdRef(folderId);
        ACP acp = session.getACP(docRef);
        acp.removeACL(ACL.LOCAL_ACL);
        session.setACP(docRef, acp, true);
        session.save();
    }

    @POST
    public String doPostCollaboration(String jsonBoxCollaboration) throws
            ClientException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        BoxCollaboration boxCollaboration = boxService.getBoxCollaboration
                (jsonBoxCollaboration);
        String documentId = boxCollaboration.getFolder().getId();
        DocumentModel targetDocument = session.getDocument(new IdRef
                (documentId));
        // ACLs Setup
        ACP acp = session.getACP(targetDocument.getRef());
        ACL acl = acp.getACL(ACL.LOCAL_ACL);
        if (acl == null) {
            acl = new ACLImpl(ACL.LOCAL_ACL);
            acp.addACL(acl);
        }
        ACE ace = new ACE(boxCollaboration.getAccessibleBy().getId(),
                boxService.getNxBoxRole().inverse().get(boxCollaboration
                        .getRole()),
                true);
        acl.add(ace);
        session.setACP(targetDocument.getRef(), acp, true);
        session.save();
        // Return the new box collab json
        BoxFolderAdapter boxFolderUpdated = (BoxFolderAdapter) targetDocument
                .getAdapter(BoxAdapter.class);
        return boxService.toJSONString(boxService.getBoxCollaboration
                (boxFolderUpdated, ace));
    }

}
