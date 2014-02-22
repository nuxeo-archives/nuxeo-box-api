package com.nuxeo.box.api.marshalling.exceptions;

import com.nuxeo.box.api.marshalling.dao.BoxServerError;

/**
 * Exception for unexpected http status code(not error status code).
 */
public class BoxUnexpectedHttpStatusException extends BoxServerException {

    private static final long serialVersionUID = 1L;

    private final BoxUnexpectedStatus unexpectedStatus;

    private Object context;

    public BoxUnexpectedHttpStatusException(BoxUnexpectedStatus
            unexpectedStatus) {
        super();
        this.unexpectedStatus = unexpectedStatus;
    }

    public BoxUnexpectedStatus getUnexpectedStatus() {
        return unexpectedStatus;
    }

    /**
     * @return the context
     */
    public Object getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(Object context) {
        this.context = context;
    }

    @Override
    public BoxServerError getError() {
        return unexpectedStatus;
    }
}
