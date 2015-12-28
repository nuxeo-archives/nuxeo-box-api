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
package org.nuxeo.box.api.marshalling.jsonparsing;

import org.nuxeo.box.api.marshalling.dao.BoxObject;
import org.nuxeo.box.api.marshalling.interfaces.IBoxResourceHub;
import org.nuxeo.box.api.marshalling.interfaces.IBoxType;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseBoxResourceHub implements IBoxResourceHub {

    // As a performance optimization, set up string values for all types.
    private static final Map<String, IBoxType> lowercaseStringToType = new HashMap<String, IBoxType>();

    public BaseBoxResourceHub() {
        initializeTypes();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class getClass(IBoxType type) {
        return BoxObject.class;
    }

    protected Map<String, IBoxType> getLowerCaseStringToTypeMap() {
        return lowercaseStringToType;
    }

    /**
     * Get the concrete class for IBoxType
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected abstract Class getConcreteClassForIBoxType();

    /**
     * Get class for a certain type, assuming the input type is an object of the concrete class of IBoxType defined in
     * this resource hub.
     *
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected abstract Class getObjectClassGivenConcreteIBoxType(IBoxType type);

    /**
     * Do call super.initializeTypes() when overriding this.
     */
    protected void initializeTypes() {
        // Make it non-abstract so children can call super. This way makes it
        // more explicit that super should be called.
    }

    protected void initializeEnumTypes(Class<? extends Enum> cls) {
        Map<String, IBoxType> map = getLowerCaseStringToTypeMap();
        Enum[] types = cls.getEnumConstants();
        for (Enum type : types) {
            String str = type.name().toLowerCase(Locale.ENGLISH);
            map.put(str, (IBoxType) type);
        }
    }
}
