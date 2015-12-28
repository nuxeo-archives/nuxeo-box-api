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

import org.nuxeo.box.api.marshalling.dao.BoxCollaboration;
import org.nuxeo.box.api.marshalling.dao.BoxCollection;
import org.nuxeo.box.api.marshalling.dao.BoxComment;
import org.nuxeo.box.api.marshalling.dao.BoxEmailAlias;
import org.nuxeo.box.api.marshalling.dao.BoxEvent;
import org.nuxeo.box.api.marshalling.dao.BoxEventCollection;
import org.nuxeo.box.api.marshalling.dao.BoxFile;
import org.nuxeo.box.api.marshalling.dao.BoxFileVersion;
import org.nuxeo.box.api.marshalling.dao.BoxFolder;
import org.nuxeo.box.api.marshalling.dao.BoxGroup;
import org.nuxeo.box.api.marshalling.dao.BoxItem;
import org.nuxeo.box.api.marshalling.dao.BoxLock;
import org.nuxeo.box.api.marshalling.dao.BoxPreview;
import org.nuxeo.box.api.marshalling.dao.BoxRealTimeServer;
import org.nuxeo.box.api.marshalling.dao.BoxResourceType;
import org.nuxeo.box.api.marshalling.dao.BoxServerError;
import org.nuxeo.box.api.marshalling.dao.BoxTypedObject;
import org.nuxeo.box.api.marshalling.dao.BoxUser;
import org.nuxeo.box.api.marshalling.dao.BoxWebLink;
import org.nuxeo.box.api.marshalling.interfaces.IBoxType;

import java.util.Collection;

public class BoxResourceHub extends BaseBoxResourceHub {

    public BoxResourceHub() {
        super();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class getClass(IBoxType type) {
        if (getConcreteClassForIBoxType().equals(type.getClass())) {
            return getObjectClassGivenConcreteIBoxType(type);
        } else {
            return super.getClass(type);
        }
    }

    @Override
    public Collection<IBoxType> getAllTypes() {
        return getLowerCaseStringToTypeMap().values();
    }

    @Override
    protected Class getObjectClassGivenConcreteIBoxType(IBoxType type) {
        switch ((BoxResourceType) type) {
        case FILE:
            return BoxFile.class;
        case PREVIEW:
            return BoxPreview.class;
        case FOLDER:
            return BoxFolder.class;
        case WEB_LINK:
            return BoxWebLink.class;
        case USER:
            return BoxUser.class;
        case GROUP:
            return BoxGroup.class;
        case FILE_VERSION:
            return BoxFileVersion.class;
        case ITEM:
            return BoxItem.class;
        case COMMENT:
            return BoxComment.class;
        case COLLABORATION:
            return BoxCollaboration.class;
        case EMAIL_ALIAS:
            return BoxEmailAlias.class;
        case EVENT:
            return BoxEvent.class;
        case EVENTS:
            return BoxEventCollection.class;
        case REALTIME_SERVER:
            return BoxRealTimeServer.class;
        case LOCK:
            return BoxLock.class;
        case ERROR:
            return BoxServerError.class;
        case ITEMS:
        case FILES:
        case USERS:
        case COMMENTS:
        case FILE_VERSIONS:
        case COLLABORATIONS:
        case EMAIL_ALIASES:
            return BoxCollection.class;
        default:
            return BoxTypedObject.class;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getConcreteClassForIBoxType() {
        return BoxResourceType.class;
    }

    @Override
    public IBoxType getTypeFromLowercaseString(String type) {
        return getLowerCaseStringToTypeMap().get(type);
    }

    @Override
    protected void initializeTypes() {
        super.initializeTypes();
        initializeEnumTypes(BoxResourceType.class);
    }
}
