package com.nuxeo.box.api.marshalling.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Class for errors sent from server.
 */
public class BoxServerError extends BoxTypedObject {

    public static final String FIELD_STATUS = "status";

    public static final String FIELD_CODE = "code";

    public static final String FIELD_HELP_URL = "help_url";

    public static final String FIELD_MESSAGE = "message";

    public static final String FIELD_REQUEST_ID = "request_id";

    public BoxServerError() {
        setType(BoxResourceType.ERROR.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxServerError(BoxServerError obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxServerError(Map<String, Object> map) {
        super(map);
    }

    /**
     * @return the status
     */
    @JsonProperty(FIELD_STATUS)
    public Integer getStatus() {
        return (Integer) getValue(FIELD_STATUS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param status the status to set
     */
    @JsonProperty(FIELD_STATUS)
    public void setStatus(Integer status) {
        put(FIELD_STATUS, status);
    }

    /**
     * @return the code
     */
    @JsonProperty(FIELD_CODE)
    public String getCode() {
        return (String) getValue(FIELD_CODE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param code the code to set
     */
    @JsonProperty(FIELD_CODE)
    protected void setCode(String code) {
        put(FIELD_CODE, code);
    }

    /**
     * @return the help_url
     */
    @JsonProperty(FIELD_HELP_URL)
    public String getHelpUrl() {
        return (String) getValue(FIELD_HELP_URL);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param helpUrl the help_url to set
     */
    @JsonProperty(FIELD_HELP_URL)
    protected void setHelpUrl(String helpUrl) {
        put(FIELD_HELP_URL, helpUrl);
    }

    /**
     * @return the message
     */
    @JsonProperty(FIELD_MESSAGE)
    public String getMessage() {
        return (String) getValue(FIELD_MESSAGE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param message the message to set
     */
    @JsonProperty(FIELD_MESSAGE)
    protected void setMessage(String message) {
        put(FIELD_MESSAGE, message);
    }

    /**
     * @return the request_id
     */
    @JsonProperty(FIELD_REQUEST_ID)
    public String getRequestId() {
        return (String) getValue(FIELD_REQUEST_ID);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param requestId the request_id to set
     */
    @JsonProperty(FIELD_REQUEST_ID)
    protected void setRequestId(String requestId) {
        put(FIELD_REQUEST_ID, requestId);
    }


    /**
     * Deprecated, use getStatus() instead
     *
     * @return status code
     */
    @JsonIgnore
    @Deprecated
    public Integer getHttpStatusCode() {
        return getStatus();
    }
}
