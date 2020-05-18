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

package io.rxmicro.annotation.processor.rest.client.component;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructureStorage;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethod;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;

import javax.lang.model.element.TypeElement;

/**
 * @author nedis
 * @since 0.1
 */
public interface RestClientMethodBuilder {

    RestClientMethod build(EnvironmentContext environmentContext,
                           RestClientClassStructureStorage restClientClassStructureStorage,
                           ClassHeader.Builder classHeaderBuilder,
                           StaticHeaders staticHeaders,
                           StaticQueryParameters staticQueryParameters,
                           RestClientMethodSignature methodSignature,
                           TypeElement configClass);
}
