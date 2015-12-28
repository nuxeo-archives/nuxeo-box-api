/*
 * Copyright 2013 Box, Inc. All rights reserved.
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
 */
package org.nuxeo.box.api.marshalling.jsonentities;

import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.marshalling.interfaces.IBoxJSONParser;
import org.nuxeo.box.api.marshalling.interfaces.IBoxJSONStringEntity;

import java.util.LinkedHashMap;

/**
 * Implemenation of {@link org.nuxeo.box.api.marshalling.interfaces.IBoxJSONStringEntity} based on LinkedHashMap.
 */
public class MapJSONStringEntity extends LinkedHashMap<String, Object> implements IBoxJSONStringEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public String toJSONString(IBoxJSONParser parser) throws BoxJSONException {
        return parser.convertBoxObjectToJSONStringQuietly(this);
    }

}
