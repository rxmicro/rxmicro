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
import io.rxmicro.annotation.processor.common.component.ModuleInfoCustomizer;
import io.rxmicro.annotation.processor.common.model.ModuleInfoItem;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.config.Configs;
import io.rxmicro.runtime.detail.Runtimes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.ModuleElement.Directive;
import javax.lang.model.element.ModuleElement.ExportsDirective;
import javax.lang.model.element.ModuleElement.OpensDirective;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_CONFIG_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_RUNTIME_MODULE;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.element.ModuleElement.DirectiveKind.EXPORTS;
import static javax.lang.model.element.ModuleElement.DirectiveKind.OPENS;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ModuleInfoCustomizerImpl implements ModuleInfoCustomizer {

    private final Map<RxMicroModule, Map.Entry<Class<?>, String>> moduleMapping = Map.of(
            RX_MICRO_RUNTIME_MODULE, entry(Runtimes.class, "getRuntimeModule"),
            RX_MICRO_CONFIG_MODULE, entry(Configs.class, "getConfigModule")
    );

    @Override
    public List<ModuleInfoItem> build(final ModuleElement currentModule,
                                      final List<Map.Entry<String, RxMicroModule>> toExports,
                                      final List<Map.Entry<String, RxMicroModule>> toOpens) {
        final List<ModuleInfoItem> result = new ArrayList<>();
        final List<? extends Directive> directives = currentModule.getDirectives();
        addMissingExports(
                directives.stream()
                        .filter(d -> d.getKind() == EXPORTS)
                        .map(d -> (ExportsDirective) d)
                        .collect(toList()),
                toExports,
                result
        );
        addMissingOpens(
                directives.stream()
                        .filter(d -> d.getKind() == OPENS)
                        .map(d -> (OpensDirective) d)
                        .collect(toList()),
                toOpens,
                result
        );
        return result;
    }

    private void addMissingExports(final List<ExportsDirective> directives,
                                   final List<Map.Entry<String, RxMicroModule>> toExports,
                                   final List<ModuleInfoItem> result) {
        addMissingDirectives(
                EXPORTS,
                entry -> directives.stream()
                        .filter(d -> d.getPackage().getQualifiedName().toString().equals(entry.getKey()))
                        .flatMap(d -> d.getTargetModules().stream()
                                .filter(m -> m.getQualifiedName().toString().equals(entry.getValue().getName())))
                        .findFirst().isEmpty(),
                toExports,
                result
        );
    }

    private Map.Entry<Class<?>, String> getModuleResolveExpressionEntry(final Map.Entry<String, RxMicroModule> entry) {
        return Optional.ofNullable(moduleMapping.get(entry.getValue())).orElseThrow(() -> {
            throw new InternalErrorException("The RxMicro module not configured: ?", entry.getValue());
        });
    }

    private void addMissingOpens(final List<OpensDirective> directives,
                                 final List<Map.Entry<String, RxMicroModule>> toOpens,
                                 final List<ModuleInfoItem> result) {
        addMissingDirectives(
                OPENS,
                entry -> directives.stream()
                        .filter(d -> d.getPackage().getQualifiedName().toString().equals(entry.getKey()))
                        .flatMap(d -> d.getTargetModules().stream()
                                .filter(m -> m.getQualifiedName().toString().equals(entry.getValue().getName())))
                        .findFirst().isEmpty(),
                toOpens,
                result
        );
    }

    private void addMissingDirectives(final ModuleElement.DirectiveKind directiveKind,
                                      final Predicate<Map.Entry<String, RxMicroModule>> missingDirectivePredicate,
                                      final List<Map.Entry<String, RxMicroModule>> toAdd,
                                      final List<ModuleInfoItem> result) {
        for (final Map.Entry<String, RxMicroModule> entry : toAdd) {
            if (missingDirectivePredicate.test(entry)) {
                final Map.Entry<Class<?>, String> moduleResolveExpressionEntry = getModuleResolveExpressionEntry(entry);
                result.add(new ModuleInfoItem(
                        directiveKind, entry.getKey(), moduleResolveExpressionEntry.getKey(), moduleResolveExpressionEntry.getValue()
                ));
            }
        }
    }
}
