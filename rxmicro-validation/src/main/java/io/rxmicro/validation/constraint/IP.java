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

package io.rxmicro.validation.constraint;

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.internal.SelfDocumented;
import io.rxmicro.validation.validator.IPConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.validation.constraint.IP.Version.IP_V4;
import static io.rxmicro.validation.constraint.IP.Version.IP_V6;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must be a valid IP address
 *
 * @author nedis
 * @since 0.1
 * @see IPConstraintValidator
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = {
                String.class
        },
        validatorClass = {
                IPConstraintValidator.class
        }
)
@SelfDocumented
public @interface IP {

    /**
     * Allows disabling the validation rule if this rule is inherited from super class. <p>
     * By default, disable is off
     *
     * @return  {@code true} if the validation must be disabled,
     */
    boolean off() default false;

    /**
     * By default, support all versions, i.e. versions 4 and 6
     *
     * @return the supported IP protocol {@link Version}s
     */
    Version[] value() default {IP_V4, IP_V6};

    /**
     * IP versions
     *
     * @author nedis
     * @since 0.1
     */
    enum Version {

        /**
         * IP version 4
         */
        @ReadMore(
                caption = "What is IP version 4?",
                link = "https://en.wikipedia.org/wiki/IPv4"
        )
        IP_V4("ipv4", 4),

        /**
         * IP version 6
         */
        @ReadMore(
                caption = "What is IP version 6?",
                link = "https://en.wikipedia.org/wiki/IPv6"
        )
        IP_V6("ipv6", 6);

        private final String jsonFormat;

        private final int version;

        Version(final String jsonFormat, final int version) {
            this.jsonFormat = jsonFormat;
            this.version = version;
        }

        public String getJsonFormat() {
            return jsonFormat;
        }

        public int getVersion() {
            return version;
        }

        public ReadMore getReadMore() {
            try {
                return getClass().getDeclaredField(name()).getAnnotation(ReadMore.class);
            } catch (final NoSuchFieldException e) {
                throw new ImpossibleException(e, "ReadMore must be present");
            }
        }
    }
}
