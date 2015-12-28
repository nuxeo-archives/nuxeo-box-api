/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package org.nuxeo.box.api.test.exception;

import org.nuxeo.box.api.test.BoxBaseTest;
import org.nuxeo.box.api.test.BoxServerFeature;
import org.nuxeo.box.api.test.BoxServerInit;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

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
        // Get wrong folder id
        ClientResponse response = service.path("folders/blabla").get(ClientResponse.class);
        // Checking response consistency
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(404, finalResult.getInt("status"));
        assertEquals("blabla", finalResult.getString("code"));

        // Get wrong endpoint
        response = service.path("folder").get(ClientResponse.class);
        // Checking response consistency
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        finalResult = getJSONFromResponse(response);
        assertEquals(500, finalResult.getInt("status"));
        assertEquals("Type not found: repo", finalResult.getString("code"));

        // Posting with wrong property 'i' instead of 'id'
        response = service.path("folders").post(ClientResponse.class,
                "{\"name\":\"New Folder\", \"parent\": {\"i\": " + "\"blabla\"}}");
        // Checking response consistency
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        finalResult = getJSONFromResponse(response);
        assertEquals(500, finalResult.getInt("status"));
        assertEquals("null reference", finalResult.getString("message"));
    }

}
