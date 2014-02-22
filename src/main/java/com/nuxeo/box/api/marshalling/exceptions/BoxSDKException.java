package com.nuxeo.box.api.marshalling.exceptions;

public abstract class BoxSDKException extends Exception {

    private static final long serialVersionUID = 1L;

    public BoxSDKException(String message) {
        super(message);
    }

    public BoxSDKException(Exception exception) {
        super(exception);
    }

    public BoxSDKException() {
        super();
    }

    /**
     * Status code of this exception. This could be helpful for exception
     * wrapping http responses. By default, it is -1.
     */
    public int getStatusCode() {
        return -1;
    }

}
