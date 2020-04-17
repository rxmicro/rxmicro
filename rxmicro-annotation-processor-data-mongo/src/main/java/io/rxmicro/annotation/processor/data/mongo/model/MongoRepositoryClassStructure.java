/*
 * Copyright 2019 https://rxmicro.io
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
 */

package io.rxmicro.annotation.processor.data.mongo.model;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.data.local.EntityFromDBConverter;
import io.rxmicro.data.local.EntityToDBConverter;
import io.rxmicro.data.mongo.detail.EntityFromMongoDBConverter;
import io.rxmicro.data.mongo.detail.EntityToMongoDBConverter;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MongoRepositoryClassStructure extends DataRepositoryClassStructure {

    private final String collectionName;

    public MongoRepositoryClassStructure(final ClassHeader.Builder classHeaderBuilder,
                                         final TypeElement repositoryInterface,
                                         final TypeElement abstractClass,
                                         final String collectionName,
                                         final String configNameSpace,
                                         final List<MongoRepositoryMethod> methods,
                                         final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues) {
        super(classHeaderBuilder, repositoryInterface, abstractClass, configNameSpace, methods, defaultConfigValues);
        this.collectionName = require(collectionName);
    }

    @Override
    protected String getRepositoryTypePrefix() {
        return "Mongo";
    }

    @Override
    protected Class<? extends EntityToDBConverter> getEntityToDBConverterClass() {
        return EntityToMongoDBConverter.class;
    }

    @Override
    protected Class<? extends EntityFromDBConverter> getEntityFromDBConverterClass() {
        return EntityFromMongoDBConverter.class;
    }

    @Override
    public String getTemplateName() {
        return "data/mongo/$$MongoRepositoryTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        final String packageName = getPackageName();
        map.put("JAVA_PACKAGE", packageName);
        map.put("JAVA_REPOSITORY_INTERFACE", getSimpleInterfaceName());
        map.put("JAVA_REPOSITORY_IMPL_CLASS", getTargetSimpleClassName());
        map.put("JAVA_REPOSITORY_ABSTRACT_CLASS", getSimpleName(abstractClass));
        map.put("MONGO_COLLECTION_NAME", collectionName);
        map.put("JAVA_REPOSITORY_METHODS", methods);
        map.put("JAVA_MODEL_TRANSFORMERS", modelTransformers);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        classHeaderBuilder
                .addImports(MongoDatabase.class)
                .addImports(abstractClass);
        modelTransformers.forEach(c -> classHeaderBuilder.addImports(c.getJavaFullClassName()));
        return classHeaderBuilder.build();
    }
}
