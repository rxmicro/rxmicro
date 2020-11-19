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

package org.slf4j.internal;

import org.slf4j.spi.MDCAdapter;

import java.util.Map;

/**
 * Do nothing.
 * <p>
 * The RxMicro framework does not support MDC, because {@link org.slf4j.MDC} can be used for
 * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
 *
 * @author nedis
 * @since 0.7
 * @link http://www.slf4j.org/manual.html#mdc
 * @link http://logback.qos.ch/manual/mdc.html
 */
public final class DoNothingMDCAdapter implements MDCAdapter {

    private static final DoNothingMDCAdapter INSTANCE = new DoNothingMDCAdapter();

    public static DoNothingMDCAdapter getInstance() {
        return INSTANCE;
    }

    private DoNothingMDCAdapter(){
    }

    @Override
    public void put(final String key, final String val) {
        // do nothing
    }

    @Override
    public String get(final String key) {
        return null;
    }

    @Override
    public void remove(final String key) {
        // do nothing
    }

    @Override
    public void clear() {
        // do nothing
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        return null;
    }

    @Override
    public void setContextMap(final Map<String, String> contextMap) {
        // do nothing
    }
}
