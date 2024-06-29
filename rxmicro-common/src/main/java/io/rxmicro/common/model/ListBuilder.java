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

package io.rxmicro.common.model;

import io.rxmicro.common.InvalidStateException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;

/**
 * The builder that builds a short-lived unmodified {@link List} instance.
 *
 * @param <E> the type of elements in this list
 * @author nedis
 * @see List
 * @see ArrayList
 * @see io.rxmicro.common.util.ExCollections#unmodifiableList(Collection)
 * @since 0.1
 */
public class ListBuilder<E> {

    private final List<E> list = new ArrayList<>();

    private boolean built;

    /**
     * Adds the specified value to the building {@link List} instance.
     *
     * @param value the specified value
     * @return the reference to this {@link ListBuilder} instance
     * @throws InvalidStateException if the {@link List} instance already built
     */
    public ListBuilder<E> add(final E value) {
        if (built) {
            throw new InvalidStateException("This builder can't be used, because the list instance already built! Create a new builder!");
        }
        list.add(value);
        return this;
    }

    /**
     * Builds the short-lived unmodified {@link List} instance.
     *
     * @return the short-lived unmodified {@link List} instance
     */
    public List<E> build() {
        built = true;
        return unmodifiableList(list);
    }
}
