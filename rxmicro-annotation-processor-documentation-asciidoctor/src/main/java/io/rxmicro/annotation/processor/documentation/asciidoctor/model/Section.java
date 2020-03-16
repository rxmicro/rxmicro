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

package io.rxmicro.annotation.processor.documentation.asciidoctor.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Section {

    private final SectionType sectionType;

    private final String data;

    Section(final SectionType sectionType,
            final String data) {
        this.sectionType = sectionType;
        this.data = data;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public boolean isIncludeTemplate() {
        return sectionType == SectionType.INCLUDE_TEMPLATE;
    }

    @UsedByFreemarker({
            "asciidoctor-document-template.adocftl",
            "micro-service-basics-document-template.adocftl"
    })
    public boolean isInvokeMacros() {
        return sectionType == SectionType.INVOKE_MACROS;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public boolean isCustomText() {
        return sectionType == SectionType.CUSTOM_TEXT;
    }

    @UsedByFreemarker({
            "asciidoctor-document-template.adocftl",
            "micro-service-basics-document-template.adocftl"
    })
    public String getData() {
        return data;
    }
}
