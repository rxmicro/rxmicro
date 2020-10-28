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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.data.sql.component.AbstractSQLDataModelFieldBuilder;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PostgreSQLDataModelFieldBuilderImpl
        extends AbstractSQLDataModelFieldBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass> {

    @Override
    protected PostgreSQLDataObjectModelClass createObjectModelClass(final ModuleElement currentModule,
                                                                    final ModelFieldType modelFieldType,
                                                                    final TypeMirror type,
                                                                    final TypeElement typeElement,
                                                                    final int nestedLevel,
                                                                    final boolean requireDefConstructor) {
        return new PostgreSQLDataObjectModelClass(
                type,
                typeElement,
                getFieldMap(currentModule, modelFieldType, asTypeElement(type).orElseThrow(), nestedLevel, requireDefConstructor)
        );
    }

    @Override
    protected SQLDataModelField build(final AnnotatedModelElement annotated,
                                      final String modelName,
                                      final int length,
                                      final boolean nullable,
                                      final boolean isId) {
        return new SQLDataModelField(annotated, modelName, length, nullable);
    }
}
