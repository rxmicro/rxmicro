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

import io.rxmicro.documentation.Author;
import io.rxmicro.documentation.BaseEndpoint;
import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.IntroductionDefinition;
import io.rxmicro.documentation.License;
import io.rxmicro.documentation.ResourceGroupDefinition;
import io.rxmicro.documentation.Title;
import io.rxmicro.documentation.asciidoctor.DocumentAttributes;

@DocumentAttributes(
        {
                // https://asciidoctor.org/docs/user-manual/#anchors
                "sectanchors", "",
                // https://asciidoctor.org/docs/user-manual/#links
                "sectlinks", "",
                // https://asciidoctor.org/docs/user-manual/#table-of-contents-summary
                "toc", "right",
                "toc-title", "TOC-TITLE",
                "toclevels", "2"
        }
)
@Title("FULL-PROJECT-DOCUMENTATION-")
@Description("*PROJECT* _DESCRIPTION_")
@Author(
        name = "Richard Hendricks",
        email = "richard.hendricks@piedpiper.com"
)
@Author(
        name = "Bertram Gilfoyle",
        email = "bertram.gilfoyle@piedpiper.com"
)
@Author(
        name = "Dinesh Chugtai",
        email = "dinesh.chugtai@piedpiper.com"
)
@BaseEndpoint("https://api.rxmicro.io")
@License(
        name = "MIT License",
        url = "https://github.com/rxmicro/rxmicro-usage/blob/master/examples/LICENSE"
)
@DocumentationDefinition(
        output = {
                DocumentationDefinition.GenerationOutput.SINGLE_DOCUMENT,
                DocumentationDefinition.GenerationOutput.BASICS_SECTION,
                DocumentationDefinition.GenerationOutput.RESOURCES_SECTION
        },
        introduction = @IntroductionDefinition(
                sectionOrder = {
                        IntroductionDefinition.Section.Common_Concept,
                        IntroductionDefinition.Section.Base_Endpoint,
                        IntroductionDefinition.Section.Http_Verbs,
                        IntroductionDefinition.Section.Error_Model,
                        IntroductionDefinition.Section.Handler_Not_Found,
                        IntroductionDefinition.Section.Licenses,
                        IntroductionDefinition.Section.Specification
                }
        ),
        resourceGroup = @ResourceGroupDefinition(
                sectionOrder = {
                        ResourceGroupDefinition.Section.Versioning,
                        ResourceGroupDefinition.Section.CORS
                }
        ),
        withGeneratedDate = false
)
module examples.documentation.asciidoctor.full {
    requires rxmicro.rest.server.netty;
    requires rxmicro.rest.server.exchange.json;
    requires rxmicro.rest.client.jdk;
    requires rxmicro.rest.client.exchange.json;
    requires rxmicro.validation;
    requires static rxmicro.documentation.asciidoctor;
}