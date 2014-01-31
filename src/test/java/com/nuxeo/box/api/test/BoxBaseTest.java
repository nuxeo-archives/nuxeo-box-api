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
 *     Damien Metzler <dmetzler@nuxeo.com>
 *     Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package com.nuxeo.box.api.test;

import com.box.boxjavalibv2.BoxClient;
import com.box.boxjavalibv2.BoxConfig;
import com.box.boxjavalibv2.dao.BoxOAuthToken;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.requestobjects.BoxOAuthRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @since 5.9.2
 */
public class BoxBaseTest {

    public static final int PORT = 4000;

    public static final String key = "arnal";

    public static final String secret = "vlado";

    public static final String redirect_uri = "blabla";


    protected BoxClient boxClient;

    protected ObjectMapper mapper;

    protected WebResource service;

    protected BoxClient getAuthenticatedClient(String code) throws
            BoxRestException, BoxServerException, AuthFatalFailureException {
        BoxClient client = new BoxClient(key, secret);
        BoxOAuthRequestObject obj = BoxOAuthRequestObject
                .createOAuthRequestObject(code, key, secret, redirect_uri);
        BoxOAuthToken bt = client.getOAuthManager().createOAuth(obj);
        client.authenticate(bt);
        return client;
    }

    // TODO NXIO-65: activate it to test with Box client and NX OAuth
    public void initBoxClient() throws AuthFatalFailureException,
            BoxServerException, BoxRestException {

        String code = "D2MT5whdEw";

        BoxConfig boxConfig = BoxConfig.getInstance();

        boxConfig.setAuthUrlScheme("http");
        boxConfig.setOAuthApiUrlPath("/nuxeo");
        boxConfig.setOAuthUrlAuthority("10.213.3.200:8080");

        boxConfig.setApiUrlScheme("http");
        boxConfig.setApiUrlPath("/nuxeo/site/box/2.0");
        boxConfig.setApiUrlAuthority("10.213.3.200:8080");

        boxClient = getAuthenticatedClient(code);

    }

    @Before
    public void doBefore() throws Exception {
        service = getServiceFor("Administrator", "Administrator");
        mapper = new ObjectMapper();
        // TODO NXIO-65: activate it to test with Box client and NX OAuth
        //initBoxClient();
    }

    protected WebResource getServiceFor(String user, String password) {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(MultiPartWriter.class);
        Client client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(user, password));
        return client.resource("http://localhost:18090/box/2.0/");
    }

    protected ClientResponse getResponse(RequestType requestType, String path) {
        return getResponse(requestType, path, null, null, null);
    }

    protected ClientResponse getResponse(RequestType requestType, String path,
            String data, MultivaluedMap<String, String> queryParams,
            MultiPart mp) {
        WebResource wr = service.path(path);

        if (queryParams != null && !queryParams.isEmpty()) {
            wr = wr.queryParams(queryParams);
        }

        Builder builder = wr.accept(MediaType.APPLICATION_JSON) //
                .header("X-NXDocumentProperties", "dublincore");
        if (mp != null) {
            builder = wr.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        }

        if (requestType == RequestType.POSTREQUEST) {
            builder.header("Content-type", "application/json+nxrequest");
        } else {
            builder.header("Content-type", "application/json+nxentity");
        }

        switch (requestType) {
        case GET:
            return builder.get(ClientResponse.class);
        case POST:
        case POSTREQUEST:
            if (mp != null) {
                return builder.post(ClientResponse.class, mp);
            } else {
                return builder.post(ClientResponse.class, data);
            }
        case PUT:
            if (mp != null) {
                return builder.put(ClientResponse.class, mp);
            } else {
                return builder.put(ClientResponse.class, data);
            }
        case DELETE:
            return builder.delete(ClientResponse.class, data);
        default:
            throw new RuntimeException();
        }
    }

    protected String getCode() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket
                .getInputStream()));
        while (true) {
            String code = StringUtils.EMPTY;
            code = in.readLine();
            String match = "code";
            int loc = code.indexOf(match);
            if (loc > 0) {
                int httpstr = code.indexOf("HTTP") - 1;
                code = code.substring(code.indexOf(match), httpstr);
                String parts[] = code.split("=");
                code = parts[1];
            }
            return code;
        }
    }

    protected static enum RequestType {
        GET, POST, DELETE, PUT, POSTREQUEST
    }

}

