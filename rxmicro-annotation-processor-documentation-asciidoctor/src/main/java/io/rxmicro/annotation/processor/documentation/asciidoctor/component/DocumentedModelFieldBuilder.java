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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component;

import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.model.HttpModelType;

import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.Annotations.getReadMore;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader.OPTIONAL_RESTRICTION;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader.REQUIRED_RESTRICTION;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.json.JsonTypes.STRING;

/**
 * @author nedis
 * @since 0.1
 */
public interface DocumentedModelFieldBuilder {

    static DocumentedModelField buildApiVersionHeaderDocumentedModelField(final boolean required) {
        return new DocumentedModelField(
                REQUEST_ID,
                STRING,
                List.of(required ? REQUIRED_RESTRICTION : OPTIONAL_RESTRICTION, "unique: true"),
                "An unique request string identifier.",
                List.of(new ReadMoreModel(getReadMore(RequestId.class).orElseThrow())));
    }

    List<Map.Entry<String, List<DocumentedModelField>>> buildComplex(EnvironmentContext environmentContext,
                                                                     boolean withStandardDescriptions,
                                                                     String projectDirectory,
                                                                     RestObjectModelClass restObjectModelClass,
                                                                     HttpModelType httpModelType,
                                                                     boolean withReadMore);

    default List<DocumentedModelField> buildSimple(final EnvironmentContext environmentContext,
                                                   final boolean withStandardDescriptions,
                                                   final String projectDirectory,
                                                   final RestObjectModelClass restObjectModelClass,
                                                   final HttpModelType httpModelType,
                                                   final boolean withReadMore) {
        final List<Map.Entry<String, List<DocumentedModelField>>> list = buildComplex(
                environmentContext,
                withStandardDescriptions,
                projectDirectory,
                restObjectModelClass,
                httpModelType,
                withReadMore
        );
        if (list.size() == 1) {
            return list.get(0).getValue();
        } else {
            throw new InternalErrorException("Invalid DocumentedModelField map size: ?", list.size());
        }
    }
}
