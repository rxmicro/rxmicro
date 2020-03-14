/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.cdi.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.BeanRegistrationQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.DefaultNameBuilder;
import io.rxmicro.annotation.processor.cdi.component.UserDefinedNameBuilder;
import io.rxmicro.annotation.processor.cdi.model.QualifierRule;
import io.rxmicro.annotation.processor.cdi.model.qualifier.ByTypeAndNameQualifierRule;
import io.rxmicro.annotation.processor.cdi.model.qualifier.ByTypeQualifierRule;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.Elements.allSuperTypesAndInterfaces;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public class BeanRegistrationQualifierRuleBuilderImpl implements BeanRegistrationQualifierRuleBuilder {

    @Inject
    private UserDefinedNameBuilder userDefinedNameBuilder;

    @Inject
    private DefaultNameBuilder defaultNameBuilder;

    @Override
    public List<QualifierRule> build(final TypeElement typeElement) {
        final List<QualifierRule> result = new ArrayList<>(3);
        final String modelName = userDefinedNameBuilder.getName(typeElement)
                .orElseGet(() -> defaultNameBuilder.getName(typeElement));

        result.add(new ByTypeQualifierRule(typeElement));
        allSuperTypesAndInterfaces(typeElement, false, true).forEach(te ->
                result.addAll(Arrays.asList(
                        new ByTypeAndNameQualifierRule(te, modelName),
                        new ByTypeQualifierRule(te)
                ))
        );
        return result;
    }

    @Override
    @SuppressWarnings("OptionalIsPresent")
    public List<QualifierRule> build(final ExecutableElement executableElement) {
        Optional<String> optionalName = userDefinedNameBuilder.getName(executableElement);
        if (optionalName.isPresent()) {
            return List.of(new ByTypeAndNameQualifierRule(executableElement.getReturnType(), optionalName.get()));
        } else {
            optionalName = userDefinedNameBuilder.getName(executableElement.getEnclosingElement());
            if (optionalName.isPresent()) {
                return List.of(new ByTypeAndNameQualifierRule(executableElement.getReturnType(), optionalName.get()));
            } else {
                return asTypeElement(executableElement.getReturnType()).stream()
                        .flatMap(te -> allSuperTypesAndInterfaces(te, true, true).stream()
                                .flatMap(t -> Stream.of(
                                        new ByTypeQualifierRule(t),
                                        new ByTypeAndNameQualifierRule(executableElement.getReturnType(), defaultNameBuilder.getName(te)))
                                ))
                        .collect(toList());
            }
        }
    }
}
