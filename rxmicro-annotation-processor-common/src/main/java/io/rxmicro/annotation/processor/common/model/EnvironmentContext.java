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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.annotation.processor.common.model.virtual.VirtualModuleElement;
import io.rxmicro.common.RxMicroModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Requires.require;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public final class EnvironmentContext {

    private final Map<Class<? extends ModuleGeneratorConfig>, ModuleGeneratorConfig> moduleGeneratorConfigs = new HashMap<>();

    private final ModuleElement currentModule;

    private final Set<RxMicroModule> rxMicroModules;

    private final Set<String> includePackages;

    private final Set<String> excludePackages;

    private final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues;

    public EnvironmentContext(final ModuleElement currentModule,
                              final Set<RxMicroModule> rxMicroModules,
                              final Set<String> includePackages,
                              final Set<String> excludePackages,
                              final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues) {
        this.currentModule = require(currentModule);
        this.rxMicroModules = new TreeSet<>(rxMicroModules);
        this.includePackages = require(includePackages);
        this.excludePackages = require(excludePackages);
        this.defaultConfigValues = require(defaultConfigValues);
    }

    public ModuleElement getCurrentModule() {
        return currentModule;
    }

    public Set<RxMicroModule> getRxMicroModules() {
        return rxMicroModules;
    }

    public boolean isRxMicroModuleEnabled(final RxMicroModule rxMicroModule) {
        return rxMicroModules.contains(rxMicroModule);
    }

    public void put(final ModuleGeneratorConfig moduleGeneratorConfig) {
        moduleGeneratorConfigs.put(moduleGeneratorConfig.getClass(), moduleGeneratorConfig);
    }

    @SuppressWarnings("unchecked")
    public <T extends ModuleGeneratorConfig> T get(final Class<T> moduleGeneratorConfigClass) {
        return (T) Optional.ofNullable(moduleGeneratorConfigs.get(moduleGeneratorConfigClass)).orElseThrow();
    }

    public boolean isRxMicroClassShouldBeProcessed(final TypeElement type) {
        if (!includePackages.isEmpty()) {
            return filterBy(includePackages, excludePackages, type, FilterType.INCLUDE);
        } else {
            return filterBy(excludePackages, Set.of(), type, FilterType.EXCLUDE);
        }
    }

    private boolean filterBy(final Set<String> packages,
                             final Set<String> exceptionPackages,
                             final TypeElement type,
                             final FilterType filterType) {
        if (packages.isEmpty()) {
            return true;
        } else {
            final String fullClassName = type.getQualifiedName().toString();
            for (final String ignoredPackage : packages) {
                if (fullClassName.startsWith(ignoredPackage)) {
                    if (exceptionPackages.isEmpty()) {
                        return filterType.success;
                    } else {
                        return filterBy(exceptionPackages, Set.of(), type, FilterType.EXCLUDE);
                    }
                }
            }
            return filterType.failed;
        }
    }

    public List<Map.Entry<String, DefaultConfigProxyValue>> getDefaultConfigValues() {
        return defaultConfigValues;
    }

    @Override
    public String toString() {
        return "Current environment context is:" + lineSeparator() +
                "  Current module:" + lineSeparator() + "    `" +
                getModuleName() + "`" + lineSeparator() +
                "  Available RxMicro modules: " + lineSeparator() +
                rxMicroModules.stream()
                        .map(s -> "    `" + s + "`;")
                        .collect(joining(lineSeparator())) + lineSeparator() +
                "  Include packages:" + (includePackages.isEmpty() ?
                " <none>" :
                lineSeparator() + includePackages.stream()
                        .map(s -> "    " + s + ".*")
                        .collect(joining(lineSeparator()))) + lineSeparator() +
                "  Exclude packages:" + (excludePackages.isEmpty() ?
                " <none>" :
                lineSeparator() + excludePackages.stream()
                        .map(s -> "    " + s + ".*")
                        .collect(joining(lineSeparator())));
    }

    private CharSequence getModuleName() {
        if (currentModule instanceof VirtualModuleElement || !currentModule.isUnnamed()) {
            return currentModule.getQualifiedName();
        } else {
            return "UNNAMED";
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private enum FilterType {

        INCLUDE(true, false),

        EXCLUDE(false, true);

        private final boolean success;

        private final boolean failed;

        FilterType(final boolean success,
                   final boolean failed) {
            this.success = success;
            this.failed = failed;
        }
    }
}
