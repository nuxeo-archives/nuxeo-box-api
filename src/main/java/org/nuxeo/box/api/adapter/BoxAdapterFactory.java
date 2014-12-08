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
package org.nuxeo.box.api.adapter;

import org.nuxeo.box.api.comment.adapter.BoxCommentAdapter;
import org.nuxeo.box.api.file.adapter.BoxFileAdapter;
import org.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.adapter.DocumentAdapterFactory;

/**
 * Box Adapter Factory - instantiate implementations related to document type
 *
 * @since 5.9.2
 */
public class BoxAdapterFactory implements DocumentAdapterFactory {

    @Override
    public Object getAdapter(final DocumentModel doc, final Class<?> itf) {
        try {
            if ("File".equals(doc.getType())) {
                return new BoxFileAdapter(doc);
            } else if (doc.isFolder()) {
                return new BoxFolderAdapter(doc);
            } else if ("Comment".equals(doc.getType())) {
                return new BoxCommentAdapter(doc);
            } else {
                return null;
            }
        } catch (ClientException e) {
            return e;
        }
    }
}
