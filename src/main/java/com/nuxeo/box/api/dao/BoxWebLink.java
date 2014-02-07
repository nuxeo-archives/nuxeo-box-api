package com.nuxeo.box.api.dao;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BoxWebLink extends BoxItem {

    public static final String FIELD_URL = "url";

    /**
     * Constructor.
     */
    public BoxWebLink() {
        setType(BoxResourceType.WEB_LINK.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxWebLink(BoxWebLink obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxWebLink(Map<String, Object> map) {
        super(map);
    }

    /**
     * Get url of the weblink.
     *
     * @return url
     */
    @JsonProperty(FIELD_URL)
    public String getUrl() {
        return (String) getValue(FIELD_URL);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param url url
     */
    @JsonProperty(FIELD_URL)
    private void setUrl(String url) {
        put(FIELD_URL, url);
    }
}
