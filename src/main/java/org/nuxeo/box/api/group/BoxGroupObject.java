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

import org.nuxeo.box.api.marshalling.dao.BoxCollection;
import org.nuxeo.box.api.marshalling.dao.BoxGroup;
import org.nuxeo.box.api.marshalling.dao.BoxTypedObject;
import org.nuxeo.box.api.marshalling.dao.BoxUser;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
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
 * WebObject for a Box Group
 *
 * @since 5.9.4
 */
@WebObject(type = "groups")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxGroupObject extends AbstractResource<ResourceTypeImpl> {

    BoxService boxService;

    UserManager userManager;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
        userManager = Framework.getLocalService(UserManager.class);
    }

    @GET
    public String doGetAllGroups() throws ClientException,
            BoxJSONException {
        List<String> groupIds = userManager.getGroupIds();
        List<BoxTypedObject> boxGroups = new ArrayList<>();
        for (String groupId : groupIds) {
            NuxeoGroup nuxeoGroup = userManager.getGroup(groupId);
            BoxGroup boxGroup = boxService.getMiniGroup(nuxeoGroup);
            boxGroups.add(boxGroup);
        }
        final Map<String, Object> boxGroupMap = new HashMap<>();
        boxGroupMap.put(BoxCollection.FIELD_ENTRIES, boxGroups);
        boxGroupMap.put(BoxCollection.FIELD_TOTAL_COUNT,
                boxGroups.size());
        BoxCollection boxGroupCollection = new BoxCollection(Collections
                .unmodifiableMap(boxGroupMap));
        return boxService.toJSONString(boxGroupCollection);
    }

    @POST
    public String doPostGroup(String jsonBoxGroup) throws ClientException,
            BoxJSONException {
        BoxGroup boxGroup = boxService.getBoxGroup(jsonBoxGroup);
        final CoreSession session = ctx.getCoreSession();
        DocumentModel nuxeoGroup = session.createDocumentModel("Group");
        nuxeoGroup.setPropertyValue("dc:title", boxGroup.getName());
        session.createDocument(nuxeoGroup);
        session.save();
        DocumentModel groupModel = userManager.createGroup(nuxeoGroup);
        boxGroup = boxService.getMiniGroup(userManager.getGroup(groupModel.getId
                ()));
        return boxService.toJSONString(boxGroup);
    }

    @PUT
    @Path("{groupId}")
    public String doPutGroup(@PathParam("groupId") String groupId,
            String jsonBoxGroup) throws ClientException, BoxJSONException,
            ParseException, IllegalAccessException, InvocationTargetException {
        return "";
    }

    @DELETE
    @Path("{groupId}")
    public void doDeleteGroup(@PathParam("groupId") String groupId) throws
            ClientException {
        userManager.deleteGroup(groupId);
    }

    @Path("{groupId}/memberships")
    public Object doGetGroupMemberships(@PathParam("groupId") String
            groupId) throws ClientException, BoxJSONException {
        NuxeoGroup nuxeoGroup = userManager.getGroup(groupId);
        List<String> userIds = nuxeoGroup.getMemberUsers();
        List<BoxTypedObject> boxUsers = new ArrayList<>();
        for (String userId : userIds) {
            NuxeoPrincipal user = userManager.getPrincipal(userId);
            BoxUser boxUser = boxService.getMiniUser(user);
            boxUsers.add(boxUser);
        }
        final Map<String, Object> boxUserMap = new HashMap<>();
        boxUserMap.put(BoxCollection.FIELD_ENTRIES, boxUsers);
        boxUserMap.put(BoxCollection.FIELD_TOTAL_COUNT,
                boxUsers.size());
        BoxCollection boxUserCollection = new BoxCollection(Collections
                .unmodifiableMap(boxUserMap));
        return boxService.toJSONString(boxUserCollection);
    }

}
