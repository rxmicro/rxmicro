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

package io.rxmicro.annotation.processor.documentation.component;

import java.util.Optional;
import javax.lang.model.element.Element;

/**
 * @author nedis
 * @since 0.1
 */
public interface TitleReader {

    Optional<String> getTitle(Element element);

    default String readTitleOrDefault(final Element element) {
        return getTitle(element).orElseGet(() -> getDefaultTitle(element));
    }

    String getDefaultTitle(Element element);
}
