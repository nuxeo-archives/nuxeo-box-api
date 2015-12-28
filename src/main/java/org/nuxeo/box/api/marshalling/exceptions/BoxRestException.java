/*
 * Copyright 2013 Box, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     * @param message customized exception message.
     */
    public BoxRestException(Exception exception, String message) {
        super(exception);
        this.mMessage = message;
    }

    /**
     * BoxRestException.
     *
     * @param message customized exception message.
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
