/**
 *
 */

package com.nuxeo.box.api.folder.io;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.adapter.DocumentAdapterFactory;

/**
 * @author vladimirpasquier
 */
public class BoxFolderFactory implements DocumentAdapterFactory {

    @Override
    public Object getAdapter(DocumentModel doc, Class<?> itf) {
        if (doc.isFolder()) {
            return new BoxFolderAdapter(doc);
        } else {
            return null;
        }
    }
}
