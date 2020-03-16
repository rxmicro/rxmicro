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

package io.rxmicro.annotation.processor.common;

import com.google.inject.AbstractModule;
import io.rxmicro.annotation.processor.common.component.SourceCodeFormatter;
import io.rxmicro.annotation.processor.common.component.SourceCodeGenerator;
import io.rxmicro.annotation.processor.common.component.TokenParser;
import io.rxmicro.annotation.processor.common.component.impl.SourceCodeFormatterImpl;
import io.rxmicro.annotation.processor.common.component.impl.SourceCodeGeneratorImpl;
import io.rxmicro.annotation.processor.common.component.impl.TokenParserImpl;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @link https://github.com/google/guice/wiki/GettingStarted
 * @since 0.1
 */
public final class FormatSourceCodeDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TokenParser.class)
                .to(TokenParserImpl.class);
        bind(SourceCodeFormatter.class)
                .to(SourceCodeFormatterImpl.class);
        bind(SourceCodeGenerator.class)
                .to(SourceCodeGeneratorImpl.class);
    }
}
