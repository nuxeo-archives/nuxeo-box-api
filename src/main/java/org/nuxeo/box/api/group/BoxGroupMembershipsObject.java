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
package org.nuxeo.box.api.group;

import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.IdRef;
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
 * WebObject for a Box Group Memberships
 *
 * @since 5.9.4
 */
@WebObject(type = "group_memberships")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxGroupMembershipsObject extends
        AbstractResource<ResourceTypeImpl> {

    BoxService boxService;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
    }

    @GET
    @Path("{groupId}/memberships")
    public Object doGetGroupMemberships(@PathParam("groupId") String groupId) {
        return newObject("groups", groupId);
    }

    @POST
    public String doPostMemberships(String jsonBoxMemberships) throws
            ClientException,
            BoxJSONException {
        return "";
    }

    @PUT
    @Path("{membershipsId}")
    public String doPutMemberships(@PathParam("membershipsId") String membershipsId,
            String jsonBoxMemberships) throws ClientException, BoxJSONException,
            ParseException, IllegalAccessException, InvocationTargetException {
        return "";
    }

    @DELETE
    @Path("{membershipsId}")
    public void doDeleteGroup(@PathParam("membershipsId") String
            membershipsId) throws
            ClientException {
        final CoreSession session = ctx.getCoreSession();
        session.removeDocument(new IdRef(membershipsId));
        session.save();
    }

}
