# TODO Improvements

* Converters and Validators must support an inheritance.
* Detect custom config classes automatically and add required exports to EnvironmentCustomizer.
* BeanSupplier must be implemented more efficient: It is necessary to set only one valid qualifier instead of an array of possible qualifiers.
* ResponseModelToJsonConverter can convert a model to byte array without using ExchangeDataFormatConverter.
* Annotation processor must generate request handler without HttpHeaders parameter for empty static headers.
* Annotation processor must validate method variable names for generated method body.
  Annotation processor must show error if user will use reserved variable name.  
  (For example if mongo repository method contains parameter 'filter', a compilation will be fail!)
* Remove unused Mongo and Postgres codecs.
