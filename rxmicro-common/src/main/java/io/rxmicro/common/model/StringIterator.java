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

package io.rxmicro.common.model;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class StringIterator {

    private final String string;

    private int index;

    public StringIterator(final String string) {
        this.string = string;
        this.index = -1;
    }

    public boolean next() {
        return ++index < string.length();
    }

    public char getCurrent() {
        return string.charAt(index);
    }

    public char getPrevious() {
        if (index > 0) {
            return string.charAt(index - 1);
        } else {
            return 0;
        }
    }

    public void previous() {
        index--;
    }

    public int getIndex() {
        return index;
    }
}
