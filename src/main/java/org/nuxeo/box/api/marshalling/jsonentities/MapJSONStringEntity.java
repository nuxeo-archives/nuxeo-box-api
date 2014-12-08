package org.nuxeo.box.api.marshalling.jsonentities;

import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.marshalling.interfaces.IBoxJSONParser;
import org.nuxeo.box.api.marshalling.interfaces.IBoxJSONStringEntity;

import java.util.LinkedHashMap;

/**
 * Implemenation of {@link org.nuxeo.box.api.marshalling.interfaces.IBoxJSONStringEntity} based on LinkedHashMap.
 */
public class MapJSONStringEntity extends LinkedHashMap<String, Object> implements IBoxJSONStringEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public String toJSONString(IBoxJSONParser parser) throws BoxJSONException {
        return parser.convertBoxObjectToJSONStringQuietly(this);
    }

}
