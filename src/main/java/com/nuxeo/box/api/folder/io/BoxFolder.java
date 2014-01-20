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
package com.nuxeo.box.api.folder.io;

import org.nuxeo.ecm.core.api.DocumentModel;

/**
 * A representation of a box folder for writer/reader
 *
 * @since 5.9.2
 */
public class BoxFolder {

    private DocumentModel doc;

    private String folderName;

    public BoxFolder(String folderName, DocumentModel doc) {
        this.folderName = folderName;
        this.doc = doc;
    }

    /**
     * @return
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * @return
     */
    public DocumentModel getDocumentModel() {
        return doc;
    }


}
