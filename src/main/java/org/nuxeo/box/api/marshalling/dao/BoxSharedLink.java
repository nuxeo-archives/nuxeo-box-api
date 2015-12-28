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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

// CHECKSTYLE:OFF

/**
 * Shared link.
 */
public class BoxSharedLink extends BoxObject {

    public static final String FIELD_URL = "url";

    public static final String FIELD_DOWNLOAD_URL = "download_url";

    public static final String FIELD_PASSWORD_ENABLED = "password_enabled";

    public static final String FIELD_UNSHARED_AT = "unshared_at";

    public static final String FIELD_DOWNLOAD_COUNT = "download_count";

    public static final String FIELD_PREVIEW_COUNT = "preview_count";

    public static final String FIELD_ACCESS = "access";

    public static final String FIELD_PERMISSIONS = "permissions";

    public BoxSharedLink() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxSharedLink(BoxSharedLink obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxSharedLink(Map<String, Object> map) {
        super(map);
    }

    /**
     * Get the url of the shared link.
     *
     * @return the url
     */
    @JsonProperty(FIELD_URL)
    public String getUrl() {
        return (String) getValue(FIELD_URL);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param theUrl the url to set
     */
    @JsonProperty(FIELD_URL)
    private void setUrl(final String theUrl) {
        put(FIELD_URL, theUrl);
    }

    /**
     * Get the url to download the shared item.
     *
     * @return the download_url
     */
    @JsonProperty(FIELD_DOWNLOAD_URL)
    public String getDownloadUrl() {
        return (String) getValue(FIELD_DOWNLOAD_URL);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param downloadUrl the download_url to set
     */
    @JsonProperty(FIELD_DOWNLOAD_URL)
    private void setDownloadUrl(final String downloadUrl) {
        put(FIELD_DOWNLOAD_URL, downloadUrl);
    }

    /**
     * Whether this shared link is password enabled.
     *
     * @return the password_enabled
     */
    @JsonProperty(FIELD_PASSWORD_ENABLED)
    public Boolean isPasswordEnabled() {
        return (Boolean) getValue(FIELD_PASSWORD_ENABLED);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param passwordEnabled the password_enabled to set
     */
    @JsonProperty(FIELD_PASSWORD_ENABLED)
    private void setPasswordEnabled(final Boolean passwordEnabled) {
        put(FIELD_PASSWORD_ENABLED, passwordEnabled);
    }

    /**
     * Get download count.
     *
     * @return the download_count
     */
    @JsonProperty(FIELD_DOWNLOAD_COUNT)
    public Integer getDownloadCount() {
        return (Integer) getValue(FIELD_DOWNLOAD_COUNT);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param downloadCount the download_count to set
     */
    @JsonProperty(FIELD_DOWNLOAD_COUNT)
    private void setDownloadCount(final Integer downloadCount) {
        put(FIELD_DOWNLOAD_COUNT, downloadCount);
    }

    /**
     * Get the time to unshare this link. This returns a String and can be parsed into {@link java.util.Date} by
     *
     * @return the unshared_at
     */
    @JsonProperty(FIELD_UNSHARED_AT)
    public String getUnsharedAt() {
        return (String) getValue(FIELD_UNSHARED_AT);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param unsharedAt the unshared_at to set
     */
    @JsonProperty(FIELD_UNSHARED_AT)
    private void setUnsharedAt(final String unsharedAt) {
        put(FIELD_UNSHARED_AT, unsharedAt);
    }

    /**
     * Get the preview count.
     *
     * @return the preview_count
     */
    @JsonProperty(FIELD_PREVIEW_COUNT)
    public Integer getPreviewCount() {
        return (Integer) getValue(FIELD_PREVIEW_COUNT);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param previewCount the preview_count to set
     */
    @JsonProperty(FIELD_PREVIEW_COUNT)
    private void setPreviewCount(final Integer previewCount) {
        put(FIELD_PREVIEW_COUNT, previewCount);
    }

    /**
     * Get access. This can only be the strings defined in {@link com.box .boxjavalibv2.dao.BoxSharedLinkAccess}
     *
     * @return the access
     */
    @JsonProperty(FIELD_ACCESS)
    public String getAccess() {
        return (String) getValue(FIELD_ACCESS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param accessLevel the access to set
     */
    @JsonProperty(FIELD_ACCESS)
    private void setAccess(final String accessLevel) {
        put(FIELD_ACCESS, accessLevel);
    }

    /**
     * Get permissions.
     *
     * @return the permissions
     */
    @JsonProperty(FIELD_PERMISSIONS)
    public BoxSharedLinkPermissions getPermissions() {
        return (BoxSharedLinkPermissions) getValue(FIELD_PERMISSIONS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param permissionsEntity the permissions to set
     */
    @JsonProperty(FIELD_PERMISSIONS)
    private void setPermissions(final BoxSharedLinkPermissions permissionsEntity) {
        put(FIELD_PERMISSIONS, permissionsEntity);
    }
}
