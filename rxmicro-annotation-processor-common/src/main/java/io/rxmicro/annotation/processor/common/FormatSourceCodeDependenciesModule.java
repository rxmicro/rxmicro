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

package io.rxmicro.annotation.processor.common;

import com.google.inject.AbstractModule;
import io.rxmicro.annotation.processor.common.component.PackagesThatMustBeOpenedToReflectionBuilder;
import io.rxmicro.annotation.processor.common.component.SourceCodeFormatter;
import io.rxmicro.annotation.processor.common.component.SourceCodeGenerator;
import io.rxmicro.annotation.processor.common.component.TokenParser;
import io.rxmicro.annotation.processor.common.component.impl.JavaTokenParserRuleProvider;
import io.rxmicro.annotation.processor.common.component.impl.PackagesThatMustBeOpenedToReflectionBuilderImpl;
import io.rxmicro.annotation.processor.common.component.impl.SourceCodeFormatterImpl;
import io.rxmicro.annotation.processor.common.component.impl.SourceCodeGeneratorImpl;
import io.rxmicro.annotation.processor.common.component.impl.TokenParserImpl;
import io.rxmicro.annotation.processor.common.model.JavaTokenParserRule;
import io.rxmicro.annotation.processor.common.model.TokenParserRule;

/**
 * @author nedis
 * @since 0.1
 */
public final class FormatSourceCodeDependenciesModule extends AbstractModule {

    private final JavaTokenParserRuleProvider javaTokenParserRuleProvider = new JavaTokenParserRuleProvider();

    @Override
    protected void configure() {
        bind(TokenParser.class)
                .to(TokenParserImpl.class);
        bind(SourceCodeFormatter.class)
                .to(SourceCodeFormatterImpl.class);
        bind(SourceCodeGenerator.class)
                .to(SourceCodeGeneratorImpl.class);
        bind(PackagesThatMustBeOpenedToReflectionBuilder.class)
                .to(PackagesThatMustBeOpenedToReflectionBuilderImpl.class);

        bind(TokenParserRule.class)
                .annotatedWith(JavaTokenParserRule.class)
                .toProvider(javaTokenParserRuleProvider::get);
    }
}
