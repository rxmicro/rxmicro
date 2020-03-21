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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.CurrentModuleDecorator;
import io.rxmicro.annotation.processor.common.component.EnvironmentContextBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.util.Annotations;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.config.ExcludeAll;
import io.rxmicro.config.IncludeAll;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.QualifiedNameable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class EnvironmentContextBuilderImpl extends AbstractProcessorComponent
        implements EnvironmentContextBuilder {

    @Inject
    private CurrentModuleDecorator currentModuleDecorator;

    @Override
    public EnvironmentContext build(final RoundEnvironment roundEnv,
                                    final ModuleElement realModuleElement) {
        final ModuleElement currentModule = currentModuleDecorator.decorate(realModuleElement);
        final Set<RxMicroModule> rxMicroModules = getRxMicroModules(currentModule);
        final Map<String, Element> includePackages = roundEnv.getElementsAnnotatedWith(IncludeAll.class)
                .stream()
                .map(el -> entry(((QualifiedNameable) el).getQualifiedName().toString(), el))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        final Map<String, Element> excludePackages = roundEnv.getElementsAnnotatedWith(ExcludeAll.class)
                .stream()
                .map(el -> entry(((QualifiedNameable) el).getQualifiedName().toString(), el))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        validate(includePackages, excludePackages);

        final EnvironmentContext environmentContext = new EnvironmentContext(
                currentModule,
                rxMicroModules,
                Set.copyOf(includePackages.keySet()),
                Set.copyOf(excludePackages.keySet()),
                getDefaultConfigValues(currentModule));
        info("?", environmentContext);
        return environmentContext;
    }

    private Set<RxMicroModule> getRxMicroModules(final ModuleElement currentModule) {
        if (currentModule.isUnnamed()) {
            return Arrays.stream(RxMicroModule.values())
                    .filter(m -> elements().getPackageElement(m.getRootPackage()) != null)
                    .collect(toSet());
        } else {
            return elements().getAllModuleElements().stream()
                    .flatMap(me -> RxMicroModule.of(me.getQualifiedName().toString()).stream())
                    .collect(toSet());
        }
    }

    private void validate(final Map<String, Element> includePackages,
                          final Map<String, Element> excludePackages) {
        for (final Map.Entry<String, Element> entry : includePackages.entrySet()) {
            if (excludePackages.containsKey(entry.getKey())) {
                throw new InterruptProcessingException(
                        entry.getValue(),
                        "? '?' both included and excluded",
                        entry.getValue() instanceof PackageElement ? "Package" : "Class or interface",
                        entry.getKey()
                );
            }
        }
    }

    private List<Map.Entry<String, String>> getDefaultConfigValues(final ModuleElement currentModule) {
        final List<Map.Entry<String, String>> defaultConfigValues =
                Annotations.getDefaultConfigValues("", currentModule);
        for (final Map.Entry<String, String> defaultConfigValue : defaultConfigValues) {
            if (defaultConfigValue.getKey().startsWith(".")) {
                throw new InterruptProcessingException(
                        currentModule,
                        "Missing name space for default config name: ?",
                        defaultConfigValue.getKey().substring(1)
                );
            }
        }
        return defaultConfigValues;
    }
}
