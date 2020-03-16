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

import io.rxmicro.rest.model.ClientExchangeFormatModule;
import io.rxmicro.rest.model.GenerateOption;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * REST client generator configuration
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(MODULE)
public @interface RestClientGeneratorConfig {

    ClientExchangeFormatModule exchangeFormat() default ClientExchangeFormatModule.AUTO_DETECT;

    GenerateOption generateRequestValidators() default GenerateOption.DISABLED;

    RequestValidationMode requestValidationMode() default RequestValidationMode.THROW_EXCEPTION;

    GenerateOption generateResponseValidators() default GenerateOption.AUTO_DETECT;

    boolean generateRequiredModuleDirectives() default true;

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    enum RequestValidationMode {

        THROW_EXCEPTION,

        RETURN_ERROR_SIGNAL
    }
}
