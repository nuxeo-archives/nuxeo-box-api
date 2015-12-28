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
package org.nuxeo.box.api.search;

import com.google.common.base.Objects;
import org.nuxeo.box.api.BoxConstants;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.service.BoxService;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * WebObject for a Box Search
 *
 * @since 5.9.3
 */
@WebObject(type = "search")
@Produces({ MediaType.APPLICATION_JSON })
public class BoxSearchObject extends AbstractResource<ResourceTypeImpl> {

    BoxService boxService;

    @Override
    public void initialize(Object... args) {
        boxService = Framework.getLocalService(BoxService.class);
    }

    /**
     * The string in query to search for; can be matched against item names, descriptions, text content of a file, and
     * other fields of the different item types.
     */
    @GET
    public String doSearch(@QueryParam("query") String query, @QueryParam("offset") String offset,
            @QueryParam("limit") String limit) throws BoxJSONException {
        return boxService.toJSONString(boxService.searchBox(query, ctx.getCoreSession(),
                Objects.firstNonNull(limit, BoxConstants.BOX_LIMIT),
                Objects.firstNonNull(offset, BoxConstants.BOX_OFFSET)));
    }

}
