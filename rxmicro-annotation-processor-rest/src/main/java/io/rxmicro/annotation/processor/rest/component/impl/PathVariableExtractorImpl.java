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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.AbstractExpressionParser;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.PathVariableExtractor;
import io.rxmicro.rest.model.UrlSegments;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PathVariableExtractorImpl extends AbstractExpressionParser
        implements PathVariableExtractor {

    @Override
    public UrlSegments extractPathSegments(final Element owner, final String path) {
        final StringBuilder templateBuilder = new StringBuilder();
        final List<String> variables = new ArrayList<>();
        extractTemplateAndVariables(owner, templateBuilder, variables, path, false);
        final String urlTemplate = templateBuilder.toString();
        validate(owner, urlTemplate, path);
        return new UrlSegments(urlTemplate, List.copyOf(variables));
    }

    private void validate(final Element owner,
                          final String urlTemplate,
                          final String path) {
        if (urlTemplate.replace("??", EMPTY_STRING).length() != urlTemplate.length()) {
            throw new InterruptProcessingException(owner, "Expected delimiter between path variables: ?", path);
        }
    }
}
