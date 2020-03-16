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
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.Title;

// tag::content[]
@Title("Metadata Annotations")
@Description("*Project* _Description_")
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
        introduction = @IntroductionDefinition(
                sectionOrder = {
                        IntroductionDefinition.Section.Base_Endpoint,
                        IntroductionDefinition.Section.Licenses
                }
        ),
        resource = @ResourceDefinition(
                withInternalErrorResponse = false
        ),
        withGeneratedDate = false
)
module examples.documentation.asciidoctor.metadata.annotations {
    requires rxmicro.rest.server.netty;
    requires rxmicro.rest.server.exchange.json;
    requires static rxmicro.documentation.asciidoctor;
}
// end::content[]