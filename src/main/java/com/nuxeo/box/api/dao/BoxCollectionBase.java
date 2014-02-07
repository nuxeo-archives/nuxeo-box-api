package com.nuxeo.box.api.dao;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

public class BoxCollectionBase extends BoxObject {

    public static final String FIELD_ENTRIES = "entries";

    public BoxCollectionBase() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxCollectionBase(BoxCollectionBase obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxCollectionBase(Map<String, Object> map) {
        super(map);
    }

    /**
     * @return the entries
     */
    @SuppressWarnings("unchecked")
    @JsonProperty("entries")
    public ArrayList<BoxTypedObject> getEntries() {
        return (ArrayList<BoxTypedObject>) super.getValue(FIELD_ENTRIES);
    }

    /**
     * @param entries the entries to set
     */
    @JsonProperty("entries")
    private void setEntries(ArrayList<BoxTypedObject> entries) {
        put(FIELD_ENTRIES, entries);
    }

}
