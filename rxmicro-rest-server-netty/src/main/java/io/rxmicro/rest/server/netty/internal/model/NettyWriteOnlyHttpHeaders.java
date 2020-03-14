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

package io.rxmicro.rest.server.netty.internal.model;

import io.netty.handler.codec.http.HttpHeaders;
import io.rxmicro.http.local.AbstractRepeatableValues;
import io.rxmicro.http.local.RepeatableHttpHeaders;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class NettyWriteOnlyHttpHeaders extends AbstractRepeatableValues<RepeatableHttpHeaders>
        implements RepeatableHttpHeaders {

    HttpHeaders toHttpHeaders() {
        return new AbstractReadableHttpHeaders() {
            @Override
            public String get(final String name) {
                return NettyWriteOnlyHttpHeaders.this.getValue(name);
            }

            @Override
            public List<String> getAll(final String name) {
                return NettyWriteOnlyHttpHeaders.this.getValues(name);
            }

            @Override
            public List<Map.Entry<String, String>> entries() {
                return NettyWriteOnlyHttpHeaders.this.getEntries();
            }

            @Override
            public boolean contains(final String name) {
                return NettyWriteOnlyHttpHeaders.this.contains(name);
            }

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return NettyWriteOnlyHttpHeaders.this.getEntries().iterator();
            }

            @Override
            public Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence() {
                final Iterator<Map.Entry<String, String>> iterator = iterator();
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public Map.Entry<CharSequence, CharSequence> next() {
                        final Map.Entry<String, String> next = iterator.next();
                        return entry(next.getKey(), next.getValue());
                    }
                };
            }

            @Override
            public int size() {
                return NettyWriteOnlyHttpHeaders.this.size();
            }

            @Override
            public Set<String> names() {
                return NettyWriteOnlyHttpHeaders.this.names();
            }
        };
    }
}
