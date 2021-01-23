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

package io.rxmicro.config.internal.model;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static io.rxmicro.config.internal.Converters.convertToType;
import static io.rxmicro.reflection.Reflections.invokeMethod;
import static java.lang.String.join;
import static java.util.Locale.ENGLISH;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class ConfigProperty implements Comparable<ConfigProperty> {

    private final String propertyName;

    private final String fullPropertyName;

    private final String systemPropertyFullPropertyName;

    private final Method propertySetter;

    private final Object configInstance;

    private Object propertyValue;

    public ConfigProperty(final String namespace,
                          final String upperNamespace,
                          final String propertyName,
                          final Method propertySetter,
                          final Object configInstance) {
        this.propertyName = propertyName;
        this.fullPropertyName = namespace + "." + propertyName;
        this.systemPropertyFullPropertyName = upperNamespace + '_' + join("_", splitByCamelCase(propertyName)).toUpperCase(ENGLISH);
        this.propertySetter = propertySetter;
        this.configInstance = configInstance;
    }

    public String getPropertyName(final boolean useFullName) {
        return useFullName ? fullPropertyName : propertyName;
    }

    public String getSystemVariablePropertyName() {
        return systemPropertyFullPropertyName;
    }

    public <T> Optional<Map.Entry<String, T>> resolve(final Map<String, T> properties,
                                                      final boolean useFullName,
                                                      final boolean isEnvironmentVariable) {
        final String property = useFullName ? fullPropertyName : propertyName;
        final T value = properties.get(property);
        if (value != null) {
            propertyValue = value;
            return Optional.of(entry(property, value));
        } else if (isEnvironmentVariable) {
            final T envVarValue = properties.get(systemPropertyFullPropertyName);
            if (envVarValue != null) {
                propertyValue = envVarValue;
                return Optional.of(entry(property, envVarValue));
            }
        }
        return Optional.empty();
    }

    public Optional<Map.Entry<String, String>> resolve(final Properties properties,
                                                       final boolean useFullName) {
        final String property = useFullName ? fullPropertyName : propertyName;
        final String value = properties.getProperty(property);
        if (value != null) {
            propertyValue = value;
            return Optional.of(entry(property, value));
        } else {
            return Optional.empty();
        }
    }

    public void setProperty() {
        if (propertyValue != null) {
            if (propertyValue instanceof String) {
                invokeMethod(configInstance, propertySetter, convertToType(propertySetter.getParameterTypes()[0], (String) propertyValue));
            } else {
                invokeMethod(configInstance, propertySetter, ((Supplier<?>) propertyValue).get());
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullPropertyName);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final ConfigProperty that = (ConfigProperty) other;
        return fullPropertyName.equals(that.fullPropertyName);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public int compareTo(final ConfigProperty other) {
        return other == null ? 1 : fullPropertyName.compareTo(other.fullPropertyName);
    }
}
