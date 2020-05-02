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

package io.rxmicro.config.internal.model;

import io.rxmicro.common.CheckedWrapperException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

import static io.rxmicro.config.internal.Converters.convert;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ConfigProperty implements Comparable<ConfigProperty> {

    private final String propertyName;

    private final String fullPropertyName;

    private final Method propertySetter;

    private final Object configInstance;

    private Object propertyValue;

    public ConfigProperty(final String nameSpace,
                          final String propertyName,
                          final Method propertySetter,
                          final Object configInstance) {
        this.propertyName = propertyName;
        this.fullPropertyName = nameSpace + "." + propertyName;
        this.propertySetter = propertySetter;
        this.configInstance = configInstance;
    }

    public <T> Optional<Map.Entry<String, T>> resolve(final Map<String, T> properties,
                                                      final boolean useFullName) {
        final String property = useFullName ? fullPropertyName : propertyName;
        final T value = properties.get(property);
        if (value != null) {
            propertyValue = value;
            return Optional.of(entry(property, value));
        } else {
            return Optional.empty();
        }
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
            try {
                if (propertyValue instanceof String) {
                    propertySetter.invoke(configInstance, convert(propertySetter.getParameterTypes()[0], (String) propertyValue));
                } else {
                    propertySetter.invoke(configInstance, ((Supplier<?>) propertyValue).get());
                }
            } catch (final IllegalAccessException e) {
                throw new CheckedWrapperException(e, "Can't set property: ?", propertyName);
            } catch (final InvocationTargetException e) {
                throw new CheckedWrapperException(e.getTargetException(), "Can't set property: ?", propertyName);
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
