/*
 * Copyright (c) 2020. https://rxmicro.io
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

package io.rxmicro.annotation.processor.data.sql.r2dbc.model;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataRepositoryMethod;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractSQLDataRepositoryClassStructure extends DataRepositoryClassStructure {

    public AbstractSQLDataRepositoryClassStructure(final ClassHeader.Builder classHeaderBuilder,
                                                   final TypeElement repositoryInterface,
                                                   final TypeElement abstractClass,
                                                   final String configNameSpace,
                                                   final List<SQLDataRepositoryMethod> methods,
                                                   final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues) {
        super(classHeaderBuilder, repositoryInterface, abstractClass, configNameSpace, methods, defaultConfigValues);
    }

    @Override
    public String getTemplateName() {
        return "data/sql/r2dbc/$$SQLDataRepositoryTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        final String packageName = getPackageName();
        map.put("JAVA_PACKAGE", packageName);
        map.put("JAVA_REPOSITORY_INTERFACE", getSimpleInterfaceName());
        map.put("JAVA_REPOSITORY_IMPL_CLASS", getTargetSimpleClassName());
        map.put("JAVA_REPOSITORY_ABSTRACT_CLASS", getSimpleName(abstractClass));
        map.put("JAVA_REPOSITORY_METHODS", methods);
        map.put("JAVA_MODEL_TRANSFORMERS", modelTransformers);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        classHeaderBuilder
                .addImports(ConnectionPool.class)
                .addImports(abstractClass);
        modelTransformers.forEach(c -> classHeaderBuilder.addImports(c.getJavaFullClassName()));
        return classHeaderBuilder.build();
    }
}
