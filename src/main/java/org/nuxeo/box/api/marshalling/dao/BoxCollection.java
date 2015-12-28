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

public class BoxCollection extends BoxCollectionBase {

    public static final String FIELD_TOTAL_COUNT = "total_count";

    public BoxCollection() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxCollection(BoxCollection obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxCollection(Map<String, Object> map) {
        super(map);
    }

    /**
     * @return the total_count
     */
    @JsonProperty("total_count")
    public Integer getTotalCount() {
        return (Integer) getValue(FIELD_TOTAL_COUNT);
    }

    /**
     * @param totalCount the total_count to set
     */
    @JsonProperty("total_count")
    private void setTotalCount(Integer totalCount) {
        put(FIELD_TOTAL_COUNT, totalCount);
    }
}
