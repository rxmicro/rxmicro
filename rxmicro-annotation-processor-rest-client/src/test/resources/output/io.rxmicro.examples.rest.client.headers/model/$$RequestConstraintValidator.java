package io.rxmicro.examples.rest.client.headers.model;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestConstraintValidator implements ConstraintValidator<Request> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    @Override
    public void validateNonNull(final Request model,
                                final ModelType httpModelType,
                                final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.endpointVersion, HttpModelType.HEADER, "Endpoint-Version");

        requiredConstraintValidator.validate(model.useProxy, HttpModelType.HEADER, "UseProxy");
    }
}