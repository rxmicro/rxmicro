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

package io.rxmicro.validation.validator;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Telegram} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Telegram
 * @since 0.1
 */
public class TelegramConstraintValidator extends PhoneConstraintValidator {

    /**
     * Creates the default instance of {@link TelegramConstraintValidator} with the specified parameters.
     *
     * @param withoutPlus  value must be without plus or not
     * @param allowsSpaces allows whitespaces or not
     */
    public TelegramConstraintValidator(final boolean withoutPlus,
                                       final boolean allowsSpaces) {
        super(withoutPlus, allowsSpaces);
    }
}
