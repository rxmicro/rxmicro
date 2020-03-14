/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.data.mongo.component;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.data.mongo.component.impl.MethodParameterReader;
import io.rxmicro.annotation.processor.data.mongo.model.BsonExpression;

import javax.lang.model.element.ExecutableElement;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public interface BsonExpressionBuilder {

    BsonExpression build(ExecutableElement repositoryMethod,
                         ClassHeader.Builder classHeaderBuilder,
                         String expressionTemplate,
                         MethodParameterReader methodParameterReader);
}
