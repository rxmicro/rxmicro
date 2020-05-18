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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.PathVariablesResolver;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.component.CustomSectionsReader;
import io.rxmicro.annotation.processor.documentation.component.ExternalResourceReader;
import io.rxmicro.annotation.processor.documentation.component.IncludeReferenceSyntaxProvider;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.documentation.IncludeMode;
import io.rxmicro.documentation.IntroductionDefinition;
import io.rxmicro.documentation.ResourceGroupDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class CustomSectionsReaderImpl implements CustomSectionsReader {

    @Inject
    private PathVariablesResolver pathVariablesResolver;

    @Inject
    private ExternalResourceReader externalResourceReader;

    @Override
    public List<String> read(final Element owner,
                             final IntroductionDefinition introductionDefinition,
                             final ProjectMetaData projectMetaData,
                             final IncludeReferenceSyntaxProvider includeReferenceSyntaxProvider) {
        validateParameters(
                owner,
                introductionDefinition.customSection().length,
                (int) Arrays.stream(introductionDefinition.sectionOrder())
                        .filter(s -> s == IntroductionDefinition.Section.CUSTOM_SECTION)
                        .count()
        );
        return read(
                owner,
                introductionDefinition.customSection(),
                introductionDefinition.includeMode(),
                projectMetaData,
                includeReferenceSyntaxProvider
        );
    }

    @Override
    public List<String> read(final Element owner,
                             final ResourceGroupDefinition resourceGroupDefinition,
                             final ProjectMetaData projectMetaData,
                             final IncludeReferenceSyntaxProvider includeReferenceSyntaxProvider) {
        validateParameters(
                owner,
                resourceGroupDefinition.customSection().length,
                (int) Arrays.stream(resourceGroupDefinition.sectionOrder())
                        .filter(s -> s == ResourceGroupDefinition.Section.CUSTOM_SECTION)
                        .count()
        );
        return read(
                owner,
                resourceGroupDefinition.customSection(),
                resourceGroupDefinition.includeMode(),
                projectMetaData,
                includeReferenceSyntaxProvider
        );
    }

    private List<String> read(final Element owner,
                              final String[] customSection,
                              final IncludeMode includeMode,
                              final ProjectMetaData projectMetaData,
                              final IncludeReferenceSyntaxProvider includeReferenceSyntaxProvider) {
        if (customSection.length == 0) {
            return List.of();
        } else if (includeMode == IncludeMode.INCLUDE_REFERENCE) {
            return Arrays.stream(customSection)
                    .map(v -> includeReferenceSyntaxProvider.include(
                            pathVariablesResolver.resolvePathVariables(owner, projectMetaData.getProjectDirectory(), v))
                    )
                    .collect(Collectors.toList());
        } else if (includeMode == IncludeMode.INLINE_CONTENT) {
            return Arrays.stream(customSection)
                    .map(v -> externalResourceReader.read(owner,
                            pathVariablesResolver.resolvePathVariables(owner, projectMetaData.getProjectDirectory(), v))
                    )
                    .collect(Collectors.toList());
        } else {
            throw new InternalErrorException("Include mode not supported: ?", includeMode);
        }
    }

    private void validateParameters(final Element owner,
                                    final int customSectionLength,
                                    final int customSectionsCount) {
        if (customSectionLength < customSectionsCount) {
            throw new InterruptProcessingException(owner, "Missing custom section resource");
        }
        if (customSectionLength > customSectionsCount) {
            throw new InterruptProcessingException(owner, "Redundant custom section resource");
        }
    }
}
