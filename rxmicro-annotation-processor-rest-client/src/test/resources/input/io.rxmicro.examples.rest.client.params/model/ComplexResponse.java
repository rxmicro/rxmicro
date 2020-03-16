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

package io.rxmicro.examples.rest.client.params.model;

import io.rxmicro.examples.rest.client.params.model.nested.NestedModel;
import io.rxmicro.rest.ParameterMappingStrategy;

import java.util.List;

// tag::content[]
@ParameterMappingStrategy
public final class ComplexResponse {

    Integer integerParameter; // <1>

    Status enumParameter; // <1>

    List<Status> enumsParameter; // <2>

    NestedModel nestedModelParameter; // <3>

    List<NestedModel> nestedModelsParameter; // <4>

    public Integer getIntegerParameter() {
        return integerParameter;
    }

    public Status getEnumParameter() {
        return enumParameter;
    }

    public List<Status> getEnumsParameter() {
        return enumsParameter;
    }

    public NestedModel getNestedModelParameter() {
        return nestedModelParameter;
    }

    public List<NestedModel> getNestedModelsParameter() {
        return nestedModelsParameter;
    }
}
// end::content[]
