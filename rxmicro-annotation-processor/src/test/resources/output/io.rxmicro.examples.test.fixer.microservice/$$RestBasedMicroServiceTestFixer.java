package rxmicro;

import java.util.Set;

import static io.rxmicro.common.CommonConstants.RX_MICRO_COMMON_MODULE;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.common.util.TestLoggers.logInfoTestMessage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RestBasedMicroServiceTestFixer {

    static {
        final Module currentModule = $$RestBasedMicroServiceTestFixer.class.getModule();
        currentModule.addExports("rxmicro", RX_MICRO_COMMON_MODULE);
    }

    public $$RestBasedMicroServiceTestFixer() {
        final Module currentModule = getClass().getModule();
        if (currentModule.isNamed()) {
            logInfoTestMessage("Fix the environment for REST based microservice test(s)...");
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
