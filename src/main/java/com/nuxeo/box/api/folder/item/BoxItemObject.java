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

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.webengine.WebException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * WebObject for a Box Folder Item
 *
 * @since 5.9.2
 */
@WebObject(type = "item")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxItemObject extends AbstractResource<ResourceTypeImpl> {

    DocumentModel folder;

    @Override
    public void initialize(Object... args) {
        assert args != null && args.length == 1;
        try {
            String folderId = (String) args[0];
            folder = ctx.getCoreSession().getDocument(new IdRef(folderId));
        } catch (Exception e) {
            throw WebException.wrap(e);
        }
        setRoot(true);
    }

    @GET
    public Object doGet() {
        return getView("index");
    }

}
