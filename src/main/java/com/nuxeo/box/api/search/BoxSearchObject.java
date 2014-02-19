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
package com.nuxeo.box.api.search;

import com.nuxeo.box.api.exceptions.BoxJSONException;
import com.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * WebObject for a Box Search
 *
 * @since 5.9.3
 */
@WebObject(type = "search")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxSearchObject extends AbstractResource<ResourceTypeImpl> {

    /**
     * The string in query to search for; can be matched against item names,
     * descriptions, text content of a file, and other fields of the
     * different item types.
     */
    @GET
    public String doSearch(@QueryParam("query") String query,
            @QueryParam("offset") String offset,
            @QueryParam("limit") String limit) throws ClientException,
            BoxJSONException,
            ParseException, IllegalAccessException, InvocationTargetException {
        final CoreSession session = ctx.getCoreSession();
        DocumentModelList documentModelList = session.query("SELECT * FROM " +
                "Document where ecm:fulltext='" + query + "'");
        BoxService boxService = Framework.getLocalService(BoxService.class);
        return boxService.toJSONString(boxService.getBoxCollection
                (documentModelList));
    }

}
