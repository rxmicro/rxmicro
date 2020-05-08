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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.TitleReader;
import io.rxmicro.common.util.Strings;
import io.rxmicro.documentation.Title;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class TitleReaderImpl implements TitleReader {

    @Override
    public Optional<String> getTitle(final Element element) {
        return Optional.ofNullable(element.getAnnotation(Title.class)).map(Title::value);
    }

    @Override
    public String getDefaultTitle(final Element element) {
        if (element instanceof ModuleElement) {
            return getHumanReadableModuleName(((ModuleElement) element).getQualifiedName().toString());
        } else if (element instanceof ExecutableElement) {
            return getHumanReadableClassOrMethod(element.getSimpleName().toString());
        } else if (element instanceof TypeElement) {
            return getHumanReadableClassOrMethod(element.getSimpleName().toString());
        } else {
            return element.asType().toString();
        }
    }

    private String getHumanReadableClassOrMethod(final String moduleName) {
        final List<String> words = splitByCamelCase(moduleName);
        return words.stream()
                .map(Strings::capitalize)
                .collect(joining(" "));
    }

    private String getHumanReadableModuleName(final String moduleName) {
        return Arrays.stream(moduleName.split("\\."))
                .map(Strings::capitalize)
                .collect(joining(" "));
    }
}
