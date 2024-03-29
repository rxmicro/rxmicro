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

package io.rxmicro.annotation.processor.integration.test.internal;

import io.rxmicro.model.BaseModel;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class SourceCodeResource extends BaseModel {

    private static final String JAVA_EXTENSION_WITH_DOT = ".java";

    private static final String SOURCE_OUTPUT = "SOURCE_OUTPUT";

    private final String originalClasspathResource;

    private final String generatedClassNameSourceCodeFile;

    private final String fullClassName;

    private final String simpleClassName;

    public SourceCodeResource(final String outputFolder,
                              final String originalClasspathResource) {
        this.originalClasspathResource = require(originalClasspathResource);
        this.fullClassName = buildFullClassName(outputFolder, originalClasspathResource);
        this.generatedClassNameSourceCodeFile = buildFullNameResource(fullClassName);
        this.simpleClassName = buildSimpleClassName(fullClassName);
    }

    private String buildFullClassName(final String outputFolder,
                                      final String resource) {
        final String temp;
        if (resource.endsWith(JAVA_EXTENSION_WITH_DOT)) {
            if (resource.startsWith(outputFolder)) {
                temp = resource.substring(outputFolder.length(), resource.length() - JAVA_EXTENSION_WITH_DOT.length());
            } else {
                temp = resource.substring(0, resource.length() - JAVA_EXTENSION_WITH_DOT.length());
            }
        } else {
            if (resource.startsWith(outputFolder)) {
                temp = resource.substring(outputFolder.length());
            } else {
                temp = resource;
            }
        }
        final String[] parts = temp.split("/");
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String part : parts) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('.');
            }
            if (part.startsWith("unnamed-")) {
                stringBuilder.append("unnamed");
            } else {
                stringBuilder.append(part);
            }
        }
        return stringBuilder.toString();
    }

    private String buildFullNameResource(final String fullClassName) {
        final StringBuilder stringBuilder = new StringBuilder()
                .append('/').append(SOURCE_OUTPUT).append('/');
        for (int i = 0; i < fullClassName.length(); i++) {
            final char ch = fullClassName.charAt(i);
            if (ch == '.') {
                stringBuilder.append('/');
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.append(".java").toString();
    }

    private String buildSimpleClassName(final String fullClassName) {
        final int last = fullClassName.lastIndexOf('.');
        return last == -1 ? fullClassName : fullClassName.substring(last + 1);
    }

    public String getOriginalClasspathResource() {
        return originalClasspathResource;
    }

    public String getGeneratedClassNameSourceCodeFile() {
        return generatedClassNameSourceCodeFile;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    @Override
    public String toString() {
        return generatedClassNameSourceCodeFile;
    }
}
