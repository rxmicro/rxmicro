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

package io.rxmicro.annotation.processor.cdi.model.qualifier;

import io.rxmicro.annotation.processor.cdi.model.QualifierRule;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class ByTypeQualifierRule implements QualifierRule {

    private final String fullTypeName;

    public ByTypeQualifierRule(final TypeMirror typeMirror) {
        this(getTypes().erasure(typeMirror).toString());
    }

    public ByTypeQualifierRule(final TypeElement typeElement) {
        this(typeElement.asType());
    }

    public ByTypeQualifierRule(final String fullTypeName) {
        this.fullTypeName = require(fullTypeName);
    }

    String getFullTypeName() {
        return fullTypeName;
    }

    @Override
    public String getJavaCodeFragment() {
        return format("new ?<>(?.class)", ByTypeInstanceQualifier.class.getSimpleName(), getSimpleName(fullTypeName));
    }

    @Override
    public Set<String> getImports() {
        return Set.of(
                fullTypeName,
                ByTypeInstanceQualifier.class.getName()
        );
    }
}
