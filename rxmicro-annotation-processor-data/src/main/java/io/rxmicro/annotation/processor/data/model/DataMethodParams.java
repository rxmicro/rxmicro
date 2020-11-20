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

package io.rxmicro.annotation.processor.data.model;

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.ExCollections.unmodifiableMap;

/**
 * @author nedis
 * @since 0.7
 */
public final class DataMethodParams {

    private final Map<String, List<Variable>> params;

    private final List<Variable> otherParams;

    public DataMethodParams(final Map<String, List<Variable>> params,
                            final List<Variable> otherParams) {
        this.params = unmodifiableMap(params);
        this.otherParams = unmodifiableList(otherParams);
    }

    public List<Variable> getParamsOfGroup(final String groupName) {
        return Optional.ofNullable(params.get(groupName)).orElse(List.of());
    }

    public Optional<Variable> getSingleParamOfGroup(final String groupName) {
        final List<Variable> params = getParamsOfGroup(groupName);
        if (params.isEmpty()) {
            return Optional.empty();
        } else if (params.size() > 1) {
            final Variable last = params.get(params.size() - 1);
            throw new InterruptProcessingException(
                    last.getElement(),
                    "Detected redundant '?' parameter! Remove it!",
                    last.getName()
            );
        } else {
            return Optional.of(params.get(0));
        }
    }

    public List<Variable> getOtherParams() {
        return otherParams;
    }
}
