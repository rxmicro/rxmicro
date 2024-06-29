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
import io.rxmicro.annotation.processor.config.SupportedOptions;
import io.rxmicro.annotation.processor.data.aggregator.DataRepositoryModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.rest.client.RestClientModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.rest.server.RestServerModuleClassStructuresBuilder;

import java.util.List;

import static io.rxmicro.annotation.processor.common.util.InternalLoggers.logThrowableStackTrace;
import static io.rxmicro.common.util.Exceptions.reThrow;

/**
 * This is the {@code RxMicro Annotation Processor}.
 *
 * <p>
 * The RxMicro framework uses the {@link javax.annotation.processing.Processor}, which generates standard code using RxMicro annotations.
 *
 * <p>
 * Thus, the RxMicro framework is a framework of declarative programming.
 *
 * <p>
 * Using the RxMicro framework, the developer focuses on writing the business logic of a microservice.
 * Then he configures the desired standard behavior with RxMicro annotations.
 * When compiling a project, the {@code RxMicro Annotation Processor} generates additional classes.
 * Generated classes contain a standard logic that ensures the functionality of the created microservice.
 *
 * <p>
 * <strong>How it works?</strong>
 *
 * <p>
 * While solving a business task, the developer writes Micro service source code.
 * Then the developer configures the desired standard microservice behavior via RxMicro annotations.
 * After that, the developer compiles the project.
 *
 * <p>
 * Since the {@code RxMicro Annotation Processor} is configured in maven, when compiling a project this processor handles
 * the source code of the microservice and generates the additional classes: Micro service generated code.
 * After that, the compiler compiles the source and generated microservice codes:
 * Micro service byte code and Micro service generated byte code.
 *
 * <p>
 * The compiled source and generated codes along with the RxMicro runtime libraries perform useful work.
 *
 * @author nedis
 * @see SupportedOptions#RX_MICRO_MAX_JSON_NESTED_DEPTH
 * @see SupportedOptions#RX_MICRO_LOG_LEVEL
 * @see io.rxmicro.annotation.processor.config.LogLevel
 * @see SupportedOptions#RX_MICRO_DOC_DESTINATION_DIR
 * @see io.rxmicro.annotation.processor.config.DocumentationType
 * @see SupportedOptions#RX_MICRO_DOC_ANALYZE_PARENT_POM
 * @see SupportedOptions#RX_MICRO_BUILD_UNNAMED_MODULE
 * @see SupportedOptions#RX_MICRO_STRICT_MODE
 * @see SupportedOptions#RX_MICRO_LIBRARY_MODULE
 * @see SupportedOptions#ALL_SUPPORTED_OPTIONS
 * @see <a href="https://docs.rxmicro.io/latest/user-guide/core.html#core-how-it-works-section">
 * Documentation -&gt; Core -&gt; How It Works
 * </a>
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
        } catch (final Throwable throwable) {
            logThrowableStackTrace(throwable);
            return reThrow(throwable);
        }
    }

    /**
     * Default constructor for the {@code RxMicro Annotation Processor}.
     */
    public RxMicroAnnotationProcessor() {
        super(create());
    }
}
