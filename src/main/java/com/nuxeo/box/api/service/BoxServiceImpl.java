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
 *     vpasquier <vpasquier@nuxeo.com>
 */
package com.nuxeo.box.api.service;

import com.nuxeo.box.api.dao.BoxCollection;
import com.nuxeo.box.api.dao.BoxObject;
import com.nuxeo.box.api.exceptions.BoxJSONException;
import com.nuxeo.box.api.jsonparsing.BoxJSONParser;
import com.nuxeo.box.api.jsonparsing.BoxResourceHub;
import org.nuxeo.ecm.core.api.DocumentModelList;

/**
 * Box Service Utils
 *
 * @since 5.9.3
 */
public class BoxServiceImpl implements BoxService {

    public BoxCollection getBoxCollection(DocumentModelList documentModelList) {
        BoxCollection boxCollection = new BoxCollection();
        return boxCollection;
    }

    public String toJSONString(BoxObject boxObject) throws BoxJSONException {
        BoxJSONParser boxJSONParser = new BoxJSONParser(new
                BoxResourceHub());
        return boxObject.toJSONString(boxJSONParser);
    }
}
