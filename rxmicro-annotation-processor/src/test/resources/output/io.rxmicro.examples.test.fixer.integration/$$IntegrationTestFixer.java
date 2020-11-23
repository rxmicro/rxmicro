package rxmicro;

import java.util.Set;

import static io.rxmicro.common.CommonConstants.RX_MICRO_COMMON_MODULE;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.common.util.TestLoggers.logInfoTestMessage;
import static io.rxmicro.runtime.RuntimeConstants.RX_MICRO_RUNTIME_MODULE;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$IntegrationTestFixer {

    static {
        final Module currentModule = $$IntegrationTestFixer.class.getModule();
        currentModule.addExports("rxmicro", RX_MICRO_RUNTIME_MODULE);
    }

    public $$IntegrationTestFixer() {
        final Module currentModule = getClass().getModule();
        if (currentModule.isNamed()) {
            logInfoTestMessage("Fix the environment for integration test(s)...");
            final Module unnamedModule = getClass().getClassLoader().getUnnamedModule();
            final Set<Module> modules = unmodifiableOrderedSet(unnamedModule, RX_MICRO_COMMON_MODULE);
            for (final Module module : modules) {
                for (final String packageName : currentModule.getPackages()) {
                    currentModule.addOpens(packageName, module);
                    logInfoTestMessage(
                            "opens ?/? to ?",
                            currentModule.getName(),
                            packageName,
                            module.isNamed() ? module.getName() : "ALL-UNNAMED"
                    );
                }
            }
        }
    }
}
