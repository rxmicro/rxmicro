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

package io.rxmicro.annotation.processor.common.model;

import javax.lang.model.element.ModuleElement;
import java.util.Map;
import java.util.Optional;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class DocumentStructure implements Comparable<DocumentStructure> {

    public abstract ModuleElement getCurrentModule();

    public abstract DocumentationType getDocumentationType();

    public abstract String getProjectDirectory();

    public Optional<String> getCustomDestinationDirectory() {
        return Optional.empty();
    }

    public abstract String getName();

    public abstract String getTemplateName();

    public abstract Map<String, Object> getTemplateVariables();

    @Override
    public final int hashCode() {
        return getName().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()){
            return false;
        }
        final DocumentStructure that = (DocumentStructure) other;
        return getName().equals(that.getName());
    }

    @Override
    public final int compareTo(final DocumentStructure other) {
        return getName().compareTo(other.getName());
    }
}
