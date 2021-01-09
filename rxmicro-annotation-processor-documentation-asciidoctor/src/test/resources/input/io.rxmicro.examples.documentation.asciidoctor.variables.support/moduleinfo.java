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
import io.rxmicro.documentation.DocumentationVersion;
import io.rxmicro.documentation.License;
import io.rxmicro.documentation.Title;

@Title(
        value = "${var}",
        variables = {
                "${var}", "Variable Support"
        }
)
@Description(
        value = "${var}",
        variables = {
                "${var}", "*Project* _Description_"
        }
)
@DocumentationVersion(
        value = "${major}.${minor}.${patch}",
        variables = {
                "${major}", "0",
                "${minor}", "0",
                "${patch}", "1"
        }
)
@Author(
        name = "${firstName} ${lastName}",
        email = "${firstName}.${lastName}@piedpiper.com",
        variables = {
                "${firstName}", "Richard",
                "${lastName}", "Hendricks"
        }
)
@BaseEndpoint(
        value = "https://api.rxmicro.io/v${major}.${minor}.${patch}",
        variables = {
                "${major}", "0",
                "${minor}", "0",
                "${patch}", "1"
        }
)
@License(
        name = "Apache License ${version}",
        url = "https://www.apache.org/licenses/LICENSE-${version}",
        variables = {
                "${version}", "2.0"
        }
)
module examples.documentation.asciidoctor.variables.support {
    requires rxmicro.rest.server.netty;
    requires rxmicro.rest.server.exchange.json;
    requires static rxmicro.documentation.asciidoctor;
}
