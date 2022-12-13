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

import io.rxmicro.annotation.processor.common.component.PackagesThatMustBeOpenedToReflectionBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.model.BaseModel;

import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.doesExtendSuperType;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_CDI_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REFLECTION_MODULE;
import static io.rxmicro.common.util.ExCollectors.toTreeSet;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.10
 */
public final class PackagesThatMustBeOpenedToReflectionBuilderImpl implements PackagesThatMustBeOpenedToReflectionBuilder {

    @Override
    public Set<String> getPackages(final EnvironmentContext environmentContext) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        if (currentModule.isUnnamed()) {
            return Set.of();
        } else {
            final Set<String> packages = getPackagesThatMustBeOpened(environmentContext, currentModule);
            if (packages.isEmpty()) {
                return Set.of();
            } else {
                return getPackagesThatNotAlreadyOpenedInModuleInfo(currentModule, packages);
            }
        }
    }

    /**
     * If cdi enabled, then any package can contain pojo class that can be used as bean.
     * For this case the RxMicro framework must create this bean via reflection.
     *
     * @see io.rxmicro.cdi.detail.InternalBeanFactory#tryCreateBeanViaReflection(Set, io.rxmicro.runtime.detail.InstanceQualifier[])
     */
    private Set<String> getPackagesThatMustBeOpened(final EnvironmentContext environmentContext,
                                                    final ModuleElement currentModule) {
        if (environmentContext.getRxMicroModules().contains(RX_MICRO_CDI_MODULE)) {
            return getAllPackagesInCurrentModule(currentModule);
        } else {
            return getPackagesThatContainModelClassesWithDynamicToStringMethod(currentModule);
        }
    }

    private Set<String> getAllPackagesInCurrentModule(final ModuleElement currentModule) {
        return currentModule.getEnclosedElements().stream()
                .flatMap(e -> e.getEnclosedElements().stream())
                .map(e -> ((PackageElement) e.getEnclosingElement()).getQualifiedName().toString())
                .collect(toTreeSet());
    }

    private Set<String> getPackagesThatContainModelClassesWithDynamicToStringMethod(final ModuleElement currentModule) {
        return currentModule.getEnclosedElements().stream()
                .flatMap(e -> e.getEnclosedElements().stream())
                .filter(e -> e.getKind() == ElementKind.CLASS &&
                        doesExtendSuperType((TypeElement) e, BaseModel.class) &&
                        doesToStringMethodNotOverridden((TypeElement) e)
                )
                .map(e -> ((PackageElement) e.getEnclosingElement()).getQualifiedName().toString())
                .collect(toTreeSet());
    }

    private boolean doesToStringMethodNotOverridden(final TypeElement typeElement) {
        TypeElement current = typeElement;
        while (!BaseModel.class.getName().equals(current.getQualifiedName().toString())) {
            if (current.getEnclosedElements().stream()
                    .anyMatch(e -> e.getKind() == ElementKind.METHOD &&
                            "toString".equals(e.getSimpleName().toString()) &&
                            ((ExecutableElement) e).getParameters().isEmpty())) {
                return false;
            }
            current = asTypeElement(current.getSuperclass()).orElseThrow(() -> {
                throw new ImpossibleException("Type element extends the BaseModel class, so super class must be found always!");
            });
        }
        return true;
    }

    private Set<String> getPackagesThatNotAlreadyOpenedInModuleInfo(final ModuleElement currentModule,
                                                                    final Set<String> packages) {
        final List<String> alreadyOpenedPackages = currentModule.getDirectives().stream()
                .filter(d -> d.getKind() == ModuleElement.DirectiveKind.OPENS)
                .map(d -> (ModuleElement.OpensDirective) d)
                .filter(d -> d.getTargetModules().stream()
                        .anyMatch(moduleElement ->
                                RX_MICRO_REFLECTION_MODULE.getName().equals(moduleElement.getQualifiedName().toString())))
                .map(d -> d.getPackage().getQualifiedName().toString())
                .collect(toList());
        return packages.stream()
                .filter(packageName -> alreadyOpenedPackages.stream()
                        .noneMatch(alreadyOpenedPackage -> alreadyOpenedPackage.equals(packageName)))
                .collect(toTreeSet());
    }
}
