package io.rxmicro.examples.validation.client.all.types;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.EmailConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.ValidatorPool.getStatelessValidator;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualREST2Request4ConstraintValidator implements ConstraintValidator<$$VirtualREST2Request4> {

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    private final EmailConstraintValidator emailConstraintValidator =
            getStatelessValidator(EmailConstraintValidator.class);

    @Override
    public void validate(final $$VirtualREST2Request4 model,
                         final HttpModelType httpModelType,
                         final String name) throws ValidationException {
        requiredConstraintValidator.validate(model.email, HttpModelType.parameter, "email");
        emailConstraintValidator.validate(model.email, HttpModelType.parameter, "email");
    }
}
