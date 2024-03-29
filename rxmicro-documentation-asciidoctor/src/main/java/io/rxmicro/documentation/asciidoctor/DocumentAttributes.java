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

package io.rxmicro.documentation.asciidoctor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Allows specifying AsciiDoc attributes for the generated REST-based microservice documentation.
 *
 * <p>
 * Read more: <a href="https://asciidoctor.org/docs/user-manual/#attributes">https://asciidoctor.org/docs/user-manual/#attributes</a>
 *
 * @author nedis
 * @see io.rxmicro.documentation.Author
 * @see io.rxmicro.documentation.BaseEndpoint
 * @see io.rxmicro.documentation.Description
 * @see io.rxmicro.documentation.DocumentationDefinition
 * @see io.rxmicro.documentation.DocumentationVersion
 * @see io.rxmicro.documentation.Example
 * @see io.rxmicro.documentation.IncludeDescription
 * @see io.rxmicro.documentation.IncludeMode
 * @see io.rxmicro.documentation.License
 * @see io.rxmicro.documentation.ModelExceptionErrorResponse
 * @see io.rxmicro.documentation.ResourceDefinition
 * @see io.rxmicro.documentation.ResourceGroupDefinition
 * @see io.rxmicro.documentation.SimpleErrorResponse
 * @see io.rxmicro.documentation.Title
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({MODULE, ANNOTATION_TYPE})
public @interface DocumentAttributes {

    /**
     * Returns the document attributes, using key-value format.
     *
     * <p>
     * Read more: <a href="https://asciidoctor.org/docs/user-manual/#attributes">https://asciidoctor.org/docs/user-manual/#attributes</a>
     *
     * @return the document attributes
     */
    String[] value() default {
            // https://asciidoctor.org/docs/user-manual/#admonition-icons
            "icons", "font",
            // https://asciidoctor.org/docs/user-manual/#anchors
            "sectanchors", EMPTY_STRING,
            // https://asciidoctor.org/docs/user-manual/#links
            "sectlinks", EMPTY_STRING,
            // https://asciidoctor.org/docs/user-manual/#table-of-contents-summary
            "toc", "left",
            "toclevels", "3",
            // https://asciidoctor.org/docs/user-manual/#numbering
            "sectnums", EMPTY_STRING,
            // https://asciidoctor.org/docs/user-manual/#numbering-depth
            "sectnumlevels", "5"
    };
}
