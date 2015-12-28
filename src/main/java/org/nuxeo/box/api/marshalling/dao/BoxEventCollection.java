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

public class BoxEventCollection extends BoxCollectionBase {

    public static final String FIELD_CHUNK_SIZE = "chunk_size";

    public static final String FIELD_NEXT_STREAM_POSITION = "next_stream_position";

    public BoxEventCollection() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxEventCollection(BoxEventCollection obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxEventCollection(Map<String, Object> map) {
        super(map);
    }

    /**
     * @return the chunk size
     */
    @JsonProperty(FIELD_CHUNK_SIZE)
    public Integer getChunkSize() {
        return (Integer) getValue(FIELD_CHUNK_SIZE);
    }

    /**
     * @param chunkSize size the chunk size
     */
    @JsonProperty(FIELD_CHUNK_SIZE)
    private void setChunkSize(Integer chunkSize) {
        put(FIELD_CHUNK_SIZE, chunkSize);
    }

    /**
     * @return the next stream_position
     */
    @JsonProperty(FIELD_NEXT_STREAM_POSITION)
    public Long getNextStreamPosition() {
        return (Long) getValue(FIELD_NEXT_STREAM_POSITION);
    }

    /**
     * @param nextStreamPosition the next stream position
     */
    @JsonProperty(FIELD_NEXT_STREAM_POSITION)
    private void setNextStreamPosition(Long nextStreamPosition) {
        put(FIELD_NEXT_STREAM_POSITION, nextStreamPosition);
    }
}
