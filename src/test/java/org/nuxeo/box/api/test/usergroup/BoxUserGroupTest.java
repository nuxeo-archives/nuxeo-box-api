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
 *     Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package org.nuxeo.box.api.test.usergroup;

import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.box.api.test.BoxBaseTest;
import org.nuxeo.box.api.test.BoxServerFeature;
import org.nuxeo.box.api.test.BoxServerInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @since 5.9.4
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxUserGroupTest extends BoxBaseTest {

    @Test
    public void itCanGetMe() throws IOException, JSONException {
        // Fetching the folder through NX Box API
        ClientResponse response = getResponse(BoxBaseTest.RequestType.GET,
                "users/me");
        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals("Administrator", finalResult.getString("login"));
    }

    @Test
    public void itCanManageUserGroup() throws Exception {
        String groupId = itCanPostGroup();
        itCanGetGroup(groupId);
        itCanUpdateGroup(groupId);
        String userId = itCanPostUser(groupId);
        itCanUpdateUser(userId);
        itCanGetAllUsers();
        itCanGetAllGroups();
        itCanDeleteGroup(groupId);
        itCanDeleteUser(userId);
    }


    protected void itCanDeleteUser(String userId) {

    }

    protected void itCanGetAllGroups() {

    }

    protected void itCanGetAllUsers() throws IOException, JSONException {
        // Fetching the folder through NX Box API
        ClientResponse response = getResponse(BoxBaseTest.RequestType.GET,
                "users");
        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals("1", finalResult.getString("total_count"));
    }

    protected void itCanUpdateUser(String userId) {

    }

    protected String itCanPostUser(String groupId) {
        return null;
    }

    protected String itCanPostGroup() throws Exception {
        return null;
    }

    protected void itCanGetGroup(String commentId) throws Exception {
    }

    protected void itCanUpdateGroup(String commentId) throws Exception {
    }

    protected void itCanDeleteGroup(String commentId) throws Exception {
    }

}
