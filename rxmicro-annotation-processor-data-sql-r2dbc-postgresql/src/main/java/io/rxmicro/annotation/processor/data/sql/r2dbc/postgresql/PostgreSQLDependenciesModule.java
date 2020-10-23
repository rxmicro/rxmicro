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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.TokenParserRule;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.data.component.DataClassStructureBuilder;
import io.rxmicro.annotation.processor.data.component.DataGenerationContextBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryInterfaceSignatureBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryMethodSignatureBuilder;
import io.rxmicro.annotation.processor.data.component.EntityConverterBuilder;
import io.rxmicro.annotation.processor.data.component.impl.DataGenerationContextBuilderImpl;
import io.rxmicro.annotation.processor.data.sql.component.PlatformPlaceHolderGeneratorFactory;
import io.rxmicro.annotation.processor.data.sql.component.SQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.SQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.component.SQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.SelectSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.CustomSelectSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.PredefinedSelectSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.SelectSQLOperatorReader;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.operator.INSelectSQLOperatorReader;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.operator.OrderBySelectSQLOperatorReader;
import io.rxmicro.annotation.processor.data.sql.component.impl.resolver.DeleteSQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.component.impl.resolver.InsertSQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.component.impl.resolver.SelectSQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.component.impl.resolver.UpdateSQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLTokenParserRule;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedDeleteVariables;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedInsertVariables;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedSelectVariables;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedUpdateVariables;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.SQLEntityConverterBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.method.CreateTransactionSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.method.DeleteSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.method.InsertSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.method.SelectSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl.method.UpdateSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.PostgrePlatformPlaceHolderGeneratorFactory;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.PostgreSQLDataModelFieldBuilderImpl;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.PostgreSQLDataRepositoryMethodSignatureBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.PostgreSQLRepositoryClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.PostgreSQLTokenParserRuleProvider;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.PostgreSQLVariableContext;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.builder.DeletePostgreSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.builder.InsertPostgreSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.builder.UpdatePostgreSQLBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.method.PostgreSQLDeleteWithReturningSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.method.PostgreSQLInsertWithReturningSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.method.PostgreSQLUpdateWithReturningSQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.operation.Update;

import java.util.Set;

import static com.google.inject.multibindings.Multibinder.newSetBinder;
import static io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords.FETCH;
import static io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords.FOR;
import static io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords.LIMIT;
import static io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLKeywords.OFFSET;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.data.sql.SupportedVariables.ALL_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.BY_ID_FILTER;
import static io.rxmicro.data.sql.SupportedVariables.ID_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.INSERTED_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.ON_CONFLICT_UPDATE_NOT_ID_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.TABLE;
import static io.rxmicro.data.sql.SupportedVariables.UPDATED_COLUMNS;
import static io.rxmicro.data.sql.SupportedVariables.VALUES;

/**
 * @author nedis
 * @since 0.1
 */
public final class PostgreSQLDependenciesModule extends AbstractModule {

    private static final Set<String> SUPPORTED_SELECT_VARIABLES =
            unmodifiableOrderedSet("*", ALL_COLUMNS, TABLE, BY_ID_FILTER);

    private static final Set<String> SUPPORTED_INSERT_VARIABLES =
            unmodifiableOrderedSet(
                    TABLE, INSERTED_COLUMNS, VALUES, ID_COLUMNS,
                    ON_CONFLICT_UPDATE_NOT_ID_COLUMNS, "*", ALL_COLUMNS
            );

    private static final Set<String> SUPPORTED_DELETE_VARIABLES =
            unmodifiableOrderedSet(TABLE, BY_ID_FILTER, "*", ALL_COLUMNS);

    private static final Set<String> SUPPORTED_UPDATE_VARIABLES =
            unmodifiableOrderedSet(TABLE, UPDATED_COLUMNS, BY_ID_FILTER, "*", ALL_COLUMNS);

    private final PostgreSQLTokenParserRuleProvider postgreSQLTokenParserRuleProvider = new PostgreSQLTokenParserRuleProvider();

