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

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.SourceCodeGenerator;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.EnvironmentCustomizerClassStructure;
import io.rxmicro.annotation.processor.common.model.ReflectionsClassStructure;
import io.rxmicro.annotation.processor.common.model.SourceCode;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.LoggerMessages.DEFAULT_OFFSET;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractModuleClassStructuresBuilder extends BaseProcessorComponent {

    @Inject
    private SourceCodeGenerator sourceCodeGenerator;

    public abstract String getBuilderName();

    public abstract Set<String> getSupportedAnnotationTypes();

    protected abstract Set<? extends ClassStructure> buildClassStructures(EnvironmentContext environmentContext,
                                                                          Set<? extends TypeElement> annotations,
                                                                          RoundEnvironment roundEnv);

    protected boolean isEnvironmentCustomizerMustBeGenerated() {
        return true;
    }

    public final List<SourceCode> buildSourceCode(final EnvironmentContext environmentContext,
                                                  final Set<? extends TypeElement> annotations,
                                                  final RoundEnvironment roundEnv) {
        final Set<ClassStructure> classStructures = new HashSet<>(buildClassStructures(environmentContext, annotations, roundEnv));
        getReflectionStructure(classStructures).ifPresent(classStructures::add);
        if (isEnvironmentCustomizerMustBeGenerated()) {
            classStructures.add(getEnvironmentCustomizerClassStructure(environmentContext, classStructures));
        }
        validateClassStructureDuplicates(environmentContext, classStructures);
        final boolean isLibraryModule = isLibraryModule();
        return classStructures.stream()
                .filter(cl -> cl.shouldSourceCodeBeGenerated(environmentContext, isLibraryModule))
                .map(cl -> sourceCodeGenerator.generate(cl)).collect(toList());
    }

    private void validateClassStructureDuplicates(final EnvironmentContext environmentContext,
                                                  final Set<ClassStructure> classStructures) {
        final Map<String, List<ClassStructure>> map = new HashMap<>();
        for (final ClassStructure classStructure : classStructures) {
            final List<ClassStructure> list =
                    map.computeIfAbsent(classStructure.getTargetFullClassName(), c -> new ArrayList<>());
            if (list.isEmpty()) {
                list.add(classStructure);
            } else {
                throw new InterruptProcessingException(
                        environmentContext.getCurrentModule(),
                        "Found two different class structures with the same full class name: " +
                                "className='?', structure1='?', structure2='?'! " +
                                "Rename your classes, which are used for generation these structures!",
                        classStructure.getTargetFullClassName(),
                        classStructure.getClass().getName(),
                        list.get(0).getClass().getName()
                );
            }
        }
    }

    public boolean isUnnamedModuleDisabled() {
        return !isUnnamedModule();
    }

    public void afterAllClassStructuresBuilt(final Set<? extends ClassStructure> classStructures) {
        // do nothing
    }

    private Optional<ReflectionsClassStructure> getReflectionStructure(final Set<? extends ClassStructure> structures) {
        boolean getterRequired = false;
        boolean setterRequired = false;
        boolean invokeRequired = false;
        for (final ClassStructure classStructure : structures) {
            if (classStructure.isRequiredReflectionGetter()) {
                getterRequired = true;
            }
            if (classStructure.isRequiredReflectionSetter()) {
                setterRequired = true;
            }
            if (classStructure.isRequiredReflectionInvoke()) {
                invokeRequired = true;
            }
            if (getterRequired && setterRequired && invokeRequired) {
                break;
            }
        }
        if (getterRequired || setterRequired || invokeRequired) {
            return Optional.of(new ReflectionsClassStructure(getterRequired, setterRequired, invokeRequired));
        } else {
            return Optional.empty();
        }
    }

    private EnvironmentCustomizerClassStructure getEnvironmentCustomizerClassStructure(final EnvironmentContext environmentContext,
                                                                                       final Set<? extends ClassStructure> structures) {
        final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues =
                Stream.concat(
                        environmentContext.getDefaultConfigValues().stream(),
                        structures.stream().flatMap(classStructure -> classStructure.getDefaultConfigProxyValues().stream())
                ).collect(toList());
        return new EnvironmentCustomizerClassStructure(
                environmentContext.getCurrentModule(),
                defaultConfigValues,
                environmentContext.getPackagesThatMustBeOpenedToRxMicroCommonModule()
        );
    }

    protected final boolean isAnnotationPerPackageHierarchyAbsent(final TypeElement modelTypeElement,
                                                                  final Class<? extends Annotation> annotationClass) {
        if (modelTypeElement.getAnnotation(annotationClass) != null) {
            return false;
        }
        final PackageElement packageElement = (PackageElement) modelTypeElement.getEnclosingElement();
        if (packageElement.getAnnotation(annotationClass) != null) {
            return false;
        }

        final Element moduleElement = packageElement.getEnclosingElement();
        if (moduleElement != null && moduleElement.getAnnotation(annotationClass) != null) {
            return false;
        }
        String packageName = packageElement.getQualifiedName().toString();
        while (true) {
            packageName = getParentPackageOrNull(packageName);
            if (packageName == null) {
                return true;
            } else {
                final PackageElement packageElement1 = getElements().getPackageElement(packageName);
                if (packageElement1 == null) {
                    return true;
                } else if (packageElement1.getAnnotation(annotationClass) != null) {
                    return false;
                }
            }
        }
    }

    private String getParentPackageOrNull(final String packageName) {
        final int index = packageName.lastIndexOf('.');
        if (index == -1) {
            return null;
        } else {
            return packageName.substring(0, index);
        }
    }

    protected void logClassStructureStorageItem(final String name,
                                                final Set<? extends ClassStructure> classStructures) {
        if (!classStructures.isEmpty() && isDebugEnabled()) {
            debug(
                    "The following ? will be generated:\n?",
                    name,
                    classStructures.stream()
                            .map(classStructure -> DEFAULT_OFFSET + classStructure.getLoggableFullClassName())
                            .collect(joining("\n"))
            );
        }
    }
}
