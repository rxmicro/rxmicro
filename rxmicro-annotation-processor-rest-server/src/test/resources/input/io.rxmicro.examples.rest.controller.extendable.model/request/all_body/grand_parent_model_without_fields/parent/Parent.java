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

package io.rxmicro.examples.rest.controller.extendable.model.request.all_body.grand_parent_model_without_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.all_body.grand_parent_model_without_fields.grand.GrandParent;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.RequestUrlPath;

public class Parent extends GrandParent {

    @RequestUrlPath
    String url;

    @PathVariable
    String parentVar;

    @Header
    String parentHeader;

    String parentParameter;
}
