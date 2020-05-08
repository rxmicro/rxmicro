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

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.data.local.EntityFromDBConverter;
import io.rxmicro.data.local.EntityToDBConverter;
import io.rxmicro.data.sql.r2dbc.detail.EntityFromR2DBCSQLDBConverter;
import io.rxmicro.data.sql.r2dbc.detail.EntityToR2DBCSQLDBConverter;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Map;

/**
 * @author nedis
 * @since 0.1
 */
public final class PostgreSQLDataObjectModelClass extends SQLDataObjectModelClass<SQLDataModelField> {

    public PostgreSQLDataObjectModelClass(final TypeMirror modelTypeMirror,
                                          final TypeElement modelTypeElement,
                                          final Map<SQLDataModelField, ModelClass> params) {
        super(modelTypeMirror, modelTypeElement, params);
    }

    @Override
    protected Class<? extends EntityToDBConverter> getEntityToDBConverterClass() {
        return EntityToR2DBCSQLDBConverter.class;
    }

    @Override
    protected Class<? extends EntityFromDBConverter> getEntityFromDBConverterClass() {
        return EntityFromR2DBCSQLDBConverter.class;
    }
}
