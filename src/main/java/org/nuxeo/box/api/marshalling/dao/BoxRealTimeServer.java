package org.nuxeo.box.api.marshalling.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BoxRealTimeServer extends BoxTypedObject {

    private static final String FIELD_URL = "url";

    /**
     * Constructor.
     */
    public BoxRealTimeServer() {
        setType(BoxResourceType.REALTIME_SERVER.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxRealTimeServer(BoxRealTimeServer obj) {
        super(obj);
    }

    /**
     * Get url.
     *
     * @return url.
     */
    @JsonProperty(FIELD_URL)
    public String getUrl() {
        return (String) getValue(FIELD_URL);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param url Url.
     */
    @JsonProperty(FIELD_URL)
    private void setUrl(String url) {
        put(FIELD_URL, url);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxRealTimeServer(Map<String, Object> map) {
        super(map);
    }

}
