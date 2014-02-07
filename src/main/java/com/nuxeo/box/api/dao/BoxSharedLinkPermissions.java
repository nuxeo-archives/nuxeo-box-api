package com.nuxeo.box.api.dao;


import java.util.Map;

// CHECKSTYLE:OFF

/**
 * Permission for shared links.
 */
public class BoxSharedLinkPermissions extends BoxObject {

    private boolean can_download;

    /**
     * Default constructor.
     */
    public BoxSharedLinkPermissions() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxSharedLinkPermissions(BoxSharedLinkPermissions obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxSharedLinkPermissions(Map<String, Object> map) {
        super(map);
    }

    /**
     * Constructor.
     *
     * @param canDownload can be downloaded
     */
    public BoxSharedLinkPermissions(final boolean canDownload) {
        this.setCan_download(canDownload);
    }

    /**
     * whether can_download is true.
     *
     * @return can_download
     */
    public boolean isCan_download() {
        return can_download;
    }

    /**
     * Setter.
     *
     * @param canDownload
     */
    private void setCan_download(final boolean canDownload) {
        this.can_download = canDownload;
    }
}
