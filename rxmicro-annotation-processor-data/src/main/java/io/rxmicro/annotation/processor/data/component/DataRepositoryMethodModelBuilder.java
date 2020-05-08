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

package io.rxmicro.annotation.processor.data.component;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethod;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.data.DataRepositoryGeneratorConfig;

import java.lang.annotation.Annotation;

/**
 * @author nedis
 * @since 0.1
 */
public interface DataRepositoryMethodModelBuilder<DMF extends DataModelField, DRM extends DataRepositoryMethod, DMC extends DataObjectModelClass<DMF>> {

    default boolean isSupported(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                                final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        return dataRepositoryMethodSignature.getMethod().getAnnotation(operationType()) != null;
    }

    DRM build(DataRepositoryMethodSignature dataRepositoryMethodSignature,
              ClassHeader.Builder classHeaderBuilder,
              DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
              DataGenerationContext<DMF, DMC> dataGenerationContext);

    Class<? extends Annotation> operationType();
}
