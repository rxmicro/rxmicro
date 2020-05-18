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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ExpressionBuilder;
import io.rxmicro.annotation.processor.common.component.ExpressionParser;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.Expression;
import io.rxmicro.annotation.processor.rest.client.component.ClientCommonOptionBuilder;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.rest.AddHeader;
import io.rxmicro.rest.AddQueryParameter;
import io.rxmicro.rest.SetHeader;
import io.rxmicro.rest.SetQueryParameter;

import java.util.Arrays;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ClientCommonOptionBuilderImpl implements ClientCommonOptionBuilder {

    @Inject
    private ExpressionParser expressionParser;

    @Inject
    private ExpressionBuilder expressionBuilder;

    @Override
    public StaticHeaders getStaticHeaders(final ClassHeader.Builder classHeaderBuilder,
                                          final Element element,
                                          final TypeElement configClass) {
        final StaticHeaders staticHeaders = new StaticHeaders();
        Arrays.stream(element.getAnnotationsByType(AddHeader.class)).forEach(a ->
                staticHeaders.add(a.name(), resolveExpression(classHeaderBuilder, element, configClass, a.value())));
        Arrays.stream(element.getAnnotationsByType(SetHeader.class)).forEach(a ->
                staticHeaders.set(a.name(), resolveExpression(classHeaderBuilder, element, configClass, a.value())));
        return staticHeaders;
    }

    @Override
    public StaticQueryParameters getStaticQueryParameters(final ClassHeader.Builder classHeaderBuilder,
                                                          final Element element,
                                                          final TypeElement configClass) {
        final StaticQueryParameters staticQueryParameters = new StaticQueryParameters();
        Arrays.stream(element.getAnnotationsByType(AddQueryParameter.class)).forEach(a ->
                staticQueryParameters.add(a.name(), resolveExpression(classHeaderBuilder, element, configClass, a.value())));
        Arrays.stream(element.getAnnotationsByType(SetQueryParameter.class)).forEach(a ->
                staticQueryParameters.set(a.name(), resolveExpression(classHeaderBuilder, element, configClass, a.value())));
        return staticQueryParameters;
    }

    private String resolveExpression(final ClassHeader.Builder classHeaderBuilder,
                                     final Element owner,
                                     final TypeElement configClass,
                                     final String value) {
        if (value.contains("${")) {
            final Expression expression = expressionParser.parse(owner, configClass, value);
            return expressionBuilder.build(classHeaderBuilder, expression, configClass, "config");
        } else {
            return format("\"?\"", value);
        }
    }
}
