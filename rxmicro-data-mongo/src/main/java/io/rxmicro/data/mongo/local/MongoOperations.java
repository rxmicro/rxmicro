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

package io.rxmicro.data.mongo.local;

import io.rxmicro.data.mongo.operation.Aggregate;
import io.rxmicro.data.mongo.operation.CountDocuments;
import io.rxmicro.data.mongo.operation.Delete;
import io.rxmicro.data.mongo.operation.Distinct;
import io.rxmicro.data.mongo.operation.EstimatedDocumentCount;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.data.mongo.operation.Insert;
import io.rxmicro.data.mongo.operation.Update;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MongoOperations {

    public static final Set<Class<? extends Annotation>> MONGO_OPERATION_ANNOTATIONS = Set.of(
            Aggregate.class,
            CountDocuments.class,
            Delete.class,
            Distinct.class,
            EstimatedDocumentCount.class,
            Find.class,
            Insert.class,
            Update.class
    );

    private MongoOperations() {
    }
}
