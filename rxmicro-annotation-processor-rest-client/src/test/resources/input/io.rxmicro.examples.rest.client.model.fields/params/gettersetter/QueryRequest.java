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

package io.rxmicro.examples.rest.client.model.fields.params.gettersetter;

import io.rxmicro.examples.rest.client.model.fields.Status;
import io.rxmicro.rest.RepeatQueryParameter;

import java.util.List;
import java.util.Set;

public final class QueryRequest extends Abstract {

    @RepeatQueryParameter
    private List<Status> repeatingStatuses;

    @RepeatQueryParameter
    private Set<Status> repeatingStatusSet;

    public List<Status> getRepeatingStatuses() {
        return repeatingStatuses;
    }

    public void setRepeatingStatuses(final List<Status> repeatingStatuses) {
        this.repeatingStatuses = repeatingStatuses;
    }

    public Set<Status> getRepeatingStatusSet() {
        return repeatingStatusSet;
    }

    public void setRepeatingStatusSet(final Set<Status> repeatingStatusSet) {
        this.repeatingStatusSet = repeatingStatusSet;
    }
}
