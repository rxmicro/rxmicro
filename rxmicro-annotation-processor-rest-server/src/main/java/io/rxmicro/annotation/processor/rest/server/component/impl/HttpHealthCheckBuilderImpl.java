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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.server.component.HttpHealthCheckBuilder;
import io.rxmicro.annotation.processor.rest.server.model.HttpHealthCheck;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.monitoring.healthcheck.EnableHttpHealthCheck;

import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.monitoring.healthcheck.EnableHttpHealthCheck.HTTP_HEALTH_CHECK_ENDPOINT;

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
            set.add(new HttpHealthCheck(enableHttpHealthCheckOnModule.method(), HTTP_HEALTH_CHECK_ENDPOINT));
        }
        for (final RestControllerClassStructure restControllerClassStructure : restControllerClassStructures) {
            final EnableHttpHealthCheck enableHttpHealthCheck =
                    restControllerClassStructure.getOwnerClass().getAnnotation(EnableHttpHealthCheck.class);
            if (enableHttpHealthCheck != null) {
                if (!set.isEmpty()) {
                    throw new InterruptProcessingException(
                            restControllerClassStructure.getOwnerClass(),
                            "The RxMicro framework supports only one @? annotation per project",
                            EnableHttpHealthCheck.class.getName()
                    );
                }
                set.add(new HttpHealthCheck(enableHttpHealthCheck.method(), HTTP_HEALTH_CHECK_ENDPOINT));
            }
        }
        return set;
    }
}
