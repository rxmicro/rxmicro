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

package io.rxmicro.config.internal.validator;

import io.rxmicro.config.Config;
import io.rxmicro.validation.ConstraintValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.internal.model.ConfigModelType.CONFIGURATION_PARAMETER;

/**
 * @author nedis
 * @since 0.12
 */
public final class ConfigPropertyValidators {

    private final static Map<String, ConfigValidatorDescriptor> descriptors = new ConcurrentHashMap<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void validateProperty(final Config instance,
                                        final String propertyName,
                                        final Object value) {
        final String fullPropertyName = format("?.?", instance.getNameSpace(), propertyName);
        final ConfigValidatorDescriptor descriptor =
                descriptors.computeIfAbsent(fullPropertyName, k -> ConfigValidatorDescriptorFactory.create(instance, propertyName));

        for (final ConstraintValidator validator : descriptor.getValidators()) {
            validator.validate(value, CONFIGURATION_PARAMETER, fullPropertyName);
        }
    }

    private ConfigPropertyValidators() {
    }
}
