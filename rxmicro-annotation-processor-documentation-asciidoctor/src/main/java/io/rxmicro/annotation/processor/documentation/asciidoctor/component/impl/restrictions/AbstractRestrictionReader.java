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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions;

import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.constraint.Nullable;

import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader.OPTIONAL_RESTRICTION;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader.REQUIRED_RESTRICTION;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
abstract class AbstractRestrictionReader {

    void readRequired(final EnvironmentContext environmentContext,
                      final List<String> restrictions,
                      final List<ReadMore> readMores,
                      final Map.Entry<RestModelField, ModelClass> entry) {
        final RestModelField field = entry.getKey();
        final Nullable nullable = field.getAnnotation(Nullable.class);
        if (environmentContext.isRxMicroModuleEnabled(RxMicroModule.RX_MICRO_VALIDATION_MODULE) &&
                (nullable == null || nullable.off())) {
            restrictions.add(REQUIRED_RESTRICTION);
        } else {
            restrictions.add(OPTIONAL_RESTRICTION);
            getReadMore(Nullable.class).ifPresent(readMores::add);
        }
    }
}
