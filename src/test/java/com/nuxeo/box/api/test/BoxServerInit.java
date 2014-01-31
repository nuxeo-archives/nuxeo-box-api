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
package com.nuxeo.box.api.test;

import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.automation.core.util.DocumentHelper;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.impl.blob.FileBlob;
import org.nuxeo.ecm.core.test.annotations.RepositoryInit;

import java.io.File;

/**
 * Initialization of box testing repository
 *
 * @since 5.9.2
 */
public class BoxServerInit implements RepositoryInit {

    public static DocumentModel getFolder(int index, CoreSession session)
            throws ClientException {
        return session.getDocument(new PathRef("/folder_" + index));
    }

    public static DocumentModel getNote(int index, CoreSession session)
            throws ClientException {
        return session.getDocument(new PathRef("/folder_1/note_" + index));
    }

    public static DocumentModel getFile(int index, CoreSession session)
            throws ClientException {
        return session.getDocument(new PathRef("/folder_2/file"));
    }

    @Override
    public void populate(CoreSession session) throws ClientException {
        // Create some docs
        for (int i = 0; i < 5; i++) {
            DocumentModel doc = session.createDocumentModel("/", "folder_" + i,
                    "Folder");
            doc.setPropertyValue("dc:title", "Folder " + i);
            doc = session.createDocument(doc);
        }

        for (int i = 0; i < 5; i++) {
            DocumentModel doc = session.createDocumentModel("/folder_1",
                    "note_" + i, "Note");
            doc.setPropertyValue("dc:title", "Note " + i);

            doc.setPropertyValue("note:note", "Note " + i);
            doc = session.createDocument(doc);
        }

        // Create a file
        DocumentModel doc = session.createDocumentModel("/folder_2",
                "file", "File");
        doc.setPropertyValue("dc:title", "File");
        doc = session.createDocument(doc);
        // upload file blob
        File fieldAsJsonFile = FileUtils.getResourceFileFromContext("blob" +
                ".json");
        FileBlob fb = new FileBlob(fieldAsJsonFile);
        fb.setMimeType("image/jpeg");
        DocumentHelper.addBlob(doc.getProperty("file:content"), fb);
        session.saveDocument(doc);

        session.save();

    }

}
