package com.nuxeo.box.api.exceptions;

import com.nuxeo.box.api.dao.BoxServerError;

/**
 * Unexpected http status code(not error status code).
 */
public class BoxUnexpectedStatus extends BoxServerError {

    public static final String FIELD_RETRY_AFTER = "retry_after";

    /**
     * @return the time in seconds to wait before retrying this api call.
     */
    public Integer getRetryAfter() {
        return (Integer) getValue(FIELD_RETRY_AFTER);
    }

    /**
     * Setter.
     *
     * @param retryAfter
     */
    public void setRetryAfter(Integer retryAfter) {
        put(FIELD_RETRY_AFTER, retryAfter);
    }

    public BoxUnexpectedStatus(int status) {
        setStatus(status);
    }
}
