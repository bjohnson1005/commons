/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.charon.core.schema;

import java.util.Arrays;
import java.util.List;

/**
 * This declares the SCIM resources schema as specified in SCIM spec.
 */
public class SCIMResourceSchema implements ResourceSchema {

    private String name;
    private String schema;
    //private List<String> schemaList;
    private String description;
    private String endpoint;
    private List<AttributeSchema> attributeSchemas;

    //define a private method to add core schema attributes to every SCIM resource schema.
    private void addCoreSchema(){

    }
    public SCIMResourceSchema(String name, String schema, String description, String endpoint,
                              AttributeSchema... attributeSchemas) {
        this.name = name;
        this.schema = schema;
        this.description = description;
        this.endpoint = endpoint;
        addCoreSchema();
        this.attributeSchemas = Arrays.asList(attributeSchemas);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schemaName) {
        this.schema = schemaName;
        //TODO:set schema as an attribute as well.
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getAttributesList() {
        return attributeSchemas;
    }

    public void setAttributeList(List attributesList) {
        this.attributeSchemas = attributesList;
    }
}
