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

import io.rxmicro.annotation.processor.documentation.asciidoctor.AsciiDoctorModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.AsciiDoctorIncludeReferenceSyntaxProvider;
import io.rxmicro.annotation.processor.documentation.component.IncludeReferenceSyntaxProvider;
import io.rxmicro.annotation.processor.rest.server.component.AbstractDocumentationModuleClassStructuresBuilder;

/**
 * The {@code RxMicro Annotation Processor} internal module.
 *
 * @author nedis
 * @since 0.1
 */
module rxmicro.annotation.processor.documentation.asciidoctor {
    requires transitive rxmicro.annotation.processor.documentation;
    requires transitive rxmicro.documentation.asciidoctor;
    requires transitive rxmicro.files;

    exports io.rxmicro.annotation.processor.documentation.asciidoctor.model to
            freemarker;

    opens io.rxmicro.annotation.processor.documentation.asciidoctor to
            com.google.guice;
    opens io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl to
            com.google.guice;

    opens ftl.documentation.asciidoctor;
    opens ftl.documentation.asciidoctor.introduction;

    provides AbstractDocumentationModuleClassStructuresBuilder with AsciiDoctorModuleClassStructuresBuilder;
    provides IncludeReferenceSyntaxProvider with AsciiDoctorIncludeReferenceSyntaxProvider;
}
