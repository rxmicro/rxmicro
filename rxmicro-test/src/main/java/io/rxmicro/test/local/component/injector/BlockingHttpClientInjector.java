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

package io.rxmicro.test.local.component.injector;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.BlockingHttpClientSettings;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.builder.BlockingHttpClientSettingsRandomPortReader;

import java.lang.reflect.Field;
import java.util.List;

import static io.rxmicro.config.Networks.validatePort;
import static io.rxmicro.reflection.Reflections.getFieldValue;
import static io.rxmicro.reflection.Reflections.setFieldValue;
import static io.rxmicro.test.local.util.Annotations.getPresentOrDefaultAnnotation;
import static java.time.Duration.ofSeconds;

/**
 * @author nedis
 * @since 0.1
 */
public final class BlockingHttpClientInjector {

    private final BlockingHttpClientSettingsRandomPortReader reader =
            new BlockingHttpClientSettingsRandomPortReader();

    private final Field blockingHttpClientField;

    BlockingHttpClientInjector(final Field blockingHttpClientField) {
        this.blockingHttpClientField = blockingHttpClientField;
    }

    public boolean hasField() {
        return blockingHttpClientField != null;
    }

    public BlockingHttpClientConfig getConfig(final Class<?> testClass,
                                              final boolean expectFollowRedirects,
                                              final int defaultPort) {
        final BlockingHttpClientSettings settings =
                getPresentOrDefaultAnnotation(blockingHttpClientField, BlockingHttpClientSettings.class);
        validateConfig(settings);
        return new BlockingHttpClientConfig()
                .setBaseUrlPath(settings.baseUrlPath())
                .setBaseUrlPosition(settings.baseUrlPosition())
                .setVersionStrategy(settings.versionStrategy())
                .setVersionValue(settings.versionValue())
                .setSchema(settings.schema())
                .setHost(settings.host())
                .setPort(getPort(settings, testClass, defaultPort))
                .setRequestTimeout(ofSeconds(settings.requestTimeout()))
                .setFollowRedirects(settings.followRedirects().toBoolean(expectFollowRedirects));
    }

    private void validateConfig(final BlockingHttpClientSettings settings) {
        if (settings.port() > -1 && !settings.randomPortProvider().isEmpty()) {
            throw new InvalidTestConfigException(
                    "Only one parameter must be set: '@?.port' or '@?.randomPortProvider'!",
                    BlockingHttpClientSettings.class.getSimpleName(),
                    BlockingHttpClientSettings.class.getSimpleName()
            );
        }
    }

    private int getPort(final BlockingHttpClientSettings settings,
                        final Class<?> testClass,
                        final int defaultPort) {
        if (settings.port() > -1) {
            return validatePort(settings.port());
        } else {
            final String portProvider = settings.randomPortProvider();
            if (!portProvider.isEmpty()) {
                return reader.getPort(testClass, portProvider);
            } else {
                return defaultPort;
            }
        }
    }

    public void injectIfFound(final List<Object> testInstances,
                              final BlockingHttpClient blockingHttpClient) {
        if (hasField()) {
            if (getFieldValue(testInstances, this.blockingHttpClientField) != null) {
                throw new InvalidTestConfigException(
                        "Field with type '?' could not be initialized. Remove initialize statement!",
                        BlockingHttpClient.class.getSimpleName()
                );
            }
            setFieldValue(testInstances, this.blockingHttpClientField, blockingHttpClient);
        }
    }
}
