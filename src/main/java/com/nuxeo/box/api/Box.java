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
 *     vpasquier <vpasquier@nuxeo.com>
 *     dmetzler <dmetzler@nuxeo.com>
 */

package com.nuxeo.box.api;

import com.nuxeo.box.api.service.BoxService;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
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
    public Object doGetRepository(@PathParam("repo")
    final String repositoryParam) throws NoSuchDocumentException {
        if (StringUtils.isNotBlank(repositoryParam)) {
            String repoName = repositoryParam.substring("repo/".length() + 1);
            try {
                ctx.setRepositoryName(repoName);
            } catch (final ClientException e) {
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


    /**
     * Return a Box compat Exception Response in JSON
     */
    @Override
    public Object handleError(final WebApplicationException e) {
        if (e instanceof WebSecurityException) {
            return Response.status(Response.Status.UNAUTHORIZED.getStatusCode
                    ()).entity
                    (boxService.getJSONBoxException(e, Response.Status
                            .UNAUTHORIZED
                            .getStatusCode())).type(
                    "json/application").build();
        } else if (e instanceof WebResourceNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity
                            (boxService.getJSONBoxException(e,
                                    Response.Status.NOT_FOUND
                                            .getStatusCode())).type(
                            "json/application").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR
                    .getStatusCode()).entity
                    (boxService.getJSONBoxException(e,
                            Response.Status.INTERNAL_SERVER_ERROR
                                    .getStatusCode())).type(
                    "json/application").build();
        }
    }

}
