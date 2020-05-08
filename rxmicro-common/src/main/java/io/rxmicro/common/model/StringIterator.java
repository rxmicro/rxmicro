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

/**
 * The implementation of the {@code iterator} design pattern for {@link String} type
 *
 * @author nedis
 * @since 0.1
 * @see java.util.Iterator
 * @see String
 */
public final class StringIterator {

    /**
     * Char constant that informs that end of char stream is reached
     */
    public static final char NO_MORE_CHARACTERS_PRESENT = 0;

    private final String string;

    private final int length;

    private int index;

    /**
     * Creates a new {@link StringIterator} instance for the specified string
     *
     * @param string the specified string
     * @throws NullPointerException if the specified string is {@code null}
     */
    public StringIterator(final String string) {
        this.string = string;
        this.length = string.length();
        this.index = -1;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     *
     * @return {@code true} if the iteration has more elements.
     */
    public boolean next() {
        return ++index < string.length();
    }

    /**
     * Returns the current character in the iteration or {@link #NO_MORE_CHARACTERS_PRESENT} if the current character not found.
     *
     * @return the current character in the iteration or {@link #NO_MORE_CHARACTERS_PRESENT} if the current character not found.
     */
    public char getCurrent() {
        if (index < length) {
            return string.charAt(index);
        } else {
            return NO_MORE_CHARACTERS_PRESENT;
        }
    }

    /**
     * Returns the previous character in the iteration or {@link #NO_MORE_CHARACTERS_PRESENT} if the previous character not found.
     *
     * @return the previous character in the iteration or {@link #NO_MORE_CHARACTERS_PRESENT} if the previous character not found.
     */
    public char getPrevious() {
        if (index > 0) {
            return string.charAt(index - 1);
        } else {
            return NO_MORE_CHARACTERS_PRESENT;
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements when traversing the list in the reverse direction.
     *
     * @return {@code true} if the iteration has more elements when traversing the list in the reverse direction.
     */
    public boolean previous() {
        return index-- >= 0;
    }

    /**
     * Returns the index of the current iteration state
     *
     * @return the index of the current iteration state
     */
    public int getIndex() {
        return index;
    }
}
