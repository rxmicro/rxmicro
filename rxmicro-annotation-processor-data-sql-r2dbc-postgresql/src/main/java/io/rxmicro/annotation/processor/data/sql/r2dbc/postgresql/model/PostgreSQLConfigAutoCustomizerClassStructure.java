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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.data.model.DataRepositoryConfigAutoCustomizerClassStructure;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfigCustomizer;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.PostgreSQLConfigAutoCustomizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.data.sql.r2dbc.postgresql.detail.PostgreSQLConfigAutoCustomizer.POSTGRES_SQL_CONFIG_AUTO_CUSTOMIZER_CLASS_NAME;
import static io.rxmicro.runtime.detail.RxMicroRuntime.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @since 0.7
 */
public final class PostgreSQLConfigAutoCustomizerClassStructure extends DataRepositoryConfigAutoCustomizerClassStructure {

    private final List<Map.Entry<TypeElement, String>> postgresEnumMapping;

    public PostgreSQLConfigAutoCustomizerClassStructure(final List<Map.Entry<TypeElement, String>> postgresEnumMapping) {
        this.postgresEnumMapping = require(postgresEnumMapping);
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(POSTGRES_SQL_CONFIG_AUTO_CUSTOMIZER_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "data/sql/r2dbc/postgresql/$$PostgreSQLConfigCustomizerTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("IMPL_CLASS_NAME", POSTGRES_SQL_CONFIG_AUTO_CUSTOMIZER_CLASS_NAME);
        map.put("POSTGRES_ENUM_MAPPING", postgresEnumMapping);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        return newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                .addImports(PostgreSQLConfigAutoCustomizer.class)
                .addStaticImport(PostgreSQLConfigCustomizer.class, "registerPostgreSQLCodecs")
                .addImports(postgresEnumMapping.stream().map(Map.Entry::getKey).toArray(TypeElement[]::new))
                .addImports(Map.class)
                .build();
    }
}
