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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.PathVariablesResolver;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.Element;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.documentation.PathVariables.PROJECT_DIR;
import static io.rxmicro.documentation.PathVariables.SUPPORTED_VARIABLES;
import static io.rxmicro.documentation.PathVariables.TEMP_DIR;
import static io.rxmicro.documentation.PathVariables.USER_DIR;
import static io.rxmicro.documentation.PathVariables.USER_HOME;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @link https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
 * @since 0.1
 */
@Singleton
public final class PathVariablesResolverImpl implements PathVariablesResolver {

    @Override
    public String resolvePathVariables(final Element owner,
                                       final String projectDirectory,
                                       final String expression) {
        String result = expression;
        if (result.contains(USER_DIR)) {
            result = result.replace(USER_DIR, USER_DIR_PATH);
        }
        if (result.contains(PROJECT_DIR)) {
            result = result.replace(PROJECT_DIR, require(projectDirectory));
        }
        if (result.contains(TEMP_DIR)) {
            result = result.replace(TEMP_DIR, System.getProperty("java.io.tmpdir", "/tmp"));
        }
        if (result.contains(USER_HOME)) {
            result = result.replace(USER_HOME, System.getProperty("user.home"));
        }
        if (result.contains("$")) {
            throw new InterruptProcessingException(
                    owner,
                    "Expression '?' contains unsupported path variable: ?. " +
                            "Only allowed variables supported: ?",
                    expression, getVariableName(result), SUPPORTED_VARIABLES);
        }
        return result;
    }

    private String getVariableName(final String expression) {
        final int index = expression.indexOf('$');
        for (int i = index; i < expression.length(); i++) {
            final char ch = expression.charAt(i);
            if ("/\\".indexOf(ch) != -1) {
                return expression.substring(index, i);
            }
        }
        return expression.substring(index);
    }
}
