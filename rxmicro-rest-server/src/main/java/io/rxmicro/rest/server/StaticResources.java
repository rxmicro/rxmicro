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

package io.rxmicro.rest.server;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * This annotation configures the static resource.
 *
 * <p>
 * By default the RxMicro framework uses {@link #urls()} or {@link #value()} to get relative file path to the specified resources.
 * It means if developer specified the following url: {@code /directory/file.txt} than the following file path will be returned:
 * ({@link HttpServerConfig#getRootDirectory()} + {@code /directory/file.txt}).
 *
 * <p>
 * If it is necessary to get two different urls on the same file path, the {@link #filePaths()} annotation parameter must be used:
 * <pre>
 * {@code
 * @StaticResources(
 *  urls = {
 *      "/url1.txt",
 *      "/url2.txt"
 *  },
 *  filePaths = {
 *      "directory/file.txt",
 *      "directory/file.txt"
 *  }
 * )
 * }
 * </pre>
 *
 * <p>
 * Note:
 * The RxMicro framework recommends starting the all urls with {@code '/'} character!
 * If @{@link StaticResources} annotation contain any url from {@link #urls()} or {@link #value()} list that does not start
 * with {@code '/'} character, than the RxMicro framework adds this missing start character automatically!
 *
 * <p>
 * But file paths from {@link #filePaths()} list is not processed by the RxMicro framework, because file paths have other semantic:
 * <ul>
 *     <li>
 *         If file path starts with {@code '/'} character it means that it is absolute file path!
 *     </li>
 *     <li>
 *         If file path does not start with {@code '/'} character it means that it is relative file path!
 *         And the RxMicro framework must use {@link HttpServerConfig#getRootDirectory()} to resolve relative path to the absolute one!
 *     </li>
 * </ul>
 *
 * <p>
 * So be careful when using the {@code '/'} character for urls and file paths,
 * because this character has different semantic for these items!
 *
 * @author nedis
 * @see HttpServerConfig#getRootDirectory()
 * @since 0.8
 */
@Documented
@Retention(CLASS)
@Target({MODULE, TYPE})
@Repeatable(StaticResources.List.class)
public @interface StaticResources {

    /**
     * Returns the static relative resource urls.
     *
     * <p>
     * Alias for {@link #urls()} annotation parameter.
     *
     * <p>
     * The RxMicro framework uses {@link HttpServerConfig#getRootDirectory()} to resolve an absolute path to the relative resources.
     *
     * @return the static relative resources.
     * @see HttpServerConfig#getRootDirectory()
     */
    String[] value() default {};

    /**
     * Returns the static relative resource urls.
     *
     * <p>
     * Alias for {@link #value()} annotation parameter.
     *
     * <p>
     * The RxMicro framework uses {@link HttpServerConfig#getRootDirectory()} to resolve an absolute path to the relative resources.
     *
     * @return the static relative resources.
     * @see HttpServerConfig#getRootDirectory()
     */
    String[] urls() default {};

    /**
     * Returns the file paths for provided {@link #urls()} or {@link #value()} annotation parameters.
     *
     * <p>
     * If the {@link #filePaths()} annotation parameter is present, than
     * {@code filePaths().length} must be equal to {@code urls().length} or {@code value().length}
     *
     * <p>
     * Note:
     * The RxMicro framework recommends starting the all urls with {@code '/'} character!
     * If @{@link StaticResources} annotation contain any url from {@link #urls()} or {@link #value()} list that does not start
     * with {@code '/'} character, than the RxMicro framework adds this missing start character automatically!
     *
     * <p>
     * But file paths from {@link #filePaths()} list is not processed by the RxMicro framework, because file paths have other semantic:
     * <ul>
     *     <li>
     *         If file path starts with {@code '/'} character it means that it is absolute file path!
     *     </li>
     *     <li>
     *         If file path does not start with {@code '/'} character it means that it is relative file path!
     *         And the RxMicro framework must use {@link HttpServerConfig#getRootDirectory()} to resolve relative path to the absolute one!
     *     </li>
     * </ul>
     *
     * <p>
     * So be careful when using the {@code '/'} character for urls and file paths,
     * because this character has different semantic for these items!
     *
     * @return the file paths for provided {@link #urls()} or {@link #value()} annotation parameters.
     * @see HttpServerConfig#getRootDirectory()
     */
    String[] filePaths() default {};

    /**
     * Defines several {@link StaticResources} annotations on the same element.
     *
     * @author nedis
     * @since 0.8
     */
    @Documented
    @Retention(CLASS)
    @Target({MODULE, TYPE})
    @interface List {

        /**
         * Returns the several {@link StaticResources} annotations on the same element.
         *
         * @return the several {@link StaticResources} annotations on the same element.
         */
        StaticResources[] value();
    }
}
