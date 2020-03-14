/*
 * Copyright (c) 2020. http://rxmicro.io
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
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataRepositoryMethodSignatureBuilder;
import io.rxmicro.data.sql.VariableValues;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static io.rxmicro.data.sql.r2dbc.postgresql.local.PostgreSQLOperations.POSTGRESQL_OPERATION_ANNOTATIONS;
import static java.util.Collections.unmodifiableSet;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class PostgreSQLDataRepositoryMethodSignatureBuilder extends AbstractDataRepositoryMethodSignatureBuilder {

    @Override
    protected Set<Class<? extends Annotation>> getOperationReturnVoidSet() {
        return Set.of();
    }

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotationsPerMethod() {
        final Set<Class<? extends Annotation>> annotations = new HashSet<>(POSTGRESQL_OPERATION_ANNOTATIONS);
        annotations.add(VariableValues.class);
        return unmodifiableSet(annotations);
    }
}
