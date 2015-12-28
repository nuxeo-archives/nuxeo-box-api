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
package org.nuxeo.box.api.adapter;

import org.nuxeo.box.api.comment.adapter.BoxCommentAdapter;
import org.nuxeo.box.api.file.adapter.BoxFileAdapter;
import org.nuxeo.box.api.folder.adapter.BoxFolderAdapter;
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
        if ("File".equals(doc.getType())) {
            return new BoxFileAdapter(doc);
        } else if (doc.isFolder()) {
            return new BoxFolderAdapter(doc);
        } else if ("Comment".equals(doc.getType())) {
            return new BoxCommentAdapter(doc);
        } else {
            return null;
        }
    }
}
