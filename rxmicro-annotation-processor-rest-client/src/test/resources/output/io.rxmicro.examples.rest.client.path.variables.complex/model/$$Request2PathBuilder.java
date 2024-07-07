package io.rxmicro.examples.rest.client.path.variables.complex.model;

import io.rxmicro.rest.client.detail.PathBuilder;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$Request2PathBuilder extends PathBuilder<Request2> {

    @Override
    public String build(final String key,
                        final String urlTemplate,
                        final Request2 model) {
        // /${a}/${b}
        return format(urlTemplate, model.a, model.b);
    }
}
