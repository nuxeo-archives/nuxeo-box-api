package org.nuxeo.box.api.marshalling.exceptions;

import org.nuxeo.ecm.webengine.WebException;

/**
 * BoxException, this is an exception thrown from executing the api requests.
 */
public class BoxRestException extends WebException {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    private String mMessage;

    private int errorCode;

    /**
     * BoxRestException.
     *
     * @param message Message of the exception.
     */
    public BoxRestException(String message) {
        this.mMessage = message;
    }

    /**
     * BoxRestException.
     *
     * @param exception raw exception.
     * @param message   customized exception message.
     */
    public BoxRestException(Exception exception, String message) {
        super(exception);
        this.mMessage = message;
    }

    /**
     * BoxRestException.
     *
     * @param message   customized exception message.
     * @param errorCode customized exception error code.
     */
    public BoxRestException(String message, int errorCode) {
        this(message);
        this.errorCode = errorCode;
    }

    /**
     * BoxRestException.
     *
     * @param e raw exception.
     */
    public BoxRestException(Exception e) {
        super(e);
    }

    public BoxRestException(String message, Exception e, int errorCode) {
        super(e);
        this.mMessage = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

    /**
     * Get the customized exception error code.
     *
     * @return error code.
     */
    public int getErrorCode() {
        return errorCode;
    }
}
