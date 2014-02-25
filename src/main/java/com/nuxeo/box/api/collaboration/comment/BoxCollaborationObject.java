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
package com.nuxeo.box.api.collaboration.comment;

import com.nuxeo.box.api.collaboration.comment.adapter.BoxCollaborationAdapter;
import com.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import com.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.webengine.WebException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * WebObject for a Box Collaboration
 *
 * @since 5.9.3
 */
@WebObject(type = "collaboration")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxCollaborationObject extends AbstractResource<ResourceTypeImpl> {


    BoxService boxService;

    DocumentModel folder;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
        assert args != null && args.length == 1;
        try {
            String folderId = (String) args[0];
            CoreSession session = ctx.getCoreSession();
            folder = session.getDocument(new IdRef(folderId));
        } catch (Exception e) {
            throw WebException.wrap(e);
        }
        setRoot(true);
    }

    @GET
    public String doGetCollaboration() throws NoSuchDocumentException,
            ClientException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        // Adapt nx document to box collaboration adapter
        final BoxCollaborationAdapter boxCollaboration = folder.getAdapter
                (BoxCollaborationAdapter.class);
        return boxService.toJSONString(boxCollaboration.getBoxCollaboration());
    }

}
