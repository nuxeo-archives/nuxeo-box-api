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
import com.nuxeo.box.api.BoxAdapter;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;

import java.util.Collections;

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
        CoreSession session = doc.getCoreSession();
        //boxProperties.put(BoxFile.FIELD_SHA1, doc.getBlo
        boxFile = new BoxFile(Collections.unmodifiableMap(boxProperties));

    }

    public BoxFile getBoxFile() {
        return boxFile;
    }

    public void setBoxFile(BoxFile boxFile) {
        this.boxFile = boxFile;
    }

}