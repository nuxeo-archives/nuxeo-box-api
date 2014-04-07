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
package org.nuxeo.box.api.user;

import org.nuxeo.box.api.marshalling.dao.BoxCollection;
import org.nuxeo.box.api.marshalling.dao.BoxGroup;
import org.nuxeo.box.api.marshalling.dao.BoxTypedObject;
import org.nuxeo.box.api.marshalling.dao.BoxUser;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.NuxeoGroup;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.usermanager.UserManager;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebObject for a Box User
 *
 * @since 5.9.3
 */
@WebObject(type = "users")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxUserObject extends AbstractResource<ResourceTypeImpl> {

    BoxService boxService;

    UserManager userManager;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
        userManager = Framework.getLocalService(UserManager.class);
    }

    @GET
    @Path("me")
    public String doGetMyself() throws ClientException,
            BoxJSONException {
        final CoreSession session = ctx.getCoreSession();
        NuxeoPrincipal me = userManager.getPrincipal(session.getPrincipal()
                .getName());
        BoxUser boxUser = boxService.getMiniUser(me);
        return boxService.toJSONString(boxUser);
    }

    @GET
    public String doGetAllUsers() throws ClientException,
            BoxJSONException {
        List<String> userIds = userManager.getUserIds();
        List<BoxTypedObject> boxUsers = new ArrayList<>();
        for (String userId : userIds) {
            NuxeoGroup nuxeoGroup = userManager.getGroup(userId);
            BoxGroup boxGroup = boxService.getMiniGroup(nuxeoGroup);
            boxUsers.add(boxGroup);
        }
        final Map<String, Object> boxUserMap = new HashMap<>();
        boxUserMap.put(BoxCollection.FIELD_ENTRIES, boxUsers);
        boxUserMap.put(BoxCollection.FIELD_TOTAL_COUNT,
                boxUsers.size());
        BoxCollection boxUserCollection = new BoxCollection(Collections
                .unmodifiableMap(boxUserMap));
        return boxService.toJSONString(boxUserCollection);
    }

    @POST
    public String doPostUser(String jsonBoxUser) throws ClientException,
            BoxJSONException {
        return "";
    }

    @PUT
    @Path("{userId}")
    public String doPutUser(@PathParam("userId") String userId,
            String jsonBoxUser) throws ClientException, BoxJSONException,
            ParseException, IllegalAccessException, InvocationTargetException {
        return "";
    }

    @DELETE
    @Path("{userId}")
    public void doDeleteUser(@PathParam("userId") String userId) throws
            ClientException {
        userManager.deleteUser(userId);
    }

}
