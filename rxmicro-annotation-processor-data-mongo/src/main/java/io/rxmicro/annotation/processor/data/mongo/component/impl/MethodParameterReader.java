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

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.data.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import javax.lang.model.element.ExecutableElement;

import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class MethodParameterReader {

    private final ListIterator<Variable> iterator;

    public MethodParameterReader(final DataMethodParams dataMethodParams) {
        iterator = new ArrayList<>(dataMethodParams.getOtherParams()).listIterator();
    }

    public Optional<Variable> nextVar() {
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    public Optional<Variable> nextIfLimit() {
        return nextIf(Variable::isLimit);
    }

    public Optional<Variable> nextIfSkip() {
        return nextIf(Variable::isSkip);
    }

    private Optional<Variable> nextIf(final Predicate<Variable> predicate) {
        if (iterator.hasNext()) {
            final Variable var = iterator.next();
            if (predicate.test(var)) {
                return Optional.of(var);
            } else {
                iterator.previous();
            }
        }
        return Optional.empty();
    }

    public List<Variable> getVars(final ExecutableElement repositoryMethod,
                                  final int count) {
        if (count == 0) {
            return List.of();
        } else {
            try {
                return IntStream.range(0, count).mapToObj(value -> iterator.next()).collect(toList());
            } catch (final NoSuchElementException ignore) {
                throw new InterruptProcessingException(repositoryMethod, "Missing method parameter for parameter placeholder!");
            }
        }
    }

    public List<Variable> getUnusedVars() {
        final List<Variable> res = new ArrayList<>();
        while (iterator.hasNext()) {
            final Variable mongoVar = iterator.next();
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
