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

import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.model.NoSuchDocumentException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.exceptions.WebResourceNotFoundException;
import org.nuxeo.ecm.webengine.model.exceptions.WebSecurityException;
import org.nuxeo.ecm.webengine.model.impl.ModuleRoot;

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

    @Override
    public Object handleError(final WebApplicationException e) {
        if (e instanceof WebSecurityException) {
            return Response.status(401).entity("not authorized").type(
                    "text/plain").build();
        } else if (e instanceof WebResourceNotFoundException) {
            return Response.status(404).entity(e.getMessage()).type(
                    "text/plain").build();
        } else {
            return super.handleError(e);
        }
    }

}
