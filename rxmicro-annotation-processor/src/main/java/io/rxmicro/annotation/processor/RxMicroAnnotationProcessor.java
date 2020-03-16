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

package io.rxmicro.annotation.processor;

import io.rxmicro.annotation.processor.cdi.CDIClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.BaseRxMicroAnnotationProcessor;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.component.impl.CompositeModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.data.aggregator.DataRepositoryModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.rest.client.RestClientModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.rest.server.RestServerModuleClassStructuresBuilder;

import java.util.List;

import static io.rxmicro.common.util.Exceptions.reThrow;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RxMicroAnnotationProcessor extends BaseRxMicroAnnotationProcessor {

    private static CompositeModuleClassStructuresBuilder<AbstractModuleClassStructuresBuilder> create() {
        try {
            return new CompositeModuleClassStructuresBuilder<>(
                    List.of(
                            RestServerModuleClassStructuresBuilder.create(),
                            RestClientModuleClassStructuresBuilder.create(),
                            DataRepositoryModuleClassStructuresBuilder.create(),
                            CDIClassStructuresBuilder.create()
                    )
            );
        } catch (final Throwable th) {
            th.printStackTrace();
            return reThrow(th);
        }
    }

    public RxMicroAnnotationProcessor() {
        super(create());
    }
}
