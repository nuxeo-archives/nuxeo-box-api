package com.nuxeo.box.api.jsonparsing;


import com.nuxeo.box.api.dao.BoxCollaboration;
import com.nuxeo.box.api.dao.BoxCollection;
import com.nuxeo.box.api.dao.BoxComment;
import com.nuxeo.box.api.dao.BoxEmailAlias;
import com.nuxeo.box.api.dao.BoxEvent;
import com.nuxeo.box.api.dao.BoxEventCollection;
import com.nuxeo.box.api.dao.BoxFile;
import com.nuxeo.box.api.dao.BoxFileVersion;
import com.nuxeo.box.api.dao.BoxFolder;
import com.nuxeo.box.api.dao.BoxGroup;
import com.nuxeo.box.api.dao.BoxItem;
import com.nuxeo.box.api.dao.BoxLock;
import com.nuxeo.box.api.dao.BoxPreview;
import com.nuxeo.box.api.dao.BoxRealTimeServer;
import com.nuxeo.box.api.dao.BoxResourceType;
import com.nuxeo.box.api.dao.BoxServerError;
import com.nuxeo.box.api.dao.BoxTypedObject;
import com.nuxeo.box.api.dao.BoxUser;
import com.nuxeo.box.api.dao.BoxWebLink;
import com.nuxeo.box.api.interfaces.IBoxType;

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
