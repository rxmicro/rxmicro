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

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class DocumentedModelField {

    private final String name;

    private final String type;

    private final List<String> restrictions;

    private final String description;

    private final List<ReadMoreModel> readMore;

    public DocumentedModelField(final String name,
                                final String type,
                                final List<String> restrictions,
                                final String description,
                                final List<ReadMoreModel> readMore) {
        this.name = require(name);
        this.type = require(type);
        this.restrictions = require(restrictions);
        this.description = description;
        this.readMore = require(readMore);
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public String getName() {
        return name;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public String getType() {
        return type;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public List<String> getRestrictions() {
        return restrictions;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public boolean isDescriptionPresent() {
        return description != null;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public String getDescription() {
        return description;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public boolean isReadMorePresent() {
        return !readMore.isEmpty();
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public List<ReadMoreModel> getReadMore() {
        return readMore;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final DocumentedModelField that = (DocumentedModelField) other;
        return name.equals(that.name);
    }
}
