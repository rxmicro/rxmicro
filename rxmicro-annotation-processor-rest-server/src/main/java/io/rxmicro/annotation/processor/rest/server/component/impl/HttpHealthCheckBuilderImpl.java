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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.rest.server.component.HttpHealthCheckBuilder;
import io.rxmicro.annotation.processor.rest.server.model.HttpHealthCheck;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.monitoring.healthcheck.EnableHttpHealthCheck;

import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class HttpHealthCheckBuilderImpl implements HttpHealthCheckBuilder {

    @Override
    public Set<HttpHealthCheck> build(final EnvironmentContext environmentContext,
                                      final Set<RestControllerClassStructure> restControllerClassStructures) {
        final Set<HttpHealthCheck> set = new TreeSet<>();
        final EnableHttpHealthCheck enableHttpHealthCheckOnModule =
                environmentContext.getCurrentModule().getAnnotation(EnableHttpHealthCheck.class);
        if (enableHttpHealthCheckOnModule != null) {
            set.add(new HttpHealthCheck(
                            enableHttpHealthCheckOnModule.method(),
                            normalizeUrlPath(enableHttpHealthCheckOnModule.endpoint())
                    )
            );
        }
        for (final RestControllerClassStructure restControllerClassStructure : restControllerClassStructures) {
            final EnableHttpHealthCheck enableHttpHealthCheck =
                    restControllerClassStructure.getOwnerClass().getAnnotation(EnableHttpHealthCheck.class);
            if (enableHttpHealthCheck != null) {
                set.add(new HttpHealthCheck(
                                enableHttpHealthCheck.method(),
                                restControllerClassStructure.getParentUrl().getFullUrlPath(enableHttpHealthCheck.endpoint())
                        )
                );
            }
        }
        return set;
    }
}
