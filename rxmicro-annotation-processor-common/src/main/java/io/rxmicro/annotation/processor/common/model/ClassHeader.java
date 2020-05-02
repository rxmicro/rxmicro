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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.common.util.Names;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.getTypes;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.lang.System.lineSeparator;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ClassHeader {

    private final String packageName;

    private final Map<String, String> importsMap;

    private final Map<String, String> staticImportsMap;

    public static Builder newClassHeaderBuilder(final String packageName) {
        return new Builder(packageName);
    }

    public static Builder newClassHeaderBuilder(final TypeElement typeElement) {
        return newClassHeaderBuilder(Names.getPackageName(typeElement));
    }

    public static Builder newClassHeaderBuilder(final ObjectModelClass<?> objectModelClass) {
        return newClassHeaderBuilder(objectModelClass.getModelTypeElement());
    }

    private ClassHeader(final String packageName,
                        final Set<String> imports,
                        final Set<String> staticImports) {
        this.packageName = packageName;
        this.importsMap = imports.stream()
                .map(cl -> entry(getSimpleName(cl), cl))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                    throw new IllegalStateException(format(
                            "Detected different classes with the same simple name: '?' and '?'", v1, v2
                    ));
                }));
        this.staticImportsMap = staticImports.stream()
                .map(cl -> entry(getSimpleName(cl), cl))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                    throw new IllegalStateException(format(
                            "Detected different static methods with the same simple name: '?' and '?'", v1, v2
                    ));
                }));
    }

    public Map<String, String> getEditableImports() {
        return importsMap;
    }

    public Map<String, String> getEditableStaticImports() {
        return staticImportsMap;
    }

    public String buildHeader(final boolean withAuthorComment) {
        final Set<String> javaImports = new TreeSet<>();
        final Set<String> libImports = new TreeSet<>();
        final Set<String> staticImports = new TreeSet<>(staticImportsMap.values());
        for (final String className : importsMap.values()) {
            final String packageName = Names.getPackageName(className);
            if (!this.packageName.equals(packageName) && !"java.lang".equals(packageName)) {
                if (packageName.startsWith("java.")) {
                    javaImports.add(className);
                } else {
                    libImports.add(className);
                }
            }
        }
        return buildHeader(withAuthorComment, javaImports, libImports, staticImports);
    }

    private String buildHeader(final boolean withAuthorComment,
                               final Set<String> javaImports,
                               final Set<String> libImports,
                               final Set<String> staticImports) {
        final StringBuilder headerBuilder = new StringBuilder(50);
        // Add package
        headerBuilder.append("package ").append(packageName).append(';').append(lineSeparator());
        headerBuilder.append(lineSeparator());
        // Add imports
        for (final Set<String> imports : Arrays.asList(libImports, javaImports, staticImports)) {
            final String startStatement = (imports == staticImports ? "import static " : "import ");
            for (final String imp : imports) {
                headerBuilder.append(startStatement).append(imp).append(';').append(lineSeparator());
            }
            if (!imports.isEmpty()) {
                headerBuilder.append(lineSeparator());
            }
        }
        if (withAuthorComment) {
            headerBuilder.append(getAuthorComment());
        }
        return headerBuilder.toString();
    }

    public String getAuthorComment() {
        return "" +
                "/**" + lineSeparator() +
                " * Generated by rxmicro annotation processor" + lineSeparator() +
                " *" + lineSeparator() +
                " * @link https://rxmicro.io" + lineSeparator() +
                " */";
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private static final TypeMirror[] EMPTY_TYPE_MIRROR_ARRAY = new TypeMirror[0];

        private final String packageName;

        private final Set<String> imports = new HashSet<>();

        private final Set<String> staticImports = new HashSet<>();

        private Builder(final String packageName) {
            this.packageName = require(packageName);
        }

        public Builder addImports(final TypeElement... types) {
            Arrays.stream(types).forEach(t -> addImport(t.getQualifiedName().toString()));
            return this;
        }

        public Builder addImports(final TypeMirror... types) {
            Arrays.stream(types)
                    .flatMap(this::expand)
                    .forEach(t -> asTypeElement(t)
                            .ifPresent(te -> addImport(t.toString())));
            return this;
        }

        private Stream<TypeMirror> expand(final TypeMirror type) {
            final List<TypeMirror> result = new ArrayList<>(2);
            populate(result, type);
            return result.stream();
        }

        private void populate(final List<TypeMirror> result,
                              final TypeMirror type) {
            if (type instanceof DeclaredType) {
                for (final TypeMirror typeArgument : ((DeclaredType) type).getTypeArguments()) {
                    populate(result, typeArgument);
                }
            }
            result.add(getTypes().erasure(type));
        }

        public Builder addImports(final Class<?>... classes) {
            Arrays.stream(classes).forEach(cl -> addImport(cl.getName()));
            return this;
        }

        public Builder addImports(final String... fullClassNames) {
            for (final String fullClassName : fullClassNames) {
                addImport(fullClassName);
            }
            return this;
        }

        public Builder addImports(final Collection<TypeMirror> fullClassNames) {
            addImports(fullClassNames.toArray(EMPTY_TYPE_MIRROR_ARRAY));
            return this;
        }

        private void addImport(final String fullClassName) {
            imports.add(fullClassName);
        }

        public Builder addStaticImport(final Class<?> className,
                                       final String methodName) {
            addStaticImport(className.getName(), methodName);
            return this;
        }

        public Builder addStaticImport(final String className,
                                       final String methodName) {
            staticImports.add(format("?.?", className, methodName));
            return this;
        }

        public ClassHeader build() {
            return new ClassHeader(packageName, imports, staticImports);
        }
    }
}
