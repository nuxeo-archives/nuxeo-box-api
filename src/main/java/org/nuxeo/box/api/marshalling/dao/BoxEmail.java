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
 * Email.
 */
public class BoxEmail extends BoxObject {

    public static final String FIELD_ACCESS = "access";

    public static final String FIELD_EMAIL = "email";

    public BoxEmail() {
    }

    public BoxEmail(BoxEmail obj) {
        super(obj);
    }

    public BoxEmail(Map<String, Object> map) {
        super(map);
    }

    /**
     * @return the access
     */
    @JsonProperty(FIELD_ACCESS)
    public String getAccess() {
        return (String) getValue(FIELD_ACCESS);
    }

    /**
     * @param access the access to set
     */
    @JsonProperty(FIELD_ACCESS)
    private void setAccess(String access) {
        put(FIELD_ACCESS, access);
    }

    /**
     * @return the email
     */
    @JsonProperty(FIELD_EMAIL)
    public String getEmail() {
        return (String) getValue(FIELD_EMAIL);
    }

    /**
     * @param email the email to set
     */
    @JsonProperty(FIELD_EMAIL)
    private void setEmail(String email) {
        put(FIELD_EMAIL, email);
    }
}