    @Override
    protected void configure() {
        bind(PlatformPlaceHolderGeneratorFactory.class)
                .to(PostgrePlatformPlaceHolderGeneratorFactory.class);
        bind(SupportedTypesProvider.class)
                .to(PostgreSQLSupportedTypesProvider.class);
        bind(TokenParserRule.class)
                .annotatedWith(SQLTokenParserRule.class)
                .toProvider(postgreSQLTokenParserRuleProvider::get);
        bind(VariableContext.class)
                .to(PostgreSQLVariableContext.class);
        bind(DataRepositoryMethodSignatureBuilder.class)
                .to(PostgreSQLDataRepositoryMethodSignatureBuilder.class);
        bind(DataRepositoryInterfaceSignatureBuilder.class)
                .to(PostgreSQLDataRepositoryInterfaceSignatureBuilder.class);
        bind(new TypeLiteral<ModelFieldBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(PostgreSQLDataModelFieldBuilderImpl.class);
        bind(new TypeLiteral<EntityConverterBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<SQLEntityConverterBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        bind(new TypeLiteral<DataClassStructureBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(PostgreSQLRepositoryClassStructureBuilderImpl.class);
        bind(new TypeLiteral<DataGenerationContextBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<DataGenerationContextBuilderImpl<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        bindSupportedVariables();
        bindMethodBodyBuilders();
        bindSelectInternalComponents();
        bindInsertInternalComponents();
        bindDeleteInternalComponents();
        bindUpdateInternalComponents();
    }

    private void bindSupportedVariables() {
        final TypeLiteral<Set<String>> supportedVariableTypeLiteral = new TypeLiteral<>() {
        };
        bind(supportedVariableTypeLiteral)
                .annotatedWith(SupportedSelectVariables.class)
                .toInstance(SUPPORTED_SELECT_VARIABLES);
        bind(supportedVariableTypeLiteral)
                .annotatedWith(SupportedInsertVariables.class)
                .toInstance(SUPPORTED_INSERT_VARIABLES);
        bind(supportedVariableTypeLiteral)
                .annotatedWith(SupportedDeleteVariables.class)
                .toInstance(SUPPORTED_DELETE_VARIABLES);
        bind(supportedVariableTypeLiteral)
                .annotatedWith(SupportedUpdateVariables.class)
                .toInstance(SUPPORTED_UPDATE_VARIABLES);
    }

    // SuppressWarnings(Convert2Diamond) fixes the bug: https://bugs.openjdk.java.net/browse/JDK-8203913
    @SuppressWarnings("Convert2Diamond")
    private void bindMethodBodyBuilders() {
        final Multibinder<SQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>> sqlOperationBinder =
                newSetBinder(
                        binder(),
                        new TypeLiteral<SQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                        }
                );
        sqlOperationBinder.addBinding()
                .to(new TypeLiteral<SelectSQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        sqlOperationBinder.addBinding()
                .to(new TypeLiteral<CreateTransactionSQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        sqlOperationBinder.addBinding()
                .to(PostgreSQLInsertWithReturningSQLRepositoryMethodModelBuilder.class);
        sqlOperationBinder.addBinding()
                .to(new TypeLiteral<InsertSQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        sqlOperationBinder.addBinding()
                .to(PostgreSQLDeleteWithReturningSQLRepositoryMethodModelBuilder.class);
        sqlOperationBinder.addBinding()
                .to(new TypeLiteral<DeleteSQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        sqlOperationBinder.addBinding()
                .to(PostgreSQLUpdateWithReturningSQLRepositoryMethodModelBuilder.class);
        sqlOperationBinder.addBinding()
                .to(new TypeLiteral<UpdateSQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
    }

    private void bindSelectInternalComponents() {
        bind(new TypeLiteral<SQLBuilder<Select, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<SelectSQLBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        bind(new TypeLiteral<SQLVariableValueResolver<Select, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<SelectSQLVariableValueResolver<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
        bind(new TypeLiteral<CustomSelectSQLBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        });
        bind(new TypeLiteral<PredefinedSelectSQLBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        });

        final Multibinder<SelectSQLOperatorReader> selectOperatorReaderBinder =
                newSetBinder(binder(), SelectSQLOperatorReader.class);
        selectOperatorReaderBinder.addBinding()
                .to(INSelectSQLOperatorReader.class);
        selectOperatorReaderBinder.addBinding()
                .toInstance(new OrderBySelectSQLOperatorReader(Set.of(LIMIT, OFFSET, FETCH, FOR)));
    }

    private void bindInsertInternalComponents() {
        bind(new TypeLiteral<SQLBuilder<Insert, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(InsertPostgreSQLBuilder.class);
        bind(new TypeLiteral<SQLVariableValueResolver<Insert, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<InsertSQLVariableValueResolver<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
    }

    private void bindDeleteInternalComponents() {
        bind(new TypeLiteral<SQLBuilder<Delete, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(DeletePostgreSQLBuilder.class);
        bind(new TypeLiteral<SQLVariableValueResolver<Delete, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<DeleteSQLVariableValueResolver<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
    }

    private void bindUpdateInternalComponents() {
        bind(new TypeLiteral<SQLBuilder<Update, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(UpdatePostgreSQLBuilder.class);
        bind(new TypeLiteral<SQLVariableValueResolver<Update, SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
        })
                .to(new TypeLiteral<UpdateSQLVariableValueResolver<SQLDataModelField, PostgreSQLDataObjectModelClass>>() {
                });
    }
}
