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
     * Status code of this exception. This could be helpful for exception wrapping http responses. By default, it is -1.
     */
    public int getStatusCode() {
        return -1;
    }

}
