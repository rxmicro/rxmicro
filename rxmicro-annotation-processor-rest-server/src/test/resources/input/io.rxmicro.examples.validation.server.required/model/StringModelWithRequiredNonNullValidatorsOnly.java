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

package io.rxmicro.examples.validation.server.required.model;

import io.rxmicro.validation.constraint.AllowEmptyString;
import io.rxmicro.validation.constraint.Base64URLEncoded;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.constraint.DigitsOnly;
import io.rxmicro.validation.constraint.DomainName;
import io.rxmicro.validation.constraint.Email;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.IP;
import io.rxmicro.validation.constraint.LatinAlphabetOnly;
import io.rxmicro.validation.constraint.Length;
import io.rxmicro.validation.constraint.MinLength;
import io.rxmicro.validation.constraint.Phone;
import io.rxmicro.validation.constraint.Telegram;
import io.rxmicro.validation.constraint.URI;
import io.rxmicro.validation.constraint.Viber;
import io.rxmicro.validation.constraint.WhatsApp;

public class StringModelWithRequiredNonNullValidatorsOnly {

    @AllowEmptyString
    String allowEmptyString;

    @MinLength(1)
    String minLength;

    @Length(1)
    String length;

    @WhatsApp
    String whatsApp;

    @Viber
    String viber;

    @Telegram
    String telegram;

    @Phone
    String phone;

    @LatinAlphabetOnly
    String latinAlphabetOnly;

    @URI
    String uri;

    @IP
    String ip;

    @HostName
    String hostName;

    @DomainName
    String domainName;

    @Enumeration({"yes", "no"})
    String enumeration;

    @Email
    String email;

    @DigitsOnly
    String digitsOnly;

    @CountryCode
    String countryCode;

    @Base64URLEncoded
    String base64URLEncoded;
}
