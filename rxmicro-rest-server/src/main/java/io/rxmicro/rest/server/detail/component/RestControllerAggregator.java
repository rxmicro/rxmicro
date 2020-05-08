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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.rest.server.local.model.RestControllerRegistrationFilter;

import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;

/**
 * Used by generated code that was created by {@code RxMicro Annotation Processor}
 *
 * @author nedis
 * @since 0.1
 */
public abstract class RestControllerAggregator {

    public static final String REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME =
            format("??Impl", GENERATED_CLASS_NAME_PREFIX, RestControllerAggregator.class.getSimpleName());

    private static final Set<Class<?>> INTERNAL_CLASSES = Set.of(
            CrossOriginResourceSharingPreflightRestController.class,
            HttpHealthCheckRestController.class,
            BadHttpRequestRestController.class
    );

    public final int register(final RestControllerRegistrar registrar,
                              final RestControllerRegistrationFilter restControllerRegistrationFilter) {
        final int[] result = {0};
        listAllRestControllers().stream()
                .filter(s -> INTERNAL_CLASSES.contains(s.getClass()) ||
                        restControllerRegistrationFilter.test(s.getRestControllerClass()))
                .forEach(s -> {
                    s.register(registrar);
                    result[0]++;
                });
        return result[0];
    }

    protected abstract List<AbstractRestController> listAllRestControllers();
}
