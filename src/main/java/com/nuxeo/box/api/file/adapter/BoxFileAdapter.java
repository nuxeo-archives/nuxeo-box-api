/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package com.nuxeo.box.api.file.adapter;

import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.dao.BoxLock;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.nuxeo.box.api.BoxAdapter;
import com.nuxeo.box.api.BoxConstants;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.Lock;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.api.blobholder.SimpleBlobHolder;
import org.nuxeo.ecm.platform.tag.TagService;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Box File Adapter
 *
 * @since 5.9.2
 */
public class BoxFileAdapter extends BoxAdapter {

    protected BoxFile boxFile;

    /**
     * Instantiate the adapter and the Box File from Nuxeo Document and
     * load its properties into json format
     */
    public BoxFileAdapter(DocumentModel doc) throws ClientException {
        super(doc);

        //MD5
        Blob blob = (Blob) doc.getPropertyValue("file:content");
        if (blob != null) {
            SimpleBlobHolder blobHolder = new SimpleBlobHolder(blob);
            boxProperties.put(BoxFile.FIELD_SHA1, blobHolder.getHash());
        }

        // Lock
        Map<String, Object> boxLockProperties = new HashMap<>();
        Lock lockInfo = doc.getLockInfo();
        if (lockInfo != null) {
            boxLockProperties.put(BoxItem.FIELD_TYPE, BoxLock.FIELD_LOCK_TYPE);
            boxLockProperties.put(BoxItem.FIELD_ID, "-1");
            final UserManager userManager = Framework.getLocalService
                    (UserManager.class);
            final NuxeoPrincipal lockCreator = userManager.getPrincipal(lockInfo
                    .getOwner());
            final BoxUser boxLockCreator = fillUser(lockCreator);
            boxLockProperties.put(BoxItem.FIELD_CREATED_BY, boxLockCreator);
            boxLockProperties.put(BoxItem.FIELD_CREATED_AT,
                    ISODateTimeFormat.dateTime().print(
                            new DateTime(lockInfo.getCreated())));
            boxLockProperties.put(BoxLock.FIELD_EXPIRES_AT, "-1");
            boxLockProperties.put(BoxLock.FIELD_IS_DOWNLOAD_PREVENTED, false);
            BoxLock boxLock = new BoxLock(boxLockProperties);
            boxProperties.put(BoxConstants.BOX_LOCK, boxLock);
        }

        boxFile = new BoxFile(Collections.unmodifiableMap(boxProperties));

    }

    /**
     * Update the document (nx/box sides) thanks to a box folder
     */
    public void save(CoreSession session) throws ClientException,
            ParseException, InvocationTargetException,
            IllegalAccessException, BoxJSONException {

        // Update nx document with new box folder properties
        setTitle(boxFile.getName());
        setDescription(boxFile.getDescription());

        // If parent id has been updated -> move the document
        String newParentId = boxFile.getParent().getId();
        IdRef documentIdRef = new IdRef(doc.getId());
        String oldParentId = session.getParentDocument(documentIdRef).getId();
        if (!oldParentId.equals(newParentId)) {
            session.move(documentIdRef, new IdRef(newParentId),
                    boxFile.getName());
        }
        setCreator(boxFile.getOwnedBy().getId());

        TagService tagService = Framework.getLocalService(TagService.class);
        if (tagService != null) {
            tagService.removeTags(session, doc.getId());
            for (String tag : boxFile.getTags()) {
                tagService.tag(session, doc.getId(), tag,
                        session.getPrincipal().getName());
            }
        }
        session.saveDocument(doc);
        session.save();
    }

    public BoxFile getBoxFile() {
        return boxFile;
    }

    public void setBoxFile(BoxFile boxFile) {
        this.boxFile = boxFile;
    }

}