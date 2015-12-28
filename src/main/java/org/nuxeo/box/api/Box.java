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
 *     vpasquier <vpasquier@nuxeo.com>
 *     dmetzler <dmetzler@nuxeo.com>
 */

package org.nuxeo.box.api;

import org.nuxeo.box.api.marshalling.exceptions.BoxRestException;
import org.nuxeo.box.api.service.BoxService;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.core.api.DocumentNotFoundException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.exceptions.WebResourceNotFoundException;
import org.nuxeo.ecm.webengine.model.exceptions.WebSecurityException;
import org.nuxeo.ecm.webengine.model.impl.ModuleRoot;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Entry point for Nuxeo Box API
 *
 * @since 5.9.2
 */
@Path("/box/2.0{repo : (/repo/[^/]+?)?}")
@Produces("text/html;charset=UTF-8")
@WebObject(type = "box")
public class Box extends ModuleRoot {

    BoxService boxService;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
    }

    @Path("/")
    public Object doGetRepository(@PathParam("repo") final String repositoryParam) throws DocumentNotFoundException {
        if (StringUtils.isNotBlank(repositoryParam)) {
            String repoName = repositoryParam.substring("repo/".length() + 1);
            try {
                ctx.setRepositoryName(repoName);
            } catch (IllegalArgumentException e) {
                throw new WebResourceNotFoundException(e.getMessage());
            }
        }
        return newObject("repo");
    }

    @Path("/folders")
    public Object doGetFolder() {
        return newObject("folder");
    }

    @Path("/files")
    public Object doGetFile() {
        return newObject("file");
    }

    @Path("/search")
    public Object doGetSearch() {
        return newObject("search");
    }

    @Path("/comments")
    public Object doGetComment() {
        return newObject("comment");
    }

    @Path("/collaborations")
    public Object doGetCollaborations() {
        return newObject("collaborations");
    }

    /**
     * Return a Box compat Exception Response in JSON
     */
    @Override
    public Object handleError(final WebApplicationException e) {
        if (e instanceof WebSecurityException) {
            return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(
                    boxService.getJSONBoxException(e, Response.Status.UNAUTHORIZED.getStatusCode())).type(
                    "json/application").build();
        } else if (e instanceof WebResourceNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(
                    boxService.getJSONBoxException(e, Response.Status.NOT_FOUND.getStatusCode())).type(
                    "json/application").build();
        } else if (e instanceof BoxRestException) {
            return Response.status(((BoxRestException) e).getErrorCode()).entity(
                    boxService.getJSONBoxException(e, ((BoxRestException) e).getErrorCode())).type("json/application").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(
                    boxService.getJSONBoxException(e, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())).type(
                    "json/application").build();
        }
    }

}
