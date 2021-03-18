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
 * The implementation of the {@code iterator} design pattern for {@link String} type.
 *
 * @author nedis
 * @see java.util.Iterator
 * @see String
 * @since 0.1
 */
public final class StringIterator {

    /**
     * Char constant that informs that end of char stream is reached.
     */
    public static final char NO_MORE_CHARACTERS_PRESENT = 0;

    private final String string;

    private final int length;

    private int index;

    /**
     * Creates a new {@link StringIterator} instance for the specified string with init iteration index equals to {@code -1}.
     *
     * @param string the specified string
     * @see #StringIterator(String, boolean)
     * @throws NullPointerException if the specified string is {@code null}
     */
    public StringIterator(final String string) {
        this(string, true);
    }

    /**
     * Creates a new {@link StringIterator} instance for the specified string.
     *
     * <p>
     * If {@code startFromFirstCharacter} is {@code true}, than the init value of iterator index is {@code -1},
     * otherwise the init value of iterator index is {@code string.length()}
     *
     * @param string the specified string
     * @param startFromFirstCharacter the boolean flag that indicates the init value of the iteration index.
     *         If {@code startFromFirstCharacter} is {@code true}, than the init value of iterator index is {@code -1},
     *         otherwise the init value of iterator index is {@code string.length()}.
     * @throws NullPointerException if the specified string is {@code null}
     */
    public StringIterator(final String string,
                          final boolean startFromFirstCharacter) {
        this.string = string;
        this.length = string.length();
        this.index = startFromFirstCharacter ? -1 : this.length;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     *
     * @return {@code true} if the iteration has more elements.
     */
    public boolean next() {
        return ++index < length;
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
     * Returns the next character in the iteration or {@link #NO_MORE_CHARACTERS_PRESENT} if the next character not found.
     *
     * @return the next character in the iteration or {@link #NO_MORE_CHARACTERS_PRESENT} if the next character not found.
     */
    public char getNext() {
        if (index < length - 1) {
            return string.charAt(index + 1);
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
        return --index >= 0;
    }

    /**
     * Returns the index of the current iteration state.
     *
     * @return the index of the current iteration state
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the source string.
     *
     * @return the source string
     * @since 0.6
     */
    public String getSource() {
        return string;
    }

    @Override
    public String toString() {
        return "StringIterator{" +
                "string='" + string + '\'' +
                ", index=" + index +
                '}';
    }
}
