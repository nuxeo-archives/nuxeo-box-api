package com.nuxeo.box.api.interfaces;


import com.nuxeo.box.api.exceptions.BoxJSONException;

import java.io.InputStream;

public interface IBoxJSONParser {

    /**
     * Convert the object into String. No exception will be thrown,
     * in case of failure, null is returned.
     *
     * @param object
     * @return
     */
    String convertBoxObjectToJSONStringQuietly(final Object object);

    /**
     * Convert InputStream to object.No exception will be thrown,
     * in case of failure, null is returned.
     *
     * @param inputStream
     * @param theClass
     * @return
     */
    <T> T parseIntoBoxObjectQuietly(final InputStream inputStream,
            final Class<T> theClass);

    /**
     * Convert the json string into object.No exception will be thrown,
     * in case of failure, null is returned.
     *
     * @param jsonString
     * @param theClass
     * @return
     */
    <T> T parseIntoBoxObjectQuietly(final String jsonString, final Class<T>
            theClass);

    /**
     * Convert the object into String.
     *
     * @param object
     * @return
     * @throws BoxJSONException
     */
    String convertBoxObjectToJSONString(final Object object) throws
            BoxJSONException;

    /**
     * Convert InputStream to object.
     *
     * @param inputStream
     * @param theClass
     * @return
     * @throws BoxJSONException
     */
    <T> T parseIntoBoxObject(final InputStream inputStream, final Class<T>
            theClass) throws BoxJSONException;

    /**
     * Convert the json string into object.
     *
     * @param jsonString
     * @param theClass
     * @return
     * @throws BoxJSONException
     */
    <T> T parseIntoBoxObject(final String jsonString,
            final Class<T> theClass) throws BoxJSONException;
}
