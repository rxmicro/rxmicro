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

package io.rxmicro.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * This annotation provides an opportunity to disable the generation of validators for the selected group of classes in the project:
 * <p>
 * <ul>
 *     <li>
 *         If a model class is annotated by this annotation, then only for this model class the validator won’t be generated.
 *     </li>
 *     <li>
 *         If this annotation annotates the package-info.java class, then for all classes from the specified package and all its
 *          subpackages no validators will be generated.
 *          </li>
 *     <li>
 *         If this annotation annotates the module-info.java descriptor, then for all classes in the current module no validators
 *         will be generated. (This behavior is similar to the removal of the {@code rxmicro.validation} module from the
 *         {@code module-info.java} descriptor.)
 *     </li>
 * </ul>
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({TYPE, PACKAGE, MODULE, ANNOTATION_TYPE})
public @interface DisableValidation {

}
