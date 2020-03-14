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
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataRepositoryInterfaceSignatureBuilder;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.PartialImplementation;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;

import java.lang.annotation.Annotation;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class MongoDataRepositoryInterfaceSignatureBuilder extends AbstractDataRepositoryInterfaceSignatureBuilder {

    @Override
    protected Class<? extends Annotation> getRepositoryMarkerAnnotationClass() {
        return MongoRepository.class;
    }

    @Override
    protected Class<? extends Annotation> getPartialImplementationAnnotationClass() {
        return PartialImplementation.class;
    }

    @Override
    protected Class<?> getDefaultAbstractClass() {
        return AbstractMongoRepository.class;
    }
}
