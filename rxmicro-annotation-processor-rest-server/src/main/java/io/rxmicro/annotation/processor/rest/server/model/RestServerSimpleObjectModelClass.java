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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.AbstractSimpleObjectModelClass;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.component.ServerModelWriter;

import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestServerSimpleObjectModelClass extends AbstractSimpleObjectModelClass {

    public RestServerSimpleObjectModelClass(final TypeElement typeElement) {
        super(typeElement);
    }

    @UsedByFreemarker(
            "$$RestControllerTemplate.javaftl"
    )
    public String getModelReaderImplSimpleClassName() {
        return getModelTransformerSimpleClassName(typeElement, ServerModelReader.class);
    }

    @UsedByFreemarker(
            "$$RestControllerTemplate.javaftl"
    )
    public String getModelReaderInstanceName() {
        return getModelTransformerInstanceName(typeElement, ServerModelReader.class);
    }

    @UsedByFreemarker(
            "$$RestControllerTemplate.javaftl"
    )
    public String getModelWriterImplSimpleClassName() {
        return getModelTransformerSimpleClassName(typeElement, ServerModelWriter.class);
    }

    @UsedByFreemarker(
            "$$RestControllerTemplate.javaftl"
    )
    public String getModelWriterInstanceName() {
        return getModelTransformerInstanceName(typeElement, ServerModelWriter.class);
    }
}
