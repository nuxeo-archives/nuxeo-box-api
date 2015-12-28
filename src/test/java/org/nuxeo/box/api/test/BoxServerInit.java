/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and others.
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
 *
 * Contributors:
 *     Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package org.nuxeo.box.api.test;

import java.io.File;
import java.io.IOException;

import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.automation.core.util.DocumentHelper;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.Blobs;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.test.annotations.RepositoryInit;

/**
 * Initialization of box testing repository
 *
 * @since 5.9.2
 */
public class BoxServerInit implements RepositoryInit {

    public static DocumentModel getFolder(int index, CoreSession session) {
        return session.getDocument(new PathRef("/folder_" + index));
    }

    public static DocumentModel getNote(int index, CoreSession session) {
        return session.getDocument(new PathRef("/folder_1/note_" + index));
    }

    public static DocumentModel getFile(int index, CoreSession session) {
        return session.getDocument(new PathRef("/folder_2/file"));
    }

    @Override
    public void populate(CoreSession session) {
        // Create some docs
        for (int i = 0; i < 5; i++) {
            DocumentModel doc = session.createDocumentModel("/", "folder_" + i, "Folder");
            doc.setPropertyValue("dc:title", "Folder " + i);
            doc = session.createDocument(doc);
        }

        for (int i = 0; i < 5; i++) {
            DocumentModel doc = session.createDocumentModel("/folder_1", "note_" + i, "Note");
            doc.setPropertyValue("dc:title", "Note " + i);

            doc.setPropertyValue("note:note", "Note " + i);
            doc = session.createDocument(doc);
        }

        // Create a file
        DocumentModel doc = session.createDocumentModel("/folder_2", "file", "File");
        doc.setPropertyValue("dc:title", "File");
        doc = session.createDocument(doc);
        // upload file blob
        try {
            File fieldAsJsonFile = FileUtils.getResourceFileFromContext("blob" + ".json");
            Blob fb = Blobs.createBlob(fieldAsJsonFile);
            fb.setMimeType("image/jpeg");
            DocumentHelper.addBlob(doc.getProperty("file:content"), fb);
            session.saveDocument(doc);
        } catch (IOException e) {
            throw new NuxeoException(e);
        }

        session.save();

    }

}
