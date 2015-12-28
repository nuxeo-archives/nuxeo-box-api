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
package org.nuxeo.box.api.marshalling.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Version of a file.
 */
public class BoxServiceAction extends BoxObject {

    public static final String FIELD_ID = "id";

    public static final String FIELD_NAME = "name";

    public BoxServiceAction() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxServiceAction(BoxServiceAction obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxServiceAction(Map<String, Object> map) {
        super(map);
    }

    /**
     * Get id.
     *
     * @return id
     */
    @JsonProperty(FIELD_ID)
    public String getId() {
        return (String) getValue(FIELD_ID);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param id id
     */
    @JsonProperty(FIELD_ID)
    private void setId(String id) {
        put(FIELD_ID, id);
    }

    @JsonProperty(FIELD_NAME)
    public String getCreatedBy() {
        return (String) getValue(FIELD_NAME);
    }

    @JsonProperty(FIELD_NAME)
    public void getName(String name) {
        put(FIELD_NAME, name);
    }

}
