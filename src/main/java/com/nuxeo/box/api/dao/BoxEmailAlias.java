package com.nuxeo.box.api.dao;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BoxEmailAlias extends BoxTypedObject {

    public static final String FIELD_IS_CONFIRMED = "is_confirmed";

    public static final String FIELD_EMAIL = "email";

    public BoxEmailAlias() {
        setType(BoxResourceType.EMAIL_ALIAS.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxEmailAlias(BoxEmailAlias obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxEmailAlias(Map<String, Object> map) {
        super(map);
    }

    @JsonProperty(FIELD_IS_CONFIRMED)
    public Boolean isConfirmed() {
        return (Boolean) getValue(FIELD_IS_CONFIRMED);
    }

    @JsonProperty(FIELD_IS_CONFIRMED)
    private void setIsConfirmed(Boolean isConfirmed) {
        put(FIELD_IS_CONFIRMED, isConfirmed);
    }

    @JsonProperty(FIELD_EMAIL)
    public String getEmail() {
        return (String) getValue(FIELD_EMAIL);
    }

    @JsonProperty(FIELD_EMAIL)
    private void setEmail(String email) {
        put(FIELD_EMAIL, email);
    }
}
