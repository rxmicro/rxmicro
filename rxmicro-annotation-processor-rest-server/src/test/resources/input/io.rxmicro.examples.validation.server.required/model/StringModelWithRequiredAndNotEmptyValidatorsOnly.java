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

package io.rxmicro.examples.validation.server.required.model;

import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.Length;
import io.rxmicro.validation.constraint.MinLength;

public class StringModelWithRequiredAndNotEmptyValidatorsOnly {

    String string;

    @MinLength(value = 1, off = true)
    String minLength;

    @Length(value = 1, off = true)
    String length;

    @Enumeration(value = {"yes", "no"}, off = true)
    String enumeration;
}
