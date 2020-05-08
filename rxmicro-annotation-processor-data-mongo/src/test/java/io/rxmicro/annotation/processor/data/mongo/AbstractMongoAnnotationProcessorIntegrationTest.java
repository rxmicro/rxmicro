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

package io.rxmicro.annotation.processor.data.mongo;

import io.rxmicro.annotation.processor.common.BaseRxMicroAnnotationProcessor;
import io.rxmicro.annotation.processor.integration.test.AbstractRxMicroAnnotationProcessorIntegrationTest;

import javax.annotation.processing.Processor;

import static io.rxmicro.data.RepositoryFactory.REPOSITORY_FACTORY_IMPL_CLASS_NAME;

/**
 * @author nedis
 *
 */
public abstract class AbstractMongoAnnotationProcessorIntegrationTest
        extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    public AbstractMongoAnnotationProcessorIntegrationTest() {
        super(REPOSITORY_FACTORY_IMPL_CLASS_NAME);
    }

    @Override
    protected final Processor createAnnotationProcessor() {
        return new BaseRxMicroAnnotationProcessor(MongoModuleClassStructuresBuilder.create());
    }
}
