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
