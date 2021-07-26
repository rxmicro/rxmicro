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

package io.rxmicro.annotation.processor.common.model.virtual;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_VIRTUAL_CLASS_SUB_PREFIX;

/**
 * @author nedis
 * @since 0.1
 */
final class VirtualNames {

    public static final String OWNER_SIMPLE_CLASS_NAME = "${owner}";

    public static final String INDEX = "${index}";

    private static final Map<String, AtomicInteger> COUNTER = new HashMap<>();

    static String buildVirtualClassName(final String classNameTemplate,
                                        final ExecutableElement method) {
        final Element classElement = method.getEnclosingElement();
        final String owner = getOwner(classElement);
        final int index = COUNTER.computeIfAbsent(classElement.asType().toString(), t -> new AtomicInteger(0)).incrementAndGet();
        return format("???",
                GENERATED_CLASS_NAME_PREFIX,
                GENERATED_VIRTUAL_CLASS_SUB_PREFIX,
                classNameTemplate
                        .replace(OWNER_SIMPLE_CLASS_NAME, owner)
                        .replace(INDEX, index == 1 ? EMPTY_STRING : String.valueOf(index))
        );
    }

    private static String getOwner(final Element classElement) {
        final String simpleName = classElement.getSimpleName().toString();
        return simpleName.replace("MicroService", EMPTY_STRING)
                .replace("Microservice", EMPTY_STRING)
                .replace("Service", EMPTY_STRING)
                .replace("RestController", EMPTY_STRING)
                .replace("Controller", EMPTY_STRING)
                .replace("RestClient", EMPTY_STRING)
                .replace("Client", EMPTY_STRING);
    }

    private VirtualNames() {
    }
}
