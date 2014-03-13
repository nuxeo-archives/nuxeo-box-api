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
package org.nuxeo.box.api.test.file;

import com.google.inject.Inject;
import org.nuxeo.box.api.adapter.BoxAdapter;
import org.nuxeo.box.api.marshalling.dao.BoxFile;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.file.adapter.BoxFileAdapter;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.box.api.test.BoxBaseTest;
import org.nuxeo.box.api.test.BoxServerFeature;
import org.nuxeo.box.api.test.BoxServerInit;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @since 5.9.2
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxFileTest extends BoxBaseTest {

    @Inject
    CoreSession session;

    @Inject
    BoxService boxService;

    @Test
    public void itCanFetchABoxFile() throws Exception {

        // Fetching the file in Nuxeo way
        DocumentModel file = BoxServerInit.getFile(1, session);

        // Fetching the file through NX Box API
        ClientResponse response = getResponse(RequestType.GET,
                "files/" + file.getId());

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(finalResult.getString("item_status"), "project");
    }

    @Test
    public void itCanDeleteABoxFile() throws ClientException {
        // Fetching the file in Nuxeo way
        final DocumentModel file = BoxServerInit.getFile(1, session);
        //Call delete on this file
        ClientResponse response = service.path("files/" + file.getId())
                .delete(ClientResponse.class);
        // Checking response consistency
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(),
                response.getStatus());
        // Checking if folder is removed
        response = getResponse(BoxBaseTest.RequestType.GET,
                "files/" + file.getId());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),
                response.getStatus());
    }

    @Test
    public void itCanUpdateABoxFile() throws ClientException,
            BoxJSONException, IOException, JSONException {
        // Fetching the File in Nuxeo way
        final DocumentModel File = BoxServerInit.getFile(1, session);

        BoxFileAdapter FileAdapter = (BoxFileAdapter) File.getAdapter
                (BoxAdapter.class);
        BoxFile boxFileUpdated = (BoxFile) FileAdapter.getBoxItem();

        // Default name checking
        assertEquals(boxFileUpdated.getName(), "file");

        // Update the name of the File
        boxFileUpdated.put("name", "newName");

        ClientResponse response = service.path("files/" + File
                .getId()).put(ClientResponse.class, boxService.getJSONFromBox
                (boxFileUpdated));

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(finalResult.getString("name"), "newName");

        // Putting with few properties
        response = service.path("files/" + File.getId()).put(ClientResponse
                .class, "{\"name\":\"new name.jpg\"}");
        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void itCanCreateABoxFile() throws ClientException, IOException,
            JSONException {
        // Setting the parent
        DocumentModel folder = BoxServerInit.getFolder(1, session);
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        formDataMultiPart.field("parent_id", folder.getId());
        // Setting the blob and name
        File file = org.nuxeo.common.utils.FileUtils
                .getResourceFileFromContext("blob.json");
        formDataMultiPart.field("filename", file.getName());
        FormDataBodyPart bodyPart = new FormDataBodyPart("file",
                new ByteArrayInputStream(FileUtils.readFileToByteArray(file)),
                MediaType.APPLICATION_OCTET_STREAM_TYPE);
        formDataMultiPart.bodyPart(bodyPart);

        final ClientResponse response = service.path("files/content").type
                (MediaType
                        .MULTIPART_FORM_DATA)
                .post(ClientResponse.class, formDataMultiPart);
        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(finalResult.getString("name"), "blob.json");
    }
}
