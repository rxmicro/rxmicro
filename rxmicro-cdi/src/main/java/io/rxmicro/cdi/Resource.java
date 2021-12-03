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

package io.rxmicro.cdi;

import io.rxmicro.cdi.resource.ResourceConverter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Indicates the need to inject the external resource into the annotated class field or method parameter.
 *
 * <p>
 * External resource is:
 * <ul>
 *     <li>File</li>
 *     <li>Directory</li>
 *     <li>Classpath resource</li>
 *     <li>URL Resource</li>
 *     <li>etc</li>
 * </ul>
 *
 * <p>
 * <i>The RxMicro framework uses blocking API to inject resource during startup!</i>
 *
 * @author nedis
 * @see Inject
 * @see Autowired
 * @since 0.6
 */
@Documented
@Retention(CLASS)
@Target({FIELD, CONSTRUCTOR, METHOD, PARAMETER})
public @interface Resource {

    /**
     * If {@code true}, and the appropriate resource is not found, the RxMicro framework will skip injection of this
     * method or field rather than produce an error.
     *
     * <p>
     * When applied to a field, any default value already assigned to the field will remain for optional injection.
     *
     * <p>
     * <i>(The RxMicro framework will not actively null out the field).</i>
     *
     * @return {@code true} if current injection point is optional
     */
    boolean optional() default false;

    /**
     * Returns the resource path.
     *
     * <p>
     * Example of valid resource paths:
     * <ul>
     *     <li>{@code /home/rxmicro/config.json}</li>
     *     <li>{@code /home/rxmicro/config.properties}</li>
     *     <li>{@code file:///home/rxmicro/config.json}</li>
     *     <li>{@code file:///home/rxmicro/config.properties}</li>
     *     <li>{@code classpath:config.json}</li>
     *     <li>{@code classpath:config.properties}</li>
     * </ul>
     *
     * @return the resource path
     */
    String value();

    /**
     * Returns the custom resource converter class.
     *
     * <p>
     * If converter class is not specified, the RxMicro framework tries to detect valid resource converter automatically.
     *
     * <p>
     * Autodetect resource converter rules:
     * <ul>
     *     <li>
     *         {@link io.rxmicro.cdi.resource.ClasspathJsonArrayResourceConverter} is used if:
     *         <ul>
     *             <li>Resource path starts with {@code classpath:} prefix</li>
     *             <li>Resource path ends with {@code json} extension</li>
     *             <li>Annotated by @{@link Resource} annotation field has {@link java.util.List}{@code <Object>} type</li>
     *         </ul>
     *     </li>
     *     <li>
     *         {@link io.rxmicro.cdi.resource.ClasspathJsonObjectResourceConverter} is used if:
     *         <ul>
     *             <li>Resource path starts with {@code classpath:} prefix</li>
     *             <li>Resource path ends with {@code json} extension</li>
     *             <li>Annotated by @{@link Resource} annotation field has {@link java.util.Map}{@code <String, Object>} type</li>
     *         </ul>
     *     </li>
     *     <li>
     *         {@link io.rxmicro.cdi.resource.ClasspathPropertiesResourceConverter} is used if:
     *         <ul>
     *             <li>Resource path starts with {@code classpath:} prefix</li>
     *             <li>Resource path ends with {@code properties} extension</li>
     *         </ul>
     *     </li>
     *     <li>
     *         {@link io.rxmicro.cdi.resource.FileJsonArrayResourceConverter} is used if:
     *         <ul>
     *             <li>Resource path starts with {@code file://} prefix or prefix is missing</li>
     *             <li>Resource path ends with {@code json} extension</li>
     *             <li>Annotated by @{@link Resource} annotation field has {@link java.util.List}{@code <Object>} type</li>
     *         </ul>
     *     </li>
     *     <li>
     *         {@link io.rxmicro.cdi.resource.FileJsonObjectResourceConverter} is used if:
     *         <ul>
     *             <li>Resource path starts with {@code file://} prefix or prefix is missing</li>
     *             <li>Resource path ends with {@code json} extension</li>
     *             <li>Annotated by @{@link Resource} annotation field has {@link java.util.Map}{@code <String, Object>} type</li>
     *         </ul>
     *     </li>
     *     <li>
     *         {@link io.rxmicro.cdi.resource.FilePropertiesResourceConverter} is used if:
     *         <ul>
     *             <li>Resource path starts with {@code file://} prefix or prefix is missing</li>
     *             <li>Resource path ends with {@code properties} extension</li>
     *         </ul>
     *     </li>
     *     <li></li>
     * </ul>
     *
     * @return the custom resource converter class
     * @see io.rxmicro.cdi.resource.ClasspathJsonArrayResourceConverter
     * @see io.rxmicro.cdi.resource.ClasspathJsonObjectResourceConverter
     * @see io.rxmicro.cdi.resource.ClasspathPropertiesResourceConverter
     * @see io.rxmicro.cdi.resource.FileJsonArrayResourceConverter
     * @see io.rxmicro.cdi.resource.FileJsonObjectResourceConverter
     * @see io.rxmicro.cdi.resource.FilePropertiesResourceConverter
     */
    Class<? extends ResourceConverter> converterClass() default ResourceConverter.class;
}
