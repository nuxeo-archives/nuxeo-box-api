package org.nuxeo.box.api.marshalling.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Email.
 */
public class BoxEmail extends BoxObject {

    public static final String FIELD_ACCESS = "access";

    public static final String FIELD_EMAIL = "email";

    public BoxEmail() {
    }

    public BoxEmail(BoxEmail obj) {
        super(obj);
    }

    public BoxEmail(Map<String, Object> map) {
        super(map);
    }

    /**
     * @return the access
     */
    @JsonProperty(FIELD_ACCESS)
    public String getAccess() {
        return (String) getValue(FIELD_ACCESS);
    }

    /**
     * @param access the access to set
     */
    @JsonProperty(FIELD_ACCESS)
    private void setAccess(String access) {
        put(FIELD_ACCESS, access);
    }

    /**
     * @return the email
     */
    @JsonProperty(FIELD_EMAIL)
    public String getEmail() {
        return (String) getValue(FIELD_EMAIL);
    }

    /**
     * @param email the email to set
     */
    @JsonProperty(FIELD_EMAIL)
    private void setEmail(String email) {
        put(FIELD_EMAIL, email);
    }
}
