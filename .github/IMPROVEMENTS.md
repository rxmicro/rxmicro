# TODO Improvements

* Converters and Validators must support an inheritance.
* BeanSupplier must be implemented more efficient: It is necessary to set only one valid qualifier instead of an array of possible qualifiers.
* ResponseModelToJsonConverter can convert a model to byte array without using ExchangeDataFormatConverter.
* Annotation processor must generate request handler without HttpHeaders parameter for empty static headers.
* Annotation processor must validate method variable names for generated method body.
  Annotation processor must show error if user will use reserved variable name.  
  (For example if mongo repository method contains parameter 'filter', a compilation will be fail!)
* Remove unused Mongo and Postgres codecs.
* If model contains object array, annotation processor generates inefficient code:
  `requiredConstraintValidator.validateIterable(model.nestedList, HttpModelType.PARAMETER, "nestedList");`
  `nestedConstraintValidator.validateIterable(model.nestedList, HttpModelType.PARAMETER, "nestedList");` 
  nested validator must validate not null array item instead of additional required validator:
  `nestedConstraintValidator.validateIterable(model.nestedList, HttpModelType.PARAMETER, "nestedList");`
  nested validator content:
  ```
  @Override
  public void validate(final Nested model,
                       final HttpModelType httpModelType,
                       final String name) {
    requiredConstraintValidator.validate(model, HttpModelType.PARAMETER, "nested item"); // !!!!!!!!!!!!!!!!!!!
    requiredAndNotEmptyStringConstraintValidator.validate(model.phone, HttpModelType.PARAMETER, "phone");
    phonePhonePhoneConstraintValidator.validate(model.phone, HttpModelType.PARAMETER, "phone");
  }
  ```
* Add validators for redundant HTTP headers and params  
