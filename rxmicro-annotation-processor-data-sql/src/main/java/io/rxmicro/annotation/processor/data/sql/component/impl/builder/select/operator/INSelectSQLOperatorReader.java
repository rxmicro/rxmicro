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

package io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.operator;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.SelectSQLOperatorReader;
import io.rxmicro.data.sql.detail.SQLParams;

import java.util.List;
import java.util.ListIterator;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.IN;
import static io.rxmicro.common.util.Formats.FORMAT_PLACEHOLDER_TOKEN;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public class INSelectSQLOperatorReader implements SelectSQLOperatorReader {

    @Override
    public boolean canRead(final String token) {
        return IN.equalsIgnoreCase(token);
    }

    @Override
    public void read(final ClassHeader.Builder classHeaderBuilder,
                     final ListIterator<String> iterator,
                     final List<Variable> methodParams,
                     final List<String> formatParams) {
        boolean isOpenParenthesesFound = false;
        while (iterator.hasNext()) {
            final String token = iterator.next();
            if (FORMAT_PLACEHOLDER_TOKEN.equals(token)) {
                if (!isOpenParenthesesFound) {
                    iterator.previous();
                    iterator.add("(");
                    iterator.next();
                    iterator.add(")");
                }
                final Variable variable = methodParams.remove(0);
                formatParams.add(resolveParameter(classHeaderBuilder, variable));
            } else if (!"(".equals(token)) {
                iterator.previous();
                break;
            } else {
                isOpenParenthesesFound = true;
            }
        }
    }

    private String resolveParameter(final ClassHeader.Builder classHeaderBuilder,
                                    final Variable variable) {
        if (List.class.getName().equals(getTypes().erasure(variable.getType()).toString())) {
            final TypeMirror itemType = ((DeclaredType) variable.getType()).getTypeArguments().get(0);
            if (String.class.getName().equals(itemType.toString())) {
                classHeaderBuilder.addStaticImport(SQLParams.class, "joinStringParams");
                return format("joinStringParams(?)", variable.getName());
            } else {
                return asEnumElement(itemType)
                        .map(e -> {
                            classHeaderBuilder.addStaticImport(SQLParams.class, "joinEnumParams");
                            return format("joinEnumParams(?)", variable.getName());
                        })
                        .orElseGet(() -> {
                            classHeaderBuilder.addStaticImport(SQLParams.class, "joinParams");
                            return format("joinParams(?)", variable.getName());
                        });
            }
        } else if (String.class.getName().equals(variable.getType().toString())) {
            return format("\"'\" + ? + \"'\"", variable.getName());
        } else {
            return asEnumElement(variable.getType())
                    .map(e -> format("\"'\" + ?.name() + \"'\"", variable.getName()))
                    .orElseGet(variable::getName);
        }
    }
}
