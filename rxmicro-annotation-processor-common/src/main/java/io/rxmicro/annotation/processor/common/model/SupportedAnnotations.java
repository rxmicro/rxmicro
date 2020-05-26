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

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.lang.model.type.DeclaredType;

import static io.rxmicro.annotation.processor.common.util.Annotations.getRequiredAnnotationClassParameter;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static java.util.Collections.unmodifiableSet;

/**
 * @author nedis
 * @since 0.1
 */
public final class SupportedAnnotations {

    private final Set<String> names;

    private final Set<String> namesWithRepeatable;

    public SupportedAnnotations(final Set<Class<? extends Annotation>> supportedAnnotationClasses) {
        final Set<String> supportedAnnotations = new TreeSet<>();
        namesWithRepeatable = new LinkedHashSet<>();
        for (final Class<? extends Annotation> annotationClass : supportedAnnotationClasses) {
            supportedAnnotations.add(annotationClass.getName());
            namesWithRepeatable.add(annotationClass.getName());
            Optional.ofNullable(getElements().getTypeElement(annotationClass.getName()))
                    .flatMap(annotationElement -> Optional.ofNullable(annotationElement.getAnnotation(Repeatable.class)))
                    .ifPresent(repeatable -> namesWithRepeatable.add(
                            getRequiredAnnotationClassParameter(repeatable::value).asType().toString())
                    );
        }
        this.names = unmodifiableSet(supportedAnnotations);
    }

    public Set<String> getNames() {
        return names;
    }

    public boolean isAnnotationSupported(final DeclaredType annotationType) {
        return namesWithRepeatable.contains(annotationType.toString());
    }
}
