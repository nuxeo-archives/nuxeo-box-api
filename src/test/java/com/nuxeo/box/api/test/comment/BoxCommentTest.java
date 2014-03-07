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
package com.nuxeo.box.api.test.comment;

import com.nuxeo.box.api.marshalling.dao.BoxComment;
import com.nuxeo.box.api.marshalling.dao.BoxFile;
import com.nuxeo.box.api.test.BoxBaseTest;
import com.nuxeo.box.api.test.BoxServerFeature;
import com.nuxeo.box.api.test.BoxServerInit;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;
import org.nuxeo.runtime.test.runner.LocalDeploy;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @since 5.9.3
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Deploy({ "org.nuxeo.ecm.platform.comment", "org.nuxeo.ecm.platform.comment" +
        ".api", "org.nuxeo.ecm.relations.api", "org.nuxeo.ecm.relations",
        "org.nuxeo.ecm.relations.jena", "org.nuxeo.ecm.relations.io",
        "org.nuxeo.ecm.relations.core.listener", "org.nuxeo.ecm.platform" +
        ".comment.api" })
@LocalDeploy({ "com.nuxeo.box.api:comment-jena-contrib.xml" })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxCommentTest extends BoxBaseTest {

    @Test
    public void itCanManageComment() throws Exception {
        String commentId = itCanPostComment();
        itCanGetComment(commentId);
        itCanGetCommentsFromAFile(commentId);
        itCanUpdateComment(commentId);
        itCanDeleteComment(commentId);
    }

    public String itCanPostComment() throws Exception {
        // Fetching the file in Nuxeo way
        DocumentModel file = BoxServerInit.getFile(1, session);

        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> targetParams = new HashMap<>();

        // setting the message
        parameters.put("message", "A new comment on the file");

        // Setting the document target
        targetParams.put("id", file.getId());
        BoxFile targetFile = new BoxFile(targetParams);
        parameters.put("item", targetFile);

        BoxComment newBoxComment = new BoxComment(parameters);

        ClientResponse response = service.path("comments").post
                (ClientResponse.class, boxService.getJSONFromBox
                        (newBoxComment));

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals("A new comment on the file", finalResult.getString
                ("message"));

        // Posting with few properties
        response = service.path("comments").post(ClientResponse
                .class, "{\"item\": {\"type\": \"file\", " +
                "\"id\": \"" + file.getId() + "\"}," +
                " " +
                "\"message\": \"YOUR_MESSAGE\"}");

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        finalResult = getJSONFromResponse(response);
        assertEquals("YOUR_MESSAGE", finalResult.getString("message"));
        return finalResult.getString("id");
    }

    public void itCanGetComment(String commentId) throws Exception {
        // Fetching the file in Nuxeo way
        DocumentModel file = BoxServerInit.getFile(1, session);

        ClientResponse response = getResponse(RequestType.GET,
                "comments/" + commentId);

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(commentId, finalResult.getString("id"));
        assertEquals("YOUR_MESSAGE", finalResult.getString("message"));
        assertEquals(file.getId(),
                finalResult.getJSONObject("item").getString("id"));
    }

    public void itCanGetCommentsFromAFile(String commentId) throws
            ClientException,
            IOException, JSONException {
        // Fetching the file in Nuxeo way
        DocumentModel file = BoxServerInit.getFile(1, session);

        ClientResponse response = getResponse(RequestType.GET,
                "files/" + file.getId() + "/comments");

        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals("2", finalResult.getString("total_count"));
    }

    public void itCanUpdateComment(String commentId) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        // setting the message
        parameters.put("message", "COMMENT_UPDATE");

        BoxComment newBoxComment = new BoxComment(parameters);
        ClientResponse response = service.path("comments/" + commentId)
                .put(ClientResponse.class,
                        boxService.getJSONFromBox(newBoxComment));
        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JSONObject finalResult = getJSONFromResponse(response);
        assertEquals(finalResult.getString("message"), "COMMENT_UPDATE");

        // Posting with few properties
        response = service.path("comments/" + commentId).put(ClientResponse
                .class, "{\"message\":\"My New Message\"}");
        // Checking response consistency
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    public void itCanDeleteComment(String commentId) throws Exception {
        //Call delete on this folder
        ClientResponse response = service.path("comments/" + commentId)
                .delete(ClientResponse.class);
        // Checking response consistency
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(),
                response.getStatus());
        // Checking if folder is removed
        response = getResponse(BoxBaseTest.RequestType.GET,
                "comments/" + commentId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),
                response.getStatus());
    }

}
