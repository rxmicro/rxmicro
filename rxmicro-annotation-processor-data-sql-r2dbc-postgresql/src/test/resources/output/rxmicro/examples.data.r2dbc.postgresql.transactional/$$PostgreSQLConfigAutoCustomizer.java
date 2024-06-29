package rxmicro.examples.data.r2dbc.postgresql.transactional;

import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.PostgreSQLConfigAutoCustomizer;
import io.rxmicro.examples.data.r2dbc.postgresql.transactional.model.Role;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PostgreSQLConfigAutoCustomizer extends PostgreSQLConfigAutoCustomizer {

    static {
        customizeTheCurrentPostgreSQLConfig(new $$PostgreSQLConfigAutoCustomizer());
    }

    public static void customize() {
        //do nothing. All customization is done at the static section
    }

    private $$PostgreSQLConfigAutoCustomizer() {
    }

    @Override
    public Map<String, Class<? extends Enum<?>>> getEnumMapping() {
        return Map.of(
                "role", Role.class,
                "isolation_level", IsolationLevel.class
        );
    }
}
