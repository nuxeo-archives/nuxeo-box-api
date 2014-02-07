package com.nuxeo.box.api.dao;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Enterprise.
 */
@SuppressWarnings("unused")
public class BoxEnterprise extends BoxObject {

    public static final String FIELD_NAME = "name";

    public BoxEnterprise() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxEnterprise(BoxEnterprise obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxEnterprise(Map<String, Object> map) {
        super(map);
    }

    private String name;

    /**
     * Get name of user's enterprise.
     *
     * @return name
     */
    @JsonProperty(FIELD_NAME)
    public String getName() {
        return (String) getValue(FIELD_NAME);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param name name
     */

    @JsonProperty(FIELD_NAME)
    private void setName(String name) {
        put(FIELD_NAME, name);
    }
}
