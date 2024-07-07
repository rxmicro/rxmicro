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

import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.IntroductionDefinition;
import io.rxmicro.documentation.ResourceDefinition;

@DocumentationDefinition(
        introduction = @IntroductionDefinition(sectionOrder = {}),
        resource = @ResourceDefinition(withInternalErrorResponse = false),
        withGeneratedDate = false
)
module examples.documentation.asciidoctor.errors {
    requires rxmicro.rest.server;
    requires rxmicro.rest.server.exchange.json;
    requires rxmicro.rest.client;
    requires rxmicro.rest.client.exchange.json;
    requires rxmicro.validation;

    requires static rxmicro.documentation.asciidoctor;
}