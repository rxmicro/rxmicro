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

package io.rxmicro.annotation.processor.documentation.asciidoctor.model;

import io.rxmicro.common.meta.ReadMore;

import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class Characteristics {

    private final List<String> restrictions;

    private final List<ReadMore> readMores;

    private final String standardDescription;

    public Characteristics(final List<String> restrictions,
                           final List<ReadMore> readMores,
                           final String standardDescription) {
        this.restrictions = require(restrictions);
        this.readMores = require(readMores);
        this.standardDescription = standardDescription;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public List<ReadMore> getReadMores() {
        return readMores;
    }

    public Optional<String> getStandardDescription() {
        return Optional.ofNullable(standardDescription);
    }
}
