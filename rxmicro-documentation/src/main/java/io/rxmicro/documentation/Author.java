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

package io.rxmicro.documentation;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.documentation.Constants.DEFAULT_AUTHOR;
import static io.rxmicro.documentation.Constants.DEFAULT_EMAIL;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The full name and email of the project author
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(MODULE)
@Repeatable(Author.List.class)
public @interface Author {

    String name() default DEFAULT_AUTHOR;

    String email() default DEFAULT_EMAIL;

    /**
     * Defines several authors per project.
     *
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target(MODULE)
    @interface List {

        Author[] value();
    }
}
