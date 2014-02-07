package com.nuxeo.box.api.exceptions;

/**
 * Exception for unexpected API response body.
 */
public class BoxMalformedResponseException extends BoxServerException {

    /**
     * default serial version UID
     */
    private static final long serialVersionUID = 1L;

    public static final String MALFORM = "malformed response";

    public BoxMalformedResponseException(int statusCode) {
        super(MALFORM, statusCode);
    }

}
