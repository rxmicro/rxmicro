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

package io.rxmicro.examples.data.mongo.model.fields.setter_getter.model.nested;

import io.rxmicro.examples.data.mongo.model.fields.Status;
import org.bson.types.Binary;
import org.bson.types.Code;

import java.time.Instant;

public final class Nested {

    private Status status;

    private Instant instant;

    private Code code;

    private Binary binary;

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(final Instant instant) {
        this.instant = instant;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(final Code code) {
        this.code = code;
    }

    public Binary getBinary() {
        return binary;
    }

    public void setBinary(final Binary binary) {
        this.binary = binary;
    }
}
