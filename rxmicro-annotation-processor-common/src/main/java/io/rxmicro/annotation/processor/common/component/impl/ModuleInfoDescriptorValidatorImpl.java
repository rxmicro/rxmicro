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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ModuleInfoDescriptorValidator;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.config.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Elements.findSuperType;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE_DEFAULT_VALUE;
import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.CommonConstants.VirtualModuleInfoConstants.RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_DOCUMENTATION_ASCIIDOCTOR_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REFLECTION_MODULE;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class ModuleInfoDescriptorValidatorImpl extends BaseProcessorComponent implements ModuleInfoDescriptorValidator {

    @Override
    public void validate(final ModuleElement moduleElement) {
        if (getBooleanOption(RX_MICRO_STRICT_MODE, RX_MICRO_STRICT_MODE_DEFAULT_VALUE)) {
            validateThatDocumentationModuleIsStatic(moduleElement);
        }
        if (moduleElement.isUnnamed()) {
            validateDefaultPackage(getElements().getPackageElement(EMPTY_STRING), true);
        } else {
            validateDefaultPackage(getElements().getPackageElement(moduleElement, EMPTY_STRING), false);
            validateThatCustomConfigPackageIsExportedToReflectionModule(moduleElement);
        }
    }

    private void validateThatDocumentationModuleIsStatic(final ModuleElement moduleElement) {
        for (final ModuleElement.Directive directive : moduleElement.getDirectives()) {
            if (directive.getKind() == ModuleElement.DirectiveKind.REQUIRES) {
                final ModuleElement.RequiresDirective requiresDirective = (ModuleElement.RequiresDirective) directive;
                if (RX_MICRO_DOCUMENTATION_ASCIIDOCTOR_MODULE.getName()
                        .equals(requiresDirective.getDependency().getQualifiedName().toString()) &&
                        !requiresDirective.isStatic()) {
                    throw new InterruptProcessingException(
                            "https://docs.rxmicro.io/latest/user-guide/project-documentation.html#min_settings",
                            moduleElement,
                            "'?' module is required for compile time only! " +
                                    "Add 'static' modifier and remove dependency from classpath!",
                            RX_MICRO_DOCUMENTATION_ASCIIDOCTOR_MODULE.getName()
                    );
                }
            }
        }
    }

    private void validateThatCustomConfigPackageIsExportedToReflectionModule(final ModuleElement moduleElement) {
        final Set<PackageElement> packageElements = moduleElement.getEnclosedElements().stream()
                .map(e -> (PackageElement) e)
                .filter(this::hasCustomConfigClass)
                .collect(Collectors.toSet());
        if (!packageElements.isEmpty()) {
            final Map<String, List<String>> missingExports = getMissingExports(moduleElement, packageElements);
            if (!missingExports.isEmpty()) {
                throw new InterruptProcessingException(
                        "https://docs.rxmicro.io/latest/user-guide/core.html#core-config-user-defined-config",
                        moduleElement,
                        "For custom config classes it is necessary to add 'exports' directive to the 'module-info.java'. " +
                                "The following export directive(s) is(are) missing: {?}.",
                        missingExports.entrySet().stream()
                                .map(e -> format("exports ? to ?;", e.getKey(), String.join(",", e.getValue())))
                                .collect(joining(" "))
                );
            }
        }
    }

    private Map<String, List<String>> getMissingExports(final ModuleElement moduleElement,
                                                        final Set<PackageElement> packageElements) {
        final Map<String, List<String>> missingExports = new LinkedHashMap<>();
        final List<String> requiredExportTargets = List.of(RX_MICRO_REFLECTION_MODULE.getName());
        for (final ModuleElement.Directive directive : moduleElement.getDirectives()) {
            if (directive.getKind() == ModuleElement.DirectiveKind.EXPORTS) {
                final ModuleElement.ExportsDirective exportsDirective = (ModuleElement.ExportsDirective) directive;
                final Iterator<PackageElement> iterator = packageElements.iterator();
                while (iterator.hasNext()) {
                    final PackageElement packageElement = iterator.next();
                    final String packageName = packageElement.getQualifiedName().toString();
                    if (exportsDirective.getPackage().getQualifiedName().toString().equals(packageName)) {
                        final List<? extends ModuleElement> targetModules = exportsDirective.getTargetModules();
                        if (targetModules != null) {
                            final List<String> exportTargets = new ArrayList<>(requiredExportTargets);
                            for (final ModuleElement targetModule : targetModules) {
                                final String targetModuleName = targetModule.getQualifiedName().toString();
                                exportTargets.remove(targetModuleName);
                            }
                            if (!exportTargets.isEmpty()) {
                                missingExports.put(packageName, exportTargets);
                            }
                        }
                        iterator.remove();
                    }
                }
                if (packageElements.isEmpty()) {
                    return missingExports;
                }
            }
        }
        for (final PackageElement packageElement : packageElements) {
            missingExports.put(packageElement.getQualifiedName().toString(), requiredExportTargets);
        }
        return missingExports;
    }

    private boolean hasCustomConfigClass(final PackageElement packageElement) {
        for (final Element element : packageElement.getEnclosedElements()) {
            if (element instanceof TypeElement &&
                    findSuperType((TypeElement) element, Config.class).isPresent()) {
                return true;
            }
        }
        return false;
    }

    private void validateDefaultPackage(final PackageElement packageElement,
                                        final boolean isUnNamedModule) {
        if (packageElement == null) {
            return;
        }
        for (final Element element : packageElement.getEnclosedElements()) {
            if (element instanceof TypeElement) {
                if (isUnNamedModule) {
                    if (!RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME.equals(element.getSimpleName().toString())) {
                        error(
                                "https://docs.rxmicro.io/latest/user-guide/java-integration.html#java-integration-unnamed-module",
                                element,
                                "Each class (interface, enum or annotation) must be declared at a package! " +
                                        "Unnamed (default) package is not supported! " +
                                        "Move this element to the named package! " +
                                        "FYI: If you want to add the virtual module " +
                                        "you should use the predefined by RxMicro framework name: '?' instead of '?'!",
                                RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME,
                                element.getSimpleName()
                        );
                    }
                } else {
                    if (RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME.equals(element.getSimpleName().toString())) {
                        error(element,
                                "? is used as alternative of `module-info.java` only! " +
                                        "To configure project use `module-info.java` instead of `?.java`",
                                RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME, RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME
                        );
                    } else {
                        error(element,
                                "Each class (interface, enum or annotation) must be declared at a package! " +
                                        "Unnamed (default) package is not supported! " +
                                        "Move this element to the named package!"
                        );
                    }
                }
            }
        }
    }
}
