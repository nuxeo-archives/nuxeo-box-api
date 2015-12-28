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
package org.nuxeo.box.api.marshalling.interfaces;

import java.util.Collection;

public interface IBoxResourceHub {

    /**
     * Given a {@link IBoxType}, get the corrosponding DAO class.
     *
     * @param type resource type
     * @return corresponding resource DAO class
     */
    @SuppressWarnings("rawtypes")
    Class getClass(IBoxType type);

    /**
     * Get the IBoxType from a lower case string value. For example "file" would return BoxResourceType.FILE
     *
     * @param
     * @return
     */
    IBoxType getTypeFromLowercaseString(String type);

    Collection<IBoxType> getAllTypes();
}
