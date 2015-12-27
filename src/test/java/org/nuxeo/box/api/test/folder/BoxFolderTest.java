/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and others.
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
package org.nuxeo.box.api.test.folder;

import org.nuxeo.box.api.adapter.BoxAdapter;
import org.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import org.nuxeo.box.api.marshalling.dao.BoxFolder;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.test.BoxBaseTest;
import org.nuxeo.box.api.test.BoxServerFeature;
import org.nuxeo.box.api.test.BoxServerInit;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @since 5.9.2
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxFolderTest extends BoxBaseTest {

    @Test
    public void itCanFetchABoxFolder() throws Exception {

        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        // Fetching the folder through NX Box API
        ClientResponse response = getResponse(BoxBaseTest.RequestType.GET, "folders/" + folder.getId());

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(finalResult.getString("sequence_id"), finalResult.getString("id"));
        assertEquals("project", finalResult.getString("item_status"));
        assertEquals("/", finalResult.getJSONObject("parent").getString("name"));
        assertEquals("null", finalResult.getJSONObject("parent").getString("etag"));
        assertEquals("null", finalResult.getJSONObject("parent").getString("sequence_id"));
    }

    @Test
    public void itCanFetchABoxRoot() throws Exception {

        // Fetching the root through NX Box API
        ClientResponse response = getResponse(BoxBaseTest.RequestType.GET, "folders/0");

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals("0", finalResult.getString("id"));
    }

    @Test
    public void itCanPostABoxFolder() throws BoxJSONException, IOException, JSONException {
        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> parentParams = new HashMap<>();
        parentParams.put("id", folder.getId());
        BoxFolder parentBoxFolder = new BoxFolder(parentParams);
        parameters.put("parent", parentBoxFolder);
        parameters.put("name", "new_child_folder");

        BoxFolder newBoxFolder = new BoxFolder(parameters);

        ClientResponse response = service.path("folders").post(ClientResponse.class,
                boxService.getJSONFromBox(newBoxFolder));

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals("project", finalResult.getString("item_status"));

        // Posting with few properties
        response = service.path("folders").post(ClientResponse.class,
                "{\"name\":\"New Folder\", \"parent\": {\"id\": " + "\"0\"}}");

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void itCanUpdateABoxFolder() throws BoxJSONException, IOException, JSONException {
        // Fetching the folder in Nuxeo way
        final DocumentModel folder = BoxServerInit.getFolder(1, session);

        BoxFolderAdapter folderAdapter = (BoxFolderAdapter) folder.getAdapter(BoxAdapter.class);
        BoxFolder boxFolderUpdated = (BoxFolder) folderAdapter.getBoxItem();

        // Default name checking
        assertEquals("folder_1", boxFolderUpdated.getName());

        // Update the name of the folder
        boxFolderUpdated.put("name", "newName");

        final ClientResponse response = service.path("folders/" + folder.getId()).put(ClientResponse.class,
                boxService.getJSONFromBox(boxFolderUpdated));

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals("newName", finalResult.getString("name"));
    }

    @Test
    public void itCanDeleteABoxFolder() {
        // Fetching the folder in Nuxeo way
        final DocumentModel folder = BoxServerInit.getFolder(1, session);
        // Call delete on this folder
        ClientResponse response = service.path("folders/" + folder.getId()).delete(ClientResponse.class);
        // Checking response consistency
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        // Checking if folder is removed
        response = getResponse(BoxBaseTest.RequestType.GET, "folders/" + folder.getId());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
