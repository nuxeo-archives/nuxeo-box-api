package org.nuxeo.box.api.marshalling.interfaces;


import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;

/**
 * Interface for classes that can be converted to JSON Strings.
 */
public interface IBoxJSONStringEntity {

    /**
     * Convert to JSON String.
     *
     * @param parser json parser
     * @return JSON String
     * @throws org.nuxeo.box.api.marshalling.exceptions.BoxJSONException
     */
    String toJSONString(IBoxJSONParser parser) throws BoxJSONException;
}
