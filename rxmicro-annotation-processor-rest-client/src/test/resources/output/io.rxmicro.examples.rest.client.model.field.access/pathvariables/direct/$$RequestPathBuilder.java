package io.rxmicro.examples.rest.client.model.field.access.pathvariables.direct;

import io.rxmicro.rest.client.detail.PathBuilder;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestPathBuilder extends PathBuilder<Request> {

    @Override
    public String build(final String key,
                        final String urlTemplate,
                        final Request model) {
        // /path-variables/direct/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}
        return format(urlTemplate, model.booleanPathVariable, model.bytePathVariable, model.shortPathVariable, model.intPathVariable, model.longPathVariable, model.bigIntegerPathVariable, model.floatPathVariable, model.charPathVariable, model.doublePathVariable, model.decimalPathVariable, model.charPathVariable, model.stringPathVariable, model.instantPathVariable, model.enumPathVariable);
    }
}
