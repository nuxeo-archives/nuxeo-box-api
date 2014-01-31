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
package com.nuxeo.box.api.test.folder;

import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.restclientv2.exceptions.BoxRestException;
import com.google.inject.Inject;
import com.nuxeo.box.api.folder.io.BoxFolderAdapter;
import com.nuxeo.box.api.test.BoxBaseTest;
import com.nuxeo.box.api.test.BoxServerFeature;
import com.nuxeo.box.api.test.BoxServerInit;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @since 5.9.2
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxFolderTest extends BoxBaseTest {

    @Inject
    CoreSession session;

    @Test
    public void itCanFetchABoxFolderWithCommonClient() throws Exception {

        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        // Fetching the folder through NX Box API
        ClientResponse response = getResponse(BoxBaseTest.RequestType.GET,
                "folders/" + folder.getId());

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        JSONTokener tokener = new JSONTokener(builder.toString());
        JSONObject finalResult = new JSONObject(tokener);
        assertEquals(finalResult.getString("item_status"), "project");
    }

    @Ignore
    @Test
    //TODO NXIO-65: activate it after OAuth testing
    public void itCanFetchABoxFolderWithBoxClient() throws BoxServerException, AuthFatalFailureException, BoxRestException, ClientException {
        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        // Fetching the folder through Box Client
        BoxFolder boxFolder = boxClient.getFoldersManager().getFolder("e438d0c4-0b7e-4272-986c-513b3b61a796", null);
        assertNotNull(boxFolder);
    }

    @Test
    public void itCanPostABoxFolder() throws ClientException, BoxJSONException, IOException, JSONException {
        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> parentParams = new HashMap<>();
        parentParams.put("id", folder.getId());
        BoxFolder parentBoxFolder = new BoxFolder(parentParams);
        parameters.put("parent", parentBoxFolder);
        parameters.put("name", "new_child_folder");

        BoxFolder newBoxFolder = new BoxFolder(parameters);

        ClientResponse response = service.path("folders").post(ClientResponse.class, newBoxFolder.toJSONString(new BoxJSONParser(new BoxResourceHub())));

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        JSONTokener tokener = new JSONTokener(builder.toString());
        JSONObject finalResult = new JSONObject(tokener);
        assertEquals(finalResult.getString("item_status"), "project");
    }

    @Test
    public void itCanUpdateABoxFolder() throws ClientException, BoxJSONException, IOException, JSONException {
        // Fetching the folder in Nuxeo way
        final DocumentModel folder = BoxServerInit.getFolder(1, session);

        BoxFolderAdapter folderAdapter = folder.getAdapter(BoxFolderAdapter.class);
        BoxFolder boxFolderUpdated = folderAdapter.newBoxInstance(session);

        // Default name checking
        assertEquals(boxFolderUpdated.getName(), "folder_1");

        // Update the name of the folder
        boxFolderUpdated.put("name", "newName");

        final ClientResponse response = service.path("folders/" + folder.getId()).put(ClientResponse.class, boxFolderUpdated.toJSONString(new BoxJSONParser(new BoxResourceHub())));

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        JSONTokener tokener = new JSONTokener(builder.toString());
        JSONObject finalResult = new JSONObject(tokener);
        assertEquals(finalResult.getString("name"), "newName");
    }
}
