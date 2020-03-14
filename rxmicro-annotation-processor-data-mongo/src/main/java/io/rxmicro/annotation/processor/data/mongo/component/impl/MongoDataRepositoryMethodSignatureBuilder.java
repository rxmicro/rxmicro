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

package io.rxmicro.annotation.processor.data.mongo.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataRepositoryMethodSignatureBuilder;
import io.rxmicro.data.mongo.operation.Delete;
import io.rxmicro.data.mongo.operation.Insert;
import io.rxmicro.data.mongo.operation.Update;

import java.lang.annotation.Annotation;
import java.util.Set;

import static io.rxmicro.data.mongo.local.MongoOperations.MONGO_OPERATION_ANNOTATIONS;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class MongoDataRepositoryMethodSignatureBuilder extends AbstractDataRepositoryMethodSignatureBuilder {

    private static final Set<Class<? extends Annotation>> OPERATION_RETURN_VOID_SET =
            Set.of(Insert.class, Delete.class, Update.class);

    @Override
    protected Set<Class<? extends Annotation>> getOperationReturnVoidSet() {
        return OPERATION_RETURN_VOID_SET;
    }

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotationsPerMethod() {
        return MONGO_OPERATION_ANNOTATIONS;
    }
}
