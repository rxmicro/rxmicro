/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.annotation.processor.data.component;

import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;

import java.util.Set;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public interface EntityConverterBuilder<DMF extends DataModelField, DMC extends DataObjectModelClass<DMF>> {

    Set<? extends ClassStructure> build(DataGenerationContext<DMF, DMC> dataGenerationContext);
}
