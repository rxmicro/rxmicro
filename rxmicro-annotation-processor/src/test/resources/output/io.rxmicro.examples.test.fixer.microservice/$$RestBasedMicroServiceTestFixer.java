package rxmicro;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.RuntimeConstants.RX_MICRO_RUNTIME_MODULE;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RestBasedMicroServiceTestFixer {

    static {
        final Module currentModule = $$RestBasedMicroServiceTestFixer.class.getModule();
        currentModule.addExports("rxmicro", RX_MICRO_RUNTIME_MODULE);
    }

    public $$RestBasedMicroServiceTestFixer() {
        final Module currentModule = getClass().getModule();
        if (currentModule.isNamed()) {
            final Module unNamedModule = getClass().getClassLoader().getUnnamedModule();
            currentModule.getPackages().forEach(p -> {
                currentModule.addOpens(p, unNamedModule);
                System.out.println(format("opens ?/? to ALL-UNNAMED", currentModule.getName(), p));
            });
        }
    }
}
