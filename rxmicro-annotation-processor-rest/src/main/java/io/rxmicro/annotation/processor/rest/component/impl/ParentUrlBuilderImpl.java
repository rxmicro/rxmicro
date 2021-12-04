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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.BaseUrlBuilder;
import io.rxmicro.annotation.processor.rest.component.ParentUrlBuilder;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.rest.BaseUrlPath;
import io.rxmicro.rest.Version;

import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ParentUrlBuilderImpl extends BaseUrlBuilder implements ParentUrlBuilder {

    @Override
    public ParentUrl build(final TypeElement ownerClass) {
        final ParentUrl.Builder builder = new ParentUrl.Builder();
        final Version version = ownerClass.getAnnotation(Version.class);
        if (version != null) {
            validate(ownerClass, version.value(), "Version value is invalid: ");
            builder.setVersion(version);
        }
        final BaseUrlPath[] baseUrlPaths = ownerClass.getAnnotationsByType(BaseUrlPath.class);
        for (final BaseUrlPath baseUrlPath : baseUrlPaths) {
            final String urlPath = baseUrlPath.value();
            validate(ownerClass, urlPath, "Base url path is invalid: ");
            if (baseUrlPath.position() == BaseUrlPath.Position.AFTER_VERSION) {
                builder.addAfterVersionUrlPath(getNormalizeUrlPath(ownerClass, urlPath));
            } else {
                validateBeforePosition(ownerClass, version);
                builder.addBeforeVersionUrlPath(getNormalizeUrlPath(ownerClass, urlPath));
            }
        }
        return builder.build();
    }

    private String getNormalizeUrlPath(final TypeElement ownerClass,
                                       final String urlPath) {
        final String normalizeUrlPath = normalizeUrlPath(urlPath);
        if (!normalizeUrlPath.equals(urlPath) && isStrictModeEnabled()) {
            throw new InterruptProcessingException(
                    ownerClass,
                    "Invalid base url path: Expected '?', but actual is '?'!",
                    normalizeUrlPath, urlPath
            );
        }
        return normalizeUrlPath;
    }

    private void validateBeforePosition(final TypeElement ownerClass,
                                        final Version version) {
        if (version == null || version.strategy() == Version.Strategy.HEADER) {
            throw new InterruptProcessingException(
                    ownerClass,
                    "'@?' annotation contains redundant parameter: 'position'. " +
                            "Remove redundant parameter!",
                    BaseUrlPath.class.getSimpleName());
        }
    }

    private void validate(final TypeElement ownerClass,
                          final String path,
                          final String validPrefix) {
        validateNotNull(ownerClass, path, validPrefix);
        validateNotEmpty(ownerClass, path, validPrefix);
        validateNotRoot(ownerClass, path, validPrefix);
        validateThatPathIsTrimmedValue(ownerClass, path, validPrefix);
    }
}
