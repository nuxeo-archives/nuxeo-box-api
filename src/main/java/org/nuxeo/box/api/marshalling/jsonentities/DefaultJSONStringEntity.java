package org.nuxeo.box.api.marshalling.jsonentities;


import org.nuxeo.box.api.marshalling.dao.BoxBase;
import org.nuxeo.box.api.marshalling.exceptions.BoxJSONException;
import org.nuxeo.box.api.marshalling.interfaces.IBoxJSONParser;
import org.nuxeo.box.api.marshalling.interfaces.IBoxJSONStringEntity;

/**
 * Default implementation of the {@link IBoxJSONStringEntity},
 * this class utilizes {@see <a href="http://jackson.codehaus.org">Jackson
 * JSON processer</a>} to
 * convert object into JSON String.
 */
public class DefaultJSONStringEntity extends BoxBase implements
        IBoxJSONStringEntity {

    @Override
    public String toJSONString(IBoxJSONParser parser) throws BoxJSONException {
        return parser.convertBoxObjectToJSONStringQuietly(this);
    }

}
