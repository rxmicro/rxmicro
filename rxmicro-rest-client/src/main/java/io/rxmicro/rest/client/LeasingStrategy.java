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

package io.rxmicro.rest.client;

/**
 * The leasing strategy.
 *
 * @author nedis
 * @since 0.8
 */
public enum LeasingStrategy {

    /**
     * The connection selection is first in, first out.
     *
     * <p>
     * Configure the pool so that if there are idle connections (i.e. pool is under-utilized),
     * the next acquire operation will get the <b>Least Recently Used</b> connection
     * (LRU, i.e. the connection that was released first among the current idle connections).
     */
    FIFO,

    /**
     * The connection selection is last in, first out.
     *
     * <p>
     * Configure the pool so that if there are idle connections (i.e. pool is under-utilized),
     * the next acquire operation will get the <b>Most Recently Used</b> connection
     * (MRU, i.e. the connection that was released last among the current idle connections).
     */
    LIFO
}
