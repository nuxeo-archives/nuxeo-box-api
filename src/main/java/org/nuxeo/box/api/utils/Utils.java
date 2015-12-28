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
