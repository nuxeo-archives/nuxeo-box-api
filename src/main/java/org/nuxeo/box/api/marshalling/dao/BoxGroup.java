package org.nuxeo.box.api.marshalling.dao;

import java.util.Map;

/**
 * Box Group.
 */
public class BoxGroup extends BoxUser {

    /**
     * Constructor.
     */
    public BoxGroup() {
        setType(BoxResourceType.GROUP.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxGroup(BoxGroup obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxGroup(Map<String, Object> map) {
        super(map);
    }

}
