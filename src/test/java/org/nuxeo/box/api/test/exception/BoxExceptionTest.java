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
package org.nuxeo.box.api.test.exception;

import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.box.api.test.BoxBaseTest;
import org.nuxeo.box.api.test.BoxServerFeature;
import org.nuxeo.box.api.test.BoxServerInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.webengine.model.TypeNotFoundException;
import org.nuxeo.ecm.webengine.model.exceptions.WebResourceNotFoundException;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * @since 5.9.2
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxExceptionTest extends BoxBaseTest {

    @Test
    public void itCanReturnJSONBoxException() throws Exception {
        //Get wrong folder id
        ClientResponse response = getResponse(RequestType.GET,
                "folders/blabla");
        // Checking response consistency
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),
                response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(404, finalResult.getInt("status"));
        assertEquals(WebResourceNotFoundException.class.getCanonicalName(),
                finalResult.getString("code"));

        // Get wrong endpoint
        response = getResponse(RequestType.GET, "folder");
        // Checking response consistency
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus());
        finalResult = getJSONFromResponse(response);
        assertEquals(500, finalResult.getInt("status"));
        assertEquals(TypeNotFoundException.class.getCanonicalName(),
                finalResult.getString("code"));

        // Posting with wrong property 'i' instead of 'id'
        response = service.path("folders").accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse
                        .class, "{\"name\":\"New Folder\", " +
                        "\"parent\": {\"i\": " +
                        "\"blabla\"}}");
        // Checking response consistency
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus());
        finalResult = getJSONFromResponse(response);
        assertEquals(500, finalResult.getInt("status"));
        assertEquals("Failed to get document null", finalResult.getString
                ("message"));
    }

}
