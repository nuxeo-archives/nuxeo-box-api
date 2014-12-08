package org.nuxeo.box.api.utils;

import org.nuxeo.box.api.marshalling.dao.BoxResourceType;

/**
 * Utils class.
 */
public final class Utils {

    /**
     * Private constructor so the class cannot be instantiated.
     */
    private Utils() {
    }

    /**
     * Given a resource type, get the string for it's REST API container. For example, given a
     * {@link org.nuxeo.box.api.marshalling.dao.BoxResourceType#FILE}, it it's container would be "files".
     *
     * @param type type
     * @return container string
     */
    public static String getContainerString(final BoxResourceType type) {
        switch (type) {
        case FILE_VERSION:
            return "versions";
        default:
            return type.toPluralString();
        }
    }

}
