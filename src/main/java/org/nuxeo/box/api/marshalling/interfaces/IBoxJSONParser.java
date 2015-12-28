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
package org.nuxeo.box.api.marshalling.interfaces;

import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;

import java.io.InputStream;

public interface IBoxJSONParser {

    /**
     * Convert the object into String. No exception will be thrown, in case of failure, null is returned.
     *
     * @param object
     * @return
     */
    String convertBoxObjectToJSONStringQuietly(final Object object);

    /**
     * Convert InputStream to object.No exception will be thrown, in case of failure, null is returned.
     *
     * @param inputStream
     * @param theClass
     * @return
     */
    <T> T parseIntoBoxObjectQuietly(final InputStream inputStream, final Class<T> theClass);

    /**
     * Convert the json string into object.No exception will be thrown, in case of failure, null is returned.
     *
     * @param jsonString
     * @param theClass
     * @return
     */
    <T> T parseIntoBoxObjectQuietly(final String jsonString, final Class<T> theClass);

    /**
     * Convert the object into String.
     *
     * @param object
     * @return
     * @throws BoxJSONException
     */
    String convertBoxObjectToJSONString(final Object object) throws BoxJSONException;

    /**
     * Convert InputStream to object.
     *
     * @param inputStream
     * @param theClass
     * @return
     * @throws BoxJSONException
     */
    <T> T parseIntoBoxObject(final InputStream inputStream, final Class<T> theClass) throws BoxJSONException;

    /**
     * Convert the json string into object.
     *
     * @param jsonString
     * @param theClass
     * @return
     * @throws BoxJSONException
     */
    <T> T parseIntoBoxObject(final String jsonString, final Class<T> theClass) throws BoxJSONException;
}
