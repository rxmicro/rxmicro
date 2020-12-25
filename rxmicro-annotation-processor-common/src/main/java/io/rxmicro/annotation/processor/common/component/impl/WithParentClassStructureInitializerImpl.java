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

import static io.rxmicro.annotation.processor.common.util.LoggerMessages.getLoggableParentChildRelationFragment;

/**
 * @author nedis
 * @since 0.7.2
 */
@Singleton
public final class WithParentClassStructureInitializerImpl extends BaseProcessorComponent
        implements WithParentClassStructureInitializer {

    @Override
    public <CS extends ClassStructure & WithParentClassStructure<CS, MF, MC>,
            MF extends ModelField,
            MC extends ObjectModelClass<MF>> void setParentIfExists(final Collection<CS> classStructureCandidates) {
        for (final CS currentItem : classStructureCandidates) {
            setParent(classStructureCandidates, currentItem);
        }
    }

    private <CS extends ClassStructure & WithParentClassStructure<CS, MF, MC>,
            MF extends ModelField,
            MC extends ObjectModelClass<MF>> void setParent(final Collection<CS> classStructureCandidates,
                                                            final CS currentItem) {
        final MC modelClass = currentItem.getModelClass();
        for (final ObjectModelClass<MF> parent : modelClass.getAllParents()) {
            final String javaFullClassName = parent.getJavaFullClassName();
            for (final CS classStructureCandidate : classStructureCandidates) {
                if (!currentItem.equals(classStructureCandidate) &&
                        classStructureCandidate.getModelClass().getJavaFullClassName().equals(javaFullClassName)) {
                    if (currentItem.assignParent(classStructureCandidate)) {
                        debug(
                                "Set parent class structure:\n?",
                                () -> getLoggableParentChildRelationFragment(
                                        0,
                                        true,
                                        classStructureCandidate,
                                        currentItem
                                )
                        );
                    }
                    return;
                }
            }
        }
    }
}
