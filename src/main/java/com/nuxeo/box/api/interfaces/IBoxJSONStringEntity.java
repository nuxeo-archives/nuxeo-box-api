package com.nuxeo.box.api.interfaces;


import com.nuxeo.box.api.exceptions.BoxJSONException;

/**
 * Interface for classes that can be converted to JSON Strings.
 */
public interface IBoxJSONStringEntity {

    /**
     * Convert to JSON String.
     *
     * @param parser json parser
     * @return JSON String
     * @throws BoxJSONException
     */
    String toJSONString(IBoxJSONParser parser) throws BoxJSONException;
}
