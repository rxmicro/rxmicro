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

package io.rxmicro.annotation.processor.documentation.component;

import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.type.EnumModelClass;
import io.rxmicro.json.JsonObjectBuilder;

/**
 * @author nedis
 * @since 0.1
 */
public interface JsonAttributesReader {

    void readStringPrimitiveAttributes(EnvironmentContext environmentContext,
                                       JsonObjectBuilder builder,
                                       ModelField modelField);

    void readDateTimePrimitiveAttributes(EnvironmentContext environmentContext,
                                         JsonObjectBuilder builder,
                                         ModelField modelField);

    void readNumberPrimitiveAttributes(EnvironmentContext environmentContext,
                                       JsonObjectBuilder builder,
                                       ModelField modelField);

    void readEnumAttributes(EnvironmentContext environmentContext,
                            JsonObjectBuilder builder,
                            ModelField modelField,
                            EnumModelClass enumModelClass);

    void readArrayAttributes(EnvironmentContext environmentContext,
                             JsonObjectBuilder builder,
                             ModelField modelField);
}
