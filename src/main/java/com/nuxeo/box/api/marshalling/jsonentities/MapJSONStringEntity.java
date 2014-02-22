package com.nuxeo.box.api.marshalling.jsonentities;

import com.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import com.nuxeo.box.api.marshalling.interfaces.IBoxJSONParser;
import com.nuxeo.box.api.marshalling.interfaces.IBoxJSONStringEntity;

import java.util.LinkedHashMap;

/**
 * Implemenation of {@link IBoxJSONStringEntity} based on LinkedHashMap.
 */
public class MapJSONStringEntity extends LinkedHashMap<String,
        Object> implements IBoxJSONStringEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public String toJSONString(IBoxJSONParser parser) throws BoxJSONException {
        return parser.convertBoxObjectToJSONStringQuietly(this);
    }

}
