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

/**
 * Version of a file.
 */
public class BoxLock extends BoxTypedObject {

    public static final String FIELD_CREATED_BY = "created_by";

    public static final String FIELD_FILE = "file";

    public static final String FIELD_EXPIRES_AT = "expires_at";

    public static final String FIELD_LOCK_TYPE = "lock_type";

    public static final String FIELD_IS_DOWNLOAD_PREVENTED = "is_download_prevented";

    public static final String FIELD_SERVICE_ACTION = "service_action";

    /**
     * Constructor.
     */
    public BoxLock() {
        setType(BoxResourceType.LOCK.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxLock(BoxLock obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxLock(Map<String, Object> map) {
        super(map);
    }

    @JsonProperty(FIELD_CREATED_BY)
    public BoxUser getCreatedBy() {
        return (BoxUser) getValue(FIELD_CREATED_BY);
    }

    @JsonProperty(FIELD_CREATED_BY)
    public void setCreatedBy(BoxUser createdBy) {
        put(FIELD_CREATED_BY, createdBy);
    }

    @JsonProperty(FIELD_FILE)
    public BoxItem getFile() {
        return (BoxItem) getValue(FIELD_FILE);
    }

    @JsonProperty(FIELD_FILE)
    public void setFile(BoxItem file) {
        put(FIELD_FILE, file);
    }

    @JsonProperty(FIELD_EXPIRES_AT)
    public String getExpiresAt() {
        return (String) getValue(FIELD_EXPIRES_AT);
    }

    @JsonProperty(FIELD_EXPIRES_AT)
    public void setExpiresAt(String expiresAt) {
        put(FIELD_EXPIRES_AT, expiresAt);
    }

    @JsonProperty(FIELD_LOCK_TYPE)
    public String getLockType() {
        return (String) getValue(FIELD_LOCK_TYPE);
    }

    @JsonProperty(FIELD_LOCK_TYPE)
    public void setLockType(String lockType) {
        put(FIELD_LOCK_TYPE, lockType);
    }

    @JsonProperty(FIELD_IS_DOWNLOAD_PREVENTED)
    public Boolean isDownloadPrevented() {
        return (Boolean) getValue(FIELD_IS_DOWNLOAD_PREVENTED);
    }

    @JsonProperty(FIELD_IS_DOWNLOAD_PREVENTED)
    public void setDownloadPrevented(Boolean lockType) {
        put(FIELD_IS_DOWNLOAD_PREVENTED, lockType);
    }

    @JsonProperty(FIELD_SERVICE_ACTION)
    public BoxServiceAction getServiceAction() {
        return (BoxServiceAction) getValue(FIELD_SERVICE_ACTION);
    }

    @JsonProperty(FIELD_SERVICE_ACTION)
    public void setServiceAction(BoxServiceAction serviceAction) {
        put(FIELD_SERVICE_ACTION, serviceAction);
    }

}
