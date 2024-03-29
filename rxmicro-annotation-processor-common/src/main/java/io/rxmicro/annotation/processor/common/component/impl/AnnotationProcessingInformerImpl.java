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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.AnnotationProcessingInformer;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;

import java.math.BigDecimal;

import static io.rxmicro.annotation.processor.common.util.LoggerMessages.LOG_MESSAGE_LINE_DELIMITER;
import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.CommonConstants.NANOS_IN_1_SECOND;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class AnnotationProcessingInformerImpl extends BaseProcessorComponent implements AnnotationProcessingInformer {

    private long classesGenerationStartedTime;

    @Override
    public void annotationProcessingStarted(final AnnotationProcessorType type) {
        info(EMPTY_STRING);
        info(LOG_MESSAGE_LINE_DELIMITER);
        info(type.infoMessage());
        info(LOG_MESSAGE_LINE_DELIMITER);
        info(EMPTY_STRING);
    }

    @Override
    public void classesGenerationStarted() {
        classesGenerationStartedTime = System.nanoTime();
        info("Generating java classes...");

    }

    @Override
    public void classesGenerationCompleted() {
        if (classesGenerationStartedTime > 0) {
            final BigDecimal spendTime = BigDecimal.valueOf(
                    ((double) (System.nanoTime() - classesGenerationStartedTime)) / NANOS_IN_1_SECOND
            ).setScale(3, HALF_UP);
            info("All java classes generated successful in ? seconds.", spendTime);
        } else {
            info("All java classes generated successful.");
        }
    }

    @Override
    public void annotationProcessingCompleted(final AnnotationProcessorType type,
                                              final boolean successful) {
        info(LOG_MESSAGE_LINE_DELIMITER);
        if (successful) {
            info("Annotations processing completed successful.");
        } else {
            error("Annotations processing completed with errors.");
        }
        info(LOG_MESSAGE_LINE_DELIMITER);
    }
}
