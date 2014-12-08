package org.nuxeo.box.api.marshalling.dao;

/**
 * Access level for shared links.
 */
public class BoxSharedLinkAccess {

    /**
     * Open access. Indicates the link can be accessed by anybody.
     */
    public final static String OPEN = "open";

    /**
     * Company access. Indicates the link can only be accessed by accounts within a same company.
     */
    public final static String COMPANY = "company";

    /**
     * Collaborators access.Indicates the link can only be accessed by collaborators.
     */
    public final static String COLLABORATORS = "collaborators";
}
