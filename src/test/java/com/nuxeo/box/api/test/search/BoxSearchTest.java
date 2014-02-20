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
package com.nuxeo.box.api.test.search;

import com.nuxeo.box.api.test.BoxBaseTest;
import com.nuxeo.box.api.test.BoxServerFeature;
import com.nuxeo.box.api.test.BoxServerInit;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * @since 5.9.2
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxSearchTest extends BoxBaseTest {

    @Test
    public void itCanSearch() throws Exception {
        // Searching in fulltext 'file' term
        ClientResponse response = service.path("search/").queryParam("query",
                "file").queryParam("limit", "2").queryParam("fields",
                "name").get(ClientResponse.class);
        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (response.getEntityInputStream()));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        JSONTokener tokener = new JSONTokener(builder.toString());
        JSONObject finalResult = new JSONObject(tokener);
        // Checking result
        assertEquals("1", finalResult.getString("total_count"));
    }

}
