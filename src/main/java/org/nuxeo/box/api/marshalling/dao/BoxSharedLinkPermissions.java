/*
 * Copyright 2013 Box, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nuxeo.box.api.marshalling.dao;

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
     * Instantiate the object from a map. Each entry in the map reflects to a field.
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
