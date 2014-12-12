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
package org.nuxeo.box.api.collaboration;

import org.apache.commons.lang.RandomStringUtils;
import org.nuxeo.box.api.adapter.BoxAdapter;
import org.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import org.nuxeo.box.api.marshalling.dao.BoxCollaboration;
import org.nuxeo.box.api.marshalling.dao.BoxUser;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.marshalling.exceptions.BoxRestException;
import org.nuxeo.box.api.service.BoxService;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
                boxFolder = (BoxFolderAdapter) folder.getAdapter(BoxAdapter.class);
            } catch (ClientException e) {
                throw WebException.wrap(e);
            }
            setRoot(true);
        }
    }

    @GET
    public String doGetCollaborations() throws NoSuchDocumentException, ClientException, BoxJSONException {
        return boxService.toJSONString(boxFolder.getCollaborations());
    }

    @GET
    @Path("/{collaborationId}")
    public String doGetCollaboration(@PathParam("collaborationId") String collaborationId) throws ClientException,
            BoxJSONException {
        CoreSession session = ctx.getCoreSession();
        String[] collaborationIds = boxService.getCollaborationArrayIds(collaborationId);
        DocumentModel folder = session.getDocument(new IdRef(collaborationIds[0]));
        boxFolder = (BoxFolderAdapter) folder.getAdapter(BoxAdapter.class);
        BoxCollaboration collaboration = boxFolder.getCollaboration(collaborationIds[1]);
        if (collaboration == null) {
            throw new BoxRestException("There is no collaboration with id " + collaborationId,
                    Response.Status.NOT_FOUND.getStatusCode());
        }
        return boxService.toJSONString(collaboration);
    }

    /**
     * Delete specific ACL for a given folder id
     */
    @DELETE
    @Path("/{collaborationId}")
    public void doRemoveCollaboration(@PathParam("collaborationId") String collaborationId)
            throws NoSuchDocumentException, ClientException, BoxJSONException {
        CoreSession session = ctx.getCoreSession();
        String[] collaborationIds = boxService.getCollaborationArrayIds(collaborationId);
        DocumentRef docRef = new IdRef(collaborationIds[0]);
        ACP acp = session.getACP(docRef);
        acp.removeACL(collaborationIds[1]);
        session.setACP(docRef, acp, true);
        session.save();
    }

    @POST
    public String doPostCollaboration(String jsonBoxCollaboration) throws ClientException, BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        BoxCollaboration boxCollaboration = boxService.getBoxCollaboration(jsonBoxCollaboration);
        String documentId = boxCollaboration.getFolder().getId();
        DocumentModel targetDocument = session.getDocument(new IdRef(documentId));
        // ACLs Setup
        ACP acp = session.getACP(targetDocument.getRef());
        String collaborationId = RandomStringUtils.random(6, false, true);
        ACL acl = acp.getACL(collaborationId);
        if (acl == null) {
            acl = new ACLImpl(collaborationId);
            acp.addACL(acl);
        }
        ACE ace = new ACE(boxCollaboration.getAccessibleBy().getId(), boxService.getNxBoxRole().inverse().get(
                boxCollaboration.getRole()), true);
        acl.add(ace);
        session.setACP(targetDocument.getRef(), acp, true);
        session.save();
        // Return the new box collab json
        BoxFolderAdapter boxFolderUpdated = (BoxFolderAdapter) targetDocument.getAdapter(BoxAdapter.class);
        return boxService.toJSONString(boxService.getBoxCollaboration(boxFolderUpdated, ace, collaborationId));
    }

    @PUT
    @Path("/{collaborationId}")
    public String doPutCollaboration(@PathParam("collaborationId") String collaborationId, String jsonBoxCollaboration)
            throws ClientException, BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        BoxCollaboration boxCollaboration = boxService.getBoxCollaboration(jsonBoxCollaboration);
        String[] collaborationIds = boxService.getCollaborationArrayIds(collaborationId);
        DocumentRef docRef = new IdRef(collaborationIds[0]);
        DocumentModel targetDocument = session.getDocument(docRef);
        // ACLs Setup
        ACP acp = session.getACP(targetDocument.getRef());
        ACL acl = acp.getACL(collaborationIds[1]);
        if (acl == null) {
            return null;
        }

        // Get the changes
        ACE existingACE = acl.getACEs()[0];
        String updatedPermission = boxService.getNxBoxRole().inverse().get(boxCollaboration.getRole());
        BoxUser updatedUser = boxCollaboration.getAccessibleBy();
        String user = updatedUser != null ? updatedUser.getId() : existingACE.getUsername();
        String permission = updatedPermission != null ? updatedPermission : existingACE.getPermission();
        // Remove the existing ACE - creating the new one
        acl.remove(0);
        ACE ace = new ACE(user, permission, true);
        acl.add(ace);
        session.setACP(targetDocument.getRef(), acp, true);
        session.save();
        // Return the new box collab json
        BoxFolderAdapter boxFolderUpdated = (BoxFolderAdapter) targetDocument.getAdapter(BoxAdapter.class);
        return boxService.toJSONString(boxService.getBoxCollaboration(boxFolderUpdated, ace, collaborationIds[1]));
    }

}
