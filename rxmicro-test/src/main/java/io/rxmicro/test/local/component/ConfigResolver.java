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

package io.rxmicro.test.local.component;

import io.rxmicro.config.Config;
import io.rxmicro.test.SetConfigValue;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Config.getDefaultNameSpace;
import static io.rxmicro.config.detail.DefaultConfigValueBuildHelper.putDefaultConfigValue;
import static io.rxmicro.reflection.Reflections.getFieldValue;
import static io.rxmicro.test.local.util.Inners.getOuterClass;
import static io.rxmicro.test.local.util.Inners.isInnerClass;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
public final class ConfigResolver {

    public Map<String, Config> getStaticConfigMap(final TestModel testModel,
                                                  final Config... defaultConfigs) {
        return getConfigMap(testModel.getStaticConfigs(), singletonList(null), defaultConfigs);
    }

    public Map<String, Config> getInstanceConfigMap(final TestModel testModel,
                                                    final List<Object> testInstances,
                                                    final Config... defaultConfigs) {
        return getConfigMap(testModel.getInstanceConfigs(), testInstances, defaultConfigs);
    }

    private Map<String, Config> getConfigMap(final List<Field> configs,
                                             final List<Object> testInstances,
                                             final Config... defaultConfigs) {
        final Map<String, Config> configMap = configs.stream()
                .map(f -> entry(f, getConfigInstance(testInstances, f)))
                .map(e -> entry(getNameSpace(e), e.getValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (o1, o2) -> o1
                ));
        Arrays.stream(defaultConfigs).forEach(c -> configMap.putIfAbsent(c.getNameSpace(), c));
        return unmodifiableOrderedMap(configMap);
    }

    private Config getConfigInstance(final List<Object> testInstances,
                                     final Field field) {
        final Config config = (Config) getFieldValue(testInstances, field);
        if (config == null) {
            throw new InvalidTestConfigException(
                    "Test config could not be null! " +
                            "Add initialization block for field: ?",
                    field);
        }
        return config;
    }

    private String getNameSpace(final Map.Entry<Field, Config> entry) {
        return Optional.of(entry.getKey().getAnnotation(WithConfig.class).value())
                .filter(v -> !v.isEmpty())
                .orElseGet(() -> entry.getValue().getNameSpace());
    }

    public void setDefaultConfigValues(final Class<?> testInstanceClass) {
        final List<SetConfigValue> setConfigValues = new ArrayList<>();
        if (isInnerClass(testInstanceClass)) {
            setConfigValues.addAll(asList(getOuterClass(testInstanceClass).getAnnotationsByType(SetConfigValue.class)));
        }
        setConfigValues.addAll(asList(testInstanceClass.getAnnotationsByType(SetConfigValue.class)));
        for (final SetConfigValue configValue : setConfigValues) {
            final String name = getConfigName(configValue);
            putDefaultConfigValue(name, configValue.value());
        }
    }

    private String getConfigName(final SetConfigValue configValue) {
        if (configValue.configClass() == Config.class) {
            final String name = configValue.name();
            if (name.indexOf('.') == -1) {
                throw new InvalidTestConfigException(
                        "Missing namespace for config property: '?'. " +
                                "Add missing namespace or set configClass!",
                        name
                );
            } else {
                return name;
            }
        } else {
            final String name = configValue.name();
            if (name.indexOf('.') == -1) {
                return format("?.?", getDefaultNameSpace(configValue.configClass()), name);
            } else {
                throw new InvalidTestConfigException(
                        "Redundant namespace for config property: '?'. Add redundant namespace or remove configClass!",
                        name
                );
            }
        }
    }
}
