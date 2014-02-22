package com.nuxeo.box.api.marshalling.dao;


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
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
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
