package io.rxmicro.examples.rest.client.path.variables;

import io.rxmicro.rest.client.detail.PathBuilder;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRESTRequestPathBuilder extends PathBuilder<$$VirtualRESTRequest> {

    @Override
    public String build(final String key,
                        final String urlTemplate,
                        final $$VirtualRESTRequest model) {
        // /${category}/${class}-${subType}
        return format(urlTemplate, model.category, model.type, model.subType);
    }
}