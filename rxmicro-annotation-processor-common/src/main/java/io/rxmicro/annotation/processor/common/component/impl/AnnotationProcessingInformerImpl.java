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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.AnnotationProcessingInformer;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class AnnotationProcessingInformerImpl extends AbstractProcessorComponent implements AnnotationProcessingInformer {

    private long classesGenerationStartedTime;

    @Override
    public void annotationProcessingStarted(final AnnotationProcessorType type) {
        info("");
        info("-------------------------------------------------------");
        info(type.infoMessage());
        info("-------------------------------------------------------");
    }

    @Override
    public void classesGenerationStarted() {
        classesGenerationStartedTime = System.nanoTime();
        info("Generating java classes...");

    }

    @Override
    public void classesGenerationCompleted() {
        if (classesGenerationStartedTime > 0) {
            info("All java classes generated successful in ? seconds.",
                    BigDecimal.valueOf(((double) (System.nanoTime() - classesGenerationStartedTime)) / 1_000_000_000.)
                            .setScale(3, HALF_UP));
        } else {
            info("All java classes generated successful.");
        }
    }

    @Override
    public void annotationProcessingCompleted(final AnnotationProcessorType type,
                                              final boolean successful) {
        info("-------------------------------------------------------");
        if (successful) {
            info("Annotations processing completed successful.");
        } else {
            error("Annotations processing completed with errors.");
        }
    }
}
