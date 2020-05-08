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

package io.rxmicro.annotation.processor.data.sql.model;

/**
 * @author nedis
 * @since 0.1
 */
public class SQLKeywords {

    public static final String SELECT = "SELECT";

    public static final String INSERT = "INSERT";

    public static final String UPDATE = "UPDATE";

    public static final String DELETE = "DELETE";

    public static final String DISTINCT = "DISTINCT";

    public static final String ALL = "ALL";

    public static final String FROM = "FROM";

    public static final String IN = "IN";

    public static final String ORDER = "ORDER";

    public static final String AS = "AS";

    protected SQLKeywords() {
        // this class is extendable
    }
}
