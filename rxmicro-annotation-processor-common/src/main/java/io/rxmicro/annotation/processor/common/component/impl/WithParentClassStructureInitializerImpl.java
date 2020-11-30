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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.WithParentClassStructureInitializer;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.WithParentClassStructure;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;

import java.util.Collection;
import java.util.Optional;

/**
 * @author nedis
 * @since 0.7.2
 */
@Singleton
public final class WithParentClassStructureInitializerImpl extends AbstractProcessorComponent implements WithParentClassStructureInitializer {

    @Override
    public <CS extends ClassStructure, MF extends ModelField, MC extends ObjectModelClass<MF>> void setParentIfExists(
            final WithParentClassStructure<CS, MF, MC> withParentClassStructure,
            final Collection<CS> classStructureCandidates) {
        final MC modelClass = withParentClassStructure.getModelClass();
        for (final ObjectModelClass<MF> parent : modelClass.getAllParents()) {
            final String javaFullClassName = parent.getJavaFullClassName();
            for (final CS classStructureCandidate : classStructureCandidates) {
                if (!withParentClassStructure.equals(classStructureCandidate)) {
                    final Optional<String> modelClassFullClassNameOptional = classStructureCandidate.getModelClassFullClassName();
                    if (modelClassFullClassNameOptional.isPresent() && modelClassFullClassNameOptional.get().equals(javaFullClassName)) {
                        if (withParentClassStructure.setParent(classStructureCandidate)) {
                            debug(
                                    "Set parent class structure:\n?\n\t?",
                                    classStructureCandidate.getTargetFullClassName(),
                                    withParentClassStructure.getTargetFullClassName()
                            );
                        }
                        return;
                    }
                }
            }
        }
    }
}
