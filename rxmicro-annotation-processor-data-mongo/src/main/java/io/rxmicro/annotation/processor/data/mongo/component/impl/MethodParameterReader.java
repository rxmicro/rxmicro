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

package io.rxmicro.annotation.processor.data.mongo.component.impl;

import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.Var;
import io.rxmicro.annotation.processor.data.mongo.model.MongoVar;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.RepeatParameter;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MethodParameterReader {

    private final ListIterator<MongoVar> iterator;

    public MethodParameterReader(final List<? extends VariableElement> parameters,
                                 final SupportedTypesProvider supportedTypesProvider) {
        final List<MongoVar> vars = new ArrayList<>(parameters.size());
        VariableElement page = null;
        for (final VariableElement parameter : parameters) {
            final String value = parameter.getSimpleName().toString();
            if (!supportedTypesProvider.standardMethodParameters().contains(parameter.asType())) {
                final MongoVar var = new MongoVar(parameter, value);
                final int repeatCount = Optional.ofNullable(parameter.getAnnotation(RepeatParameter.class))
                        .map(RepeatParameter::value)
                        .orElse(1);
                for (int i = 0; i < repeatCount; i++) {
                    vars.add(var);
                }
            } else {
                if (Pageable.class.getName().equals(parameter.asType().toString())) {
                    if (page != null) {
                        throw new InterruptProcessingException(
                                parameter,
                                "Only one '?' parameter allowed per method!",
                                Pageable.class.getName()
                        );
                    } else {
                        page = parameter;
                        vars.add(new MongoVar(parameter, format("?.getLimit()", value)));
                        vars.add(new MongoVar(parameter, format("?.getSkip()", value)));
                    }
                }
            }
        }
        iterator = vars.listIterator();
    }

    public Optional<MongoVar> nextVar() {
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    public Optional<MongoVar> nextIfLimit() {
        return nextIf(MongoVar::isLimit);
    }

    public Optional<MongoVar> nextIfSkip() {
        return nextIf(MongoVar::isSkip);
    }

    private Optional<MongoVar> nextIf(final Predicate<MongoVar> predicate) {
        if (iterator.hasNext()) {
            final MongoVar mongoVar = iterator.next();
            if (predicate.test(mongoVar)) {
                return Optional.of(mongoVar);
            } else {
                iterator.previous();
            }
        }
        return Optional.empty();
    }

    public List<MongoVar> getVars(final ExecutableElement repositoryMethod,
                                  final int count) {
        if (count == 0) {
            return List.of();
        } else {
            try {
                return IntStream.range(0, count).mapToObj(value -> iterator.next()).collect(toList());
            } catch (final NoSuchElementException e) {
                throw new InterruptProcessingException(repositoryMethod,
                        "Missing method parameter for parameter placeholder!");
            }
        }
    }

    public List<Var> getUnusedVars() {
        final List<Var> res = new ArrayList<>();
        while (iterator.hasNext()) {
            final MongoVar mongoVar = iterator.next();
            if (mongoVar.is(Pageable.class)) {
                if (mongoVar.isLimit()) {
                    res.add(mongoVar);
                }
            } else {
                res.add(mongoVar);
            }
        }
        return res;
    }
}
