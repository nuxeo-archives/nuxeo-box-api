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
package org.nuxeo.box.api.test.folder.item;

import org.nuxeo.box.api.test.BoxBaseTest;
import org.nuxeo.box.api.test.BoxServerFeature;
import org.nuxeo.box.api.test.BoxServerInit;
import com.sun.jersey.api.client.ClientResponse;
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

import static org.junit.Assert.assertEquals;

/**
 * @since 5.9.2
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxItemTest extends BoxBaseTest {

    @Test
    public void itCanFetchAllBoxItemsWithAllProperties() throws Exception {
        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        ClientResponse response = service.path("folders/" + folder.getId() +
                "/items").get(ClientResponse.class);

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(finalResult.getString("total_count"), "5");
        assertEquals(((JSONObject) finalResult.getJSONArray("entries").get(0)
        ).get("etag"), ((JSONObject) finalResult.getJSONArray("entries").get
                (0)).get("sequence_id") + "_0.0");
    }

    @Test
    public void itCanFetchAllBoxItemsWithOptions() throws Exception {
        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        ClientResponse response = service.path("folders/" + folder.getId() +
                "/items").queryParam("limit", "2").queryParam("fields",
                "name").get(ClientResponse.class);

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(finalResult.getString("total_count"), "2");
        assertEquals(((JSONObject) finalResult.getJSONArray("entries").get(0)
        ).get("etag"), null);
    }


}
