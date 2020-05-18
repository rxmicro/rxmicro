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

package io.rxmicro.annotation.processor.data.sql.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.data.sql.model.reactor.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.data.sql.model.TransactionType.SUPPORTED_TRANSACTION_TYPES;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class TransactionResolver {

    public Optional<Variable> getTransactionParameter(final ExecutableElement method) {
        final List<VariableElement> transactions = method.getParameters().stream()
                .filter(v -> SUPPORTED_TRANSACTION_TYPES.contains(v.asType().toString()))
                .collect(Collectors.toList());
        if (transactions.isEmpty()) {
            return Optional.empty();
        } else if (transactions.size() > 1) {
            throw new InterruptProcessingException(
                    method,
                    "Expected only one method parameter of type '?'",
                    Transaction.class.getSimpleName()
            );
        } else {
            return Optional.of(new Variable(transactions.get(0)));
        }
    }
}
