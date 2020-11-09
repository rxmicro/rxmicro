package io.rxmicro.examples.validation.server.all.standard;

import io.rxmicro.examples.validation.server.all.standard.model.Color;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.LocationAccuracy;
import io.rxmicro.validation.constraint.Base64URLEncoded.Alphabet;
import io.rxmicro.validation.constraint.CountryCode.Format;
import io.rxmicro.validation.constraint.IP.Version;
import io.rxmicro.validation.constraint.TruncatedTime.Truncated;
import io.rxmicro.validation.validator.AssertFalseConstraintValidator;
import io.rxmicro.validation.validator.AssertTrueConstraintValidator;
import io.rxmicro.validation.validator.Base64URLEncodedConstraintValidator;
import io.rxmicro.validation.validator.CountryCodeConstraintValidator;
import io.rxmicro.validation.validator.EmailConstraintValidator;
import io.rxmicro.validation.validator.EnumerationCharacterConstraintValidator;
import io.rxmicro.validation.validator.EnumerationStringConstraintValidator;
import io.rxmicro.validation.validator.FutureInstantConstraintValidator;
import io.rxmicro.validation.validator.FutureOrPresentInstantConstraintValidator;
import io.rxmicro.validation.validator.HostNameConstraintValidator;
import io.rxmicro.validation.validator.IPConstraintValidator;
import io.rxmicro.validation.validator.LatConstraintValidator;
import io.rxmicro.validation.validator.LatinAlphabetOnlyConstraintValidator;
import io.rxmicro.validation.validator.LengthConstraintValidator;
import io.rxmicro.validation.validator.LngConstraintValidator;
import io.rxmicro.validation.validator.LowercaseConstraintValidator;
import io.rxmicro.validation.validator.MaxBigDecimalNumberConstraintValidator;
import io.rxmicro.validation.validator.MaxBigIntegerNumberConstraintValidator;
import io.rxmicro.validation.validator.MaxByteConstraintValidator;
import io.rxmicro.validation.validator.MaxDoubleConstraintValidator;
import io.rxmicro.validation.validator.MaxFloatConstraintValidator;
import io.rxmicro.validation.validator.MaxIntConstraintValidator;
import io.rxmicro.validation.validator.MaxLengthConstraintValidator;
import io.rxmicro.validation.validator.MaxLongConstraintValidator;
import io.rxmicro.validation.validator.MaxShortConstraintValidator;
import io.rxmicro.validation.validator.MaxSizeListConstraintValidator;
import io.rxmicro.validation.validator.MaxSizeMapConstraintValidator;
import io.rxmicro.validation.validator.MaxSizeSetConstraintValidator;
import io.rxmicro.validation.validator.MinBigDecimalNumberConstraintValidator;
import io.rxmicro.validation.validator.MinBigIntegerNumberConstraintValidator;
import io.rxmicro.validation.validator.MinByteConstraintValidator;
import io.rxmicro.validation.validator.MinDoubleConstraintValidator;
import io.rxmicro.validation.validator.MinFloatConstraintValidator;
import io.rxmicro.validation.validator.MinIntConstraintValidator;
import io.rxmicro.validation.validator.MinLengthConstraintValidator;
import io.rxmicro.validation.validator.MinLongConstraintValidator;
import io.rxmicro.validation.validator.MinShortConstraintValidator;
import io.rxmicro.validation.validator.MinSizeListConstraintValidator;
import io.rxmicro.validation.validator.MinSizeMapConstraintValidator;
import io.rxmicro.validation.validator.MinSizeSetConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.NumericConstraintValidator;
import io.rxmicro.validation.validator.PastInstantConstraintValidator;
import io.rxmicro.validation.validator.PastOrPresentInstantConstraintValidator;
import io.rxmicro.validation.validator.PatternConstraintValidator;
import io.rxmicro.validation.validator.PhoneConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;
import io.rxmicro.validation.validator.RequiredListConstraintValidator;
import io.rxmicro.validation.validator.RequiredMapConstraintValidator;
import io.rxmicro.validation.validator.RequiredSetConstraintValidator;
import io.rxmicro.validation.validator.SizeListConstraintValidator;
import io.rxmicro.validation.validator.SizeMapConstraintValidator;
import io.rxmicro.validation.validator.SizeSetConstraintValidator;
import io.rxmicro.validation.validator.SkypeConstraintValidator;
import io.rxmicro.validation.validator.SubEnumConstraintValidator;
import io.rxmicro.validation.validator.TelegramConstraintValidator;
import io.rxmicro.validation.validator.TruncatedTimeInstantConstraintValidator;
import io.rxmicro.validation.validator.URIConstraintValidator;
import io.rxmicro.validation.validator.URLEncodedConstraintValidator;
import io.rxmicro.validation.validator.UniqueItemsListConstraintValidator;
import io.rxmicro.validation.validator.UppercaseConstraintValidator;
import io.rxmicro.validation.validator.ViberConstraintValidator;
import io.rxmicro.validation.validator.WhatsAppConstraintValidator;

import java.util.List;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRequestConstraintValidator implements ConstraintValidator<$$VirtualRequest> {

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    private final AssertFalseConstraintValidator assertFalseConstraintValidator =
            getStatelessValidator(AssertFalseConstraintValidator.class);

    private final AssertTrueConstraintValidator assertTrueConstraintValidator =
            getStatelessValidator(AssertTrueConstraintValidator.class);

    private final LowercaseConstraintValidator lowercaseConstraintValidator =
            getStatelessValidator(LowercaseConstraintValidator.class);

    private final SkypeConstraintValidator skypeConstraintValidator =
            getStatelessValidator(SkypeConstraintValidator.class);

    private final URIConstraintValidator uRIConstraintValidator =
            getStatelessValidator(URIConstraintValidator.class);

    private final URLEncodedConstraintValidator uRLEncodedConstraintValidator =
            getStatelessValidator(URLEncodedConstraintValidator.class);

    private final UppercaseConstraintValidator uppercaseConstraintValidator =
            getStatelessValidator(UppercaseConstraintValidator.class);

    private final FutureInstantConstraintValidator futureInstantConstraintValidator =
            getStatelessValidator(FutureInstantConstraintValidator.class);

    private final FutureOrPresentInstantConstraintValidator futureOrPresentInstantConstraintValidator =
            getStatelessValidator(FutureOrPresentInstantConstraintValidator.class);

    private final PastInstantConstraintValidator pastInstantConstraintValidator =
            getStatelessValidator(PastInstantConstraintValidator.class);

    private final PastOrPresentInstantConstraintValidator pastOrPresentInstantConstraintValidator =
            getStatelessValidator(PastOrPresentInstantConstraintValidator.class);

    private final RequiredListConstraintValidator requiredListConstraintValidator =
            getStatelessValidator(RequiredListConstraintValidator.class);

    private final UniqueItemsListConstraintValidator uniqueItemsListConstraintValidator =
            getStatelessValidator(UniqueItemsListConstraintValidator.class);

    private final RequiredSetConstraintValidator requiredSetConstraintValidator =
            getStatelessValidator(RequiredSetConstraintValidator.class);

    private final RequiredMapConstraintValidator requiredMapConstraintValidator =
            getStatelessValidator(RequiredMapConstraintValidator.class);

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final MaxByteConstraintValidator byteParameterMaxIntMaxByteConstraintValidator =
            new MaxByteConstraintValidator(10L, true);

    private final MaxByteConstraintValidator byteParameterMaxNumberMaxByteConstraintValidator =
            new MaxByteConstraintValidator("10", true);

    private final MinByteConstraintValidator byteParameterMinIntMinByteConstraintValidator =
            new MinByteConstraintValidator(3L, true);

    private final MinByteConstraintValidator byteParameterMinNumberMinByteConstraintValidator =
            new MinByteConstraintValidator("3", true);

    private final MaxShortConstraintValidator shortParameterMaxIntMaxShortConstraintValidator =
            new MaxShortConstraintValidator(10L, true);

    private final MaxShortConstraintValidator shortParameterMaxNumberMaxShortConstraintValidator =
            new MaxShortConstraintValidator("10", true);

    private final MinShortConstraintValidator shortParameterMinIntMinShortConstraintValidator =
            new MinShortConstraintValidator(3L, true);

    private final MinShortConstraintValidator shortParameterMinNumberMinShortConstraintValidator =
            new MinShortConstraintValidator("3", true);

    private final MaxIntConstraintValidator intParameterMaxIntMaxIntConstraintValidator =
            new MaxIntConstraintValidator(10L, true);

    private final MaxIntConstraintValidator intParameterMaxNumberMaxIntConstraintValidator =
            new MaxIntConstraintValidator("10", true);

    private final MinIntConstraintValidator intParameterMinIntMinIntConstraintValidator =
            new MinIntConstraintValidator(3L, true);

    private final MinIntConstraintValidator intParameterMinNumberMinIntConstraintValidator =
            new MinIntConstraintValidator("3", true);

    private final MaxLongConstraintValidator longParameterMaxIntMaxLongConstraintValidator =
            new MaxLongConstraintValidator(10L, true);

    private final MaxLongConstraintValidator longParameterMaxNumberMaxLongConstraintValidator =
            new MaxLongConstraintValidator("10", true);

    private final MinLongConstraintValidator longParameterMinIntMinLongConstraintValidator =
            new MinLongConstraintValidator(3L, true);

    private final MinLongConstraintValidator longParameterMinNumberMinLongConstraintValidator =
            new MinLongConstraintValidator("3", true);

    private final MaxBigIntegerNumberConstraintValidator bigIntParameterMaxNumberMaxBigIntegerNumberConstraintValidator =
            new MaxBigIntegerNumberConstraintValidator("10", true);

    private final MinBigIntegerNumberConstraintValidator bigIntParameterMinNumberMinBigIntegerNumberConstraintValidator =
            new MinBigIntegerNumberConstraintValidator("3", true);

    private final MaxFloatConstraintValidator floatParameterMaxDoubleMaxFloatConstraintValidator =
            new MaxFloatConstraintValidator(10.9);

    private final MaxFloatConstraintValidator floatParameterMaxNumberMaxFloatConstraintValidator =
            new MaxFloatConstraintValidator("10", true);

    private final MinFloatConstraintValidator floatParameterMinDoubleMinFloatConstraintValidator =
            new MinFloatConstraintValidator(3.1);

    private final MinFloatConstraintValidator floatParameterMinNumberMinFloatConstraintValidator =
            new MinFloatConstraintValidator("3", true);

    private final MaxDoubleConstraintValidator doubleParameterMaxDoubleMaxDoubleConstraintValidator =
            new MaxDoubleConstraintValidator(10.9);

    private final MaxDoubleConstraintValidator doubleParameterMaxNumberMaxDoubleConstraintValidator =
            new MaxDoubleConstraintValidator("10", true);

    private final MinDoubleConstraintValidator doubleParameterMinDoubleMinDoubleConstraintValidator =
            new MinDoubleConstraintValidator(3.1);

    private final MinDoubleConstraintValidator doubleParameterMinNumberMinDoubleConstraintValidator =
            new MinDoubleConstraintValidator("3", true);

    private final LatConstraintValidator decimalParameterLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final LngConstraintValidator decimalParameterLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final MaxBigDecimalNumberConstraintValidator decimalParameterMaxNumberMaxBigDecimalNumberConstraintValidator =
            new MaxBigDecimalNumberConstraintValidator("10.9", true);

    private final MinBigDecimalNumberConstraintValidator decimalParameterMinNumberMinBigDecimalNumberConstraintValidator =
            new MinBigDecimalNumberConstraintValidator("3.1", true);

    private final NumericConstraintValidator decimalParameterNumericNumericConstraintValidator =
            new NumericConstraintValidator(5, 2);

    private final EnumerationCharacterConstraintValidator charParameterEnumerationEnumerationCharacterConstraintValidator =
            new EnumerationCharacterConstraintValidator(List.of("y", "n"));

    private final Base64URLEncodedConstraintValidator stringParameterBase64URLEncodedBase64URLEncodedConstraintValidator =
            new Base64URLEncodedConstraintValidator(Alphabet.URL);

    private final CountryCodeConstraintValidator stringParameterCountryCodeCountryCodeConstraintValidator =
            new CountryCodeConstraintValidator(Format.ISO_3166_1_ALPHA_2);

    private final EmailConstraintValidator stringParameterEmailEmailConstraintValidator =
            new EmailConstraintValidator(false);

    private final EnumerationStringConstraintValidator stringParameterEnumerationEnumerationStringConstraintValidator =
            new EnumerationStringConstraintValidator(List.of("3", "2", "3"));

    private final HostNameConstraintValidator stringParameterHostNameHostNameConstraintValidator =
            new HostNameConstraintValidator(false);

    private final IPConstraintValidator stringParameterIPIPConstraintValidator =
            new IPConstraintValidator(List.of(Version.IP_V4, Version.IP_V6));

    private final LatinAlphabetOnlyConstraintValidator stringParameterLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator =
            new LatinAlphabetOnlyConstraintValidator(true, true, true, "~!@#$%^&*()_+=-[]{},.;:<>?/\\\"' \t|\r\n\b");

    private final LengthConstraintValidator stringParameterLengthLengthConstraintValidator =
            new LengthConstraintValidator(2);

    private final MaxLengthConstraintValidator stringParameterMaxLengthMaxLengthConstraintValidator =
            new MaxLengthConstraintValidator(2, true);

    private final MinLengthConstraintValidator stringParameterMinLengthMinLengthConstraintValidator =
            new MinLengthConstraintValidator(2, true);

    private final PatternConstraintValidator stringParameterPatternPatternConstraintValidator =
            new PatternConstraintValidator("hello", List.of());

    private final PhoneConstraintValidator stringParameterPhonePhoneConstraintValidator =
            new PhoneConstraintValidator(true, false);

    private final TelegramConstraintValidator stringParameterTelegramTelegramConstraintValidator =
            new TelegramConstraintValidator(true, false);

    private final ViberConstraintValidator stringParameterViberViberConstraintValidator =
            new ViberConstraintValidator(true, false);

    private final WhatsAppConstraintValidator stringParameterWhatsAppWhatsAppConstraintValidator =
            new WhatsAppConstraintValidator(true, false);

    private final TruncatedTimeInstantConstraintValidator instantParameterTruncatedTimeTruncatedTimeInstantConstraintValidator =
            new TruncatedTimeInstantConstraintValidator(Truncated.MILLIS);

    private final SubEnumConstraintValidator colorParameterSubEnumSubEnumConstraintValidator =
            new SubEnumConstraintValidator(Color.class, List.of("RED", "BLUE"), List.of());

    private final MaxSizeListConstraintValidator booleanValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinSizeListConstraintValidator booleanValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator booleanValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final MaxByteConstraintValidator byteValuesMaxIntMaxByteConstraintValidator =
            new MaxByteConstraintValidator(10L, true);

    private final MaxSizeListConstraintValidator byteValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinByteConstraintValidator byteValuesMinIntMinByteConstraintValidator =
            new MinByteConstraintValidator(3L, true);

    private final MinSizeListConstraintValidator byteValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator byteValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final MaxShortConstraintValidator shortValuesMaxIntMaxShortConstraintValidator =
            new MaxShortConstraintValidator(10L, true);

    private final MaxSizeListConstraintValidator shortValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinShortConstraintValidator shortValuesMinIntMinShortConstraintValidator =
            new MinShortConstraintValidator(3L, true);

    private final MinSizeListConstraintValidator shortValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator shortValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final MaxIntConstraintValidator intValuesMaxIntMaxIntConstraintValidator =
            new MaxIntConstraintValidator(10L, true);

    private final MaxSizeListConstraintValidator intValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinIntConstraintValidator intValuesMinIntMinIntConstraintValidator =
            new MinIntConstraintValidator(3L, true);

    private final MinSizeListConstraintValidator intValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator intValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final MaxLongConstraintValidator longValuesMaxIntMaxLongConstraintValidator =
            new MaxLongConstraintValidator(10L, true);

    private final MaxSizeListConstraintValidator longValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinLongConstraintValidator longValuesMinIntMinLongConstraintValidator =
            new MinLongConstraintValidator(3L, true);

    private final MinSizeListConstraintValidator longValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator longValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final EnumerationCharacterConstraintValidator charValuesEnumerationEnumerationCharacterConstraintValidator =
            new EnumerationCharacterConstraintValidator(List.of("y", "n"));

    private final MaxSizeListConstraintValidator charValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinSizeListConstraintValidator charValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator charValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final MaxFloatConstraintValidator floatValuesMaxDoubleMaxFloatConstraintValidator =
            new MaxFloatConstraintValidator(10.9);

    private final MaxSizeListConstraintValidator floatValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinFloatConstraintValidator floatValuesMinDoubleMinFloatConstraintValidator =
            new MinFloatConstraintValidator(3.1);

    private final MinSizeListConstraintValidator floatValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator floatValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final MaxDoubleConstraintValidator doubleValuesMaxDoubleMaxDoubleConstraintValidator =
            new MaxDoubleConstraintValidator(10.9);

    private final MaxSizeListConstraintValidator doubleValuesMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinDoubleConstraintValidator doubleValuesMinDoubleMinDoubleConstraintValidator =
            new MinDoubleConstraintValidator(3.1);

    private final MinSizeListConstraintValidator doubleValuesMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator doubleValuesSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final LatConstraintValidator decimalsLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final LngConstraintValidator decimalsLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final MaxBigDecimalNumberConstraintValidator decimalsMaxNumberMaxBigDecimalNumberConstraintValidator =
            new MaxBigDecimalNumberConstraintValidator("10.9", true);

    private final MaxSizeListConstraintValidator decimalsMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinBigDecimalNumberConstraintValidator decimalsMinNumberMinBigDecimalNumberConstraintValidator =
            new MinBigDecimalNumberConstraintValidator("3.1", true);

    private final MinSizeListConstraintValidator decimalsMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final NumericConstraintValidator decimalsNumericNumericConstraintValidator =
            new NumericConstraintValidator(5, 2);

    private final SizeListConstraintValidator decimalsSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final MaxBigIntegerNumberConstraintValidator bigIntegersMaxNumberMaxBigIntegerNumberConstraintValidator =
            new MaxBigIntegerNumberConstraintValidator("10", true);

    private final MaxSizeListConstraintValidator bigIntegersMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinBigIntegerNumberConstraintValidator bigIntegersMinNumberMinBigIntegerNumberConstraintValidator =
            new MinBigIntegerNumberConstraintValidator("3", true);

    private final MinSizeListConstraintValidator bigIntegersMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator bigIntegersSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final Base64URLEncodedConstraintValidator stringsBase64URLEncodedBase64URLEncodedConstraintValidator =
            new Base64URLEncodedConstraintValidator(Alphabet.URL);

    private final CountryCodeConstraintValidator stringsCountryCodeCountryCodeConstraintValidator =
            new CountryCodeConstraintValidator(Format.ISO_3166_1_ALPHA_2);

    private final EmailConstraintValidator stringsEmailEmailConstraintValidator =
            new EmailConstraintValidator(false);

    private final EnumerationStringConstraintValidator stringsEnumerationEnumerationStringConstraintValidator =
            new EnumerationStringConstraintValidator(List.of("3", "2", "3"));

    private final HostNameConstraintValidator stringsHostNameHostNameConstraintValidator =
            new HostNameConstraintValidator(false);

    private final IPConstraintValidator stringsIPIPConstraintValidator =
            new IPConstraintValidator(List.of(Version.IP_V4, Version.IP_V6));

    private final LatinAlphabetOnlyConstraintValidator stringsLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator =
            new LatinAlphabetOnlyConstraintValidator(true, true, true, "~!@#$%^&*()_+=-[]{},.;:<>?/\\\"' \t|\r\n\b");

    private final LengthConstraintValidator stringsLengthLengthConstraintValidator =
            new LengthConstraintValidator(2);

    private final MaxLengthConstraintValidator stringsMaxLengthMaxLengthConstraintValidator =
            new MaxLengthConstraintValidator(2, true);

    private final MaxSizeListConstraintValidator stringsMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinLengthConstraintValidator stringsMinLengthMinLengthConstraintValidator =
            new MinLengthConstraintValidator(2, true);

    private final MinSizeListConstraintValidator stringsMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final PatternConstraintValidator stringsPatternPatternConstraintValidator =
            new PatternConstraintValidator("hello", List.of());

    private final PhoneConstraintValidator stringsPhonePhoneConstraintValidator =
            new PhoneConstraintValidator(true, false);

    private final SizeListConstraintValidator stringsSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final TelegramConstraintValidator stringsTelegramTelegramConstraintValidator =
            new TelegramConstraintValidator(true, false);

    private final ViberConstraintValidator stringsViberViberConstraintValidator =
            new ViberConstraintValidator(true, false);

    private final WhatsAppConstraintValidator stringsWhatsAppWhatsAppConstraintValidator =
            new WhatsAppConstraintValidator(true, false);

    private final MaxSizeListConstraintValidator instantsMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinSizeListConstraintValidator instantsMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator instantsSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final TruncatedTimeInstantConstraintValidator instantsTruncatedTimeTruncatedTimeInstantConstraintValidator =
            new TruncatedTimeInstantConstraintValidator(Truncated.MILLIS);

    private final MaxSizeListConstraintValidator colorsMaxSizeMaxSizeListConstraintValidator =
            new MaxSizeListConstraintValidator(50, true);

    private final MinSizeListConstraintValidator colorsMinSizeMinSizeListConstraintValidator =
            new MinSizeListConstraintValidator(2, true);

    private final SizeListConstraintValidator colorsSizeSizeListConstraintValidator =
            new SizeListConstraintValidator(12);

    private final SubEnumConstraintValidator colorsSubEnumSubEnumConstraintValidator =
            new SubEnumConstraintValidator(Color.class, List.of("RED", "BLUE"), List.of());

    private final MaxSizeSetConstraintValidator booleanSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinSizeSetConstraintValidator booleanSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator booleanSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final MaxByteConstraintValidator byteSetMaxIntMaxByteConstraintValidator =
            new MaxByteConstraintValidator(10L, true);

    private final MaxSizeSetConstraintValidator byteSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinByteConstraintValidator byteSetMinIntMinByteConstraintValidator =
            new MinByteConstraintValidator(3L, true);

    private final MinSizeSetConstraintValidator byteSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator byteSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final MaxShortConstraintValidator shortSetMaxIntMaxShortConstraintValidator =
            new MaxShortConstraintValidator(10L, true);

    private final MaxSizeSetConstraintValidator shortSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinShortConstraintValidator shortSetMinIntMinShortConstraintValidator =
            new MinShortConstraintValidator(3L, true);

    private final MinSizeSetConstraintValidator shortSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator shortSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final MaxIntConstraintValidator intSetMaxIntMaxIntConstraintValidator =
            new MaxIntConstraintValidator(10L, true);

    private final MaxSizeSetConstraintValidator intSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinIntConstraintValidator intSetMinIntMinIntConstraintValidator =
            new MinIntConstraintValidator(3L, true);

    private final MinSizeSetConstraintValidator intSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator intSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final MaxLongConstraintValidator longSetMaxIntMaxLongConstraintValidator =
            new MaxLongConstraintValidator(10L, true);

    private final MaxSizeSetConstraintValidator longSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinLongConstraintValidator longSetMinIntMinLongConstraintValidator =
            new MinLongConstraintValidator(3L, true);

    private final MinSizeSetConstraintValidator longSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator longSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final EnumerationCharacterConstraintValidator charSetEnumerationEnumerationCharacterConstraintValidator =
            new EnumerationCharacterConstraintValidator(List.of("y", "n"));

    private final MaxSizeSetConstraintValidator charSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinSizeSetConstraintValidator charSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator charSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final MaxFloatConstraintValidator floatSetMaxDoubleMaxFloatConstraintValidator =
            new MaxFloatConstraintValidator(10.9);

    private final MaxSizeSetConstraintValidator floatSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinFloatConstraintValidator floatSetMinDoubleMinFloatConstraintValidator =
            new MinFloatConstraintValidator(3.1);

    private final MinSizeSetConstraintValidator floatSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator floatSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final MaxDoubleConstraintValidator doubleSetMaxDoubleMaxDoubleConstraintValidator =
            new MaxDoubleConstraintValidator(10.9);

    private final MaxSizeSetConstraintValidator doubleSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinDoubleConstraintValidator doubleSetMinDoubleMinDoubleConstraintValidator =
            new MinDoubleConstraintValidator(3.1);

    private final MinSizeSetConstraintValidator doubleSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator doubleSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final LatConstraintValidator decimalSetLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final LngConstraintValidator decimalSetLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final MaxBigDecimalNumberConstraintValidator decimalSetMaxNumberMaxBigDecimalNumberConstraintValidator =
            new MaxBigDecimalNumberConstraintValidator("10.9", true);

    private final MaxSizeSetConstraintValidator decimalSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinBigDecimalNumberConstraintValidator decimalSetMinNumberMinBigDecimalNumberConstraintValidator =
            new MinBigDecimalNumberConstraintValidator("3.1", true);

    private final MinSizeSetConstraintValidator decimalSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final NumericConstraintValidator decimalSetNumericNumericConstraintValidator =
            new NumericConstraintValidator(5, 2);

    private final SizeSetConstraintValidator decimalSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final MaxBigIntegerNumberConstraintValidator bigIntegerSetMaxNumberMaxBigIntegerNumberConstraintValidator =
            new MaxBigIntegerNumberConstraintValidator("10", true);

    private final MaxSizeSetConstraintValidator bigIntegerSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinBigIntegerNumberConstraintValidator bigIntegerSetMinNumberMinBigIntegerNumberConstraintValidator =
            new MinBigIntegerNumberConstraintValidator("3", true);

    private final MinSizeSetConstraintValidator bigIntegerSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator bigIntegerSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final Base64URLEncodedConstraintValidator stringSetBase64URLEncodedBase64URLEncodedConstraintValidator =
            new Base64URLEncodedConstraintValidator(Alphabet.URL);

    private final CountryCodeConstraintValidator stringSetCountryCodeCountryCodeConstraintValidator =
            new CountryCodeConstraintValidator(Format.ISO_3166_1_ALPHA_2);

    private final EmailConstraintValidator stringSetEmailEmailConstraintValidator =
            new EmailConstraintValidator(false);

    private final EnumerationStringConstraintValidator stringSetEnumerationEnumerationStringConstraintValidator =
            new EnumerationStringConstraintValidator(List.of("3", "2", "3"));

    private final HostNameConstraintValidator stringSetHostNameHostNameConstraintValidator =
            new HostNameConstraintValidator(false);

    private final IPConstraintValidator stringSetIPIPConstraintValidator =
            new IPConstraintValidator(List.of(Version.IP_V4, Version.IP_V6));

    private final LatinAlphabetOnlyConstraintValidator stringSetLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator =
            new LatinAlphabetOnlyConstraintValidator(true, true, true, "~!@#$%^&*()_+=-[]{},.;:<>?/\\\"' \t|\r\n\b");

    private final LengthConstraintValidator stringSetLengthLengthConstraintValidator =
            new LengthConstraintValidator(2);

    private final MaxLengthConstraintValidator stringSetMaxLengthMaxLengthConstraintValidator =
            new MaxLengthConstraintValidator(2, true);

    private final MaxSizeSetConstraintValidator stringSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinLengthConstraintValidator stringSetMinLengthMinLengthConstraintValidator =
            new MinLengthConstraintValidator(2, true);

    private final MinSizeSetConstraintValidator stringSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final PatternConstraintValidator stringSetPatternPatternConstraintValidator =
            new PatternConstraintValidator("hello", List.of());

    private final PhoneConstraintValidator stringSetPhonePhoneConstraintValidator =
            new PhoneConstraintValidator(true, false);

    private final SizeSetConstraintValidator stringSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final TelegramConstraintValidator stringSetTelegramTelegramConstraintValidator =
            new TelegramConstraintValidator(true, false);

    private final ViberConstraintValidator stringSetViberViberConstraintValidator =
            new ViberConstraintValidator(true, false);

    private final WhatsAppConstraintValidator stringSetWhatsAppWhatsAppConstraintValidator =
            new WhatsAppConstraintValidator(true, false);

    private final MaxSizeSetConstraintValidator instantSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinSizeSetConstraintValidator instantSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator instantSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final TruncatedTimeInstantConstraintValidator instantSetTruncatedTimeTruncatedTimeInstantConstraintValidator =
            new TruncatedTimeInstantConstraintValidator(Truncated.MILLIS);

    private final MaxSizeSetConstraintValidator colorSetMaxSizeMaxSizeSetConstraintValidator =
            new MaxSizeSetConstraintValidator(50, true);

    private final MinSizeSetConstraintValidator colorSetMinSizeMinSizeSetConstraintValidator =
            new MinSizeSetConstraintValidator(2, true);

    private final SizeSetConstraintValidator colorSetSizeSizeSetConstraintValidator =
            new SizeSetConstraintValidator(12);

    private final SubEnumConstraintValidator colorSetSubEnumSubEnumConstraintValidator =
            new SubEnumConstraintValidator(Color.class, List.of("RED", "BLUE"), List.of());

    private final MaxSizeMapConstraintValidator booleanMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinSizeMapConstraintValidator booleanMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator booleanMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final MaxByteConstraintValidator byteMapMaxIntMaxByteConstraintValidator =
            new MaxByteConstraintValidator(10L, true);

    private final MaxSizeMapConstraintValidator byteMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinByteConstraintValidator byteMapMinIntMinByteConstraintValidator =
            new MinByteConstraintValidator(3L, true);

    private final MinSizeMapConstraintValidator byteMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator byteMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final MaxShortConstraintValidator shortMapMaxIntMaxShortConstraintValidator =
            new MaxShortConstraintValidator(10L, true);

    private final MaxSizeMapConstraintValidator shortMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinShortConstraintValidator shortMapMinIntMinShortConstraintValidator =
            new MinShortConstraintValidator(3L, true);

    private final MinSizeMapConstraintValidator shortMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator shortMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final MaxIntConstraintValidator intMapMaxIntMaxIntConstraintValidator =
            new MaxIntConstraintValidator(10L, true);

    private final MaxSizeMapConstraintValidator intMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinIntConstraintValidator intMapMinIntMinIntConstraintValidator =
            new MinIntConstraintValidator(3L, true);

    private final MinSizeMapConstraintValidator intMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator intMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final MaxLongConstraintValidator longMapMaxIntMaxLongConstraintValidator =
            new MaxLongConstraintValidator(10L, true);

    private final MaxSizeMapConstraintValidator longMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinLongConstraintValidator longMapMinIntMinLongConstraintValidator =
            new MinLongConstraintValidator(3L, true);

    private final MinSizeMapConstraintValidator longMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator longMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final EnumerationCharacterConstraintValidator charMapEnumerationEnumerationCharacterConstraintValidator =
            new EnumerationCharacterConstraintValidator(List.of("y", "n"));

    private final MaxSizeMapConstraintValidator charMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinSizeMapConstraintValidator charMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator charMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final MaxFloatConstraintValidator floatMapMaxDoubleMaxFloatConstraintValidator =
            new MaxFloatConstraintValidator(10.9);

    private final MaxSizeMapConstraintValidator floatMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinFloatConstraintValidator floatMapMinDoubleMinFloatConstraintValidator =
            new MinFloatConstraintValidator(3.1);

    private final MinSizeMapConstraintValidator floatMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator floatMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final MaxDoubleConstraintValidator doubleMapMaxDoubleMaxDoubleConstraintValidator =
            new MaxDoubleConstraintValidator(10.9);

    private final MaxSizeMapConstraintValidator doubleMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinDoubleConstraintValidator doubleMapMinDoubleMinDoubleConstraintValidator =
            new MinDoubleConstraintValidator(3.1);

    private final MinSizeMapConstraintValidator doubleMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator doubleMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final LatConstraintValidator decimalMapLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final LngConstraintValidator decimalMapLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final MaxBigDecimalNumberConstraintValidator decimalMapMaxNumberMaxBigDecimalNumberConstraintValidator =
            new MaxBigDecimalNumberConstraintValidator("10.9", true);

    private final MaxSizeMapConstraintValidator decimalMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinBigDecimalNumberConstraintValidator decimalMapMinNumberMinBigDecimalNumberConstraintValidator =
            new MinBigDecimalNumberConstraintValidator("3.1", true);

    private final MinSizeMapConstraintValidator decimalMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final NumericConstraintValidator decimalMapNumericNumericConstraintValidator =
            new NumericConstraintValidator(5, 2);

    private final SizeMapConstraintValidator decimalMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final MaxBigIntegerNumberConstraintValidator bigIntegerMapMaxNumberMaxBigIntegerNumberConstraintValidator =
            new MaxBigIntegerNumberConstraintValidator("10", true);

    private final MaxSizeMapConstraintValidator bigIntegerMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinBigIntegerNumberConstraintValidator bigIntegerMapMinNumberMinBigIntegerNumberConstraintValidator =
            new MinBigIntegerNumberConstraintValidator("3", true);

    private final MinSizeMapConstraintValidator bigIntegerMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator bigIntegerMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final Base64URLEncodedConstraintValidator stringMapBase64URLEncodedBase64URLEncodedConstraintValidator =
            new Base64URLEncodedConstraintValidator(Alphabet.URL);

    private final CountryCodeConstraintValidator stringMapCountryCodeCountryCodeConstraintValidator =
            new CountryCodeConstraintValidator(Format.ISO_3166_1_ALPHA_2);

    private final EmailConstraintValidator stringMapEmailEmailConstraintValidator =
            new EmailConstraintValidator(false);

    private final EnumerationStringConstraintValidator stringMapEnumerationEnumerationStringConstraintValidator =
            new EnumerationStringConstraintValidator(List.of("3", "2", "3"));

    private final HostNameConstraintValidator stringMapHostNameHostNameConstraintValidator =
            new HostNameConstraintValidator(false);

    private final IPConstraintValidator stringMapIPIPConstraintValidator =
            new IPConstraintValidator(List.of(Version.IP_V4, Version.IP_V6));

    private final LatinAlphabetOnlyConstraintValidator stringMapLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator =
            new LatinAlphabetOnlyConstraintValidator(true, true, true, "~!@#$%^&*()_+=-[]{},.;:<>?/\\\"' \t|\r\n\b");

    private final LengthConstraintValidator stringMapLengthLengthConstraintValidator =
            new LengthConstraintValidator(2);

    private final MaxLengthConstraintValidator stringMapMaxLengthMaxLengthConstraintValidator =
            new MaxLengthConstraintValidator(2, true);

    private final MaxSizeMapConstraintValidator stringMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinLengthConstraintValidator stringMapMinLengthMinLengthConstraintValidator =
            new MinLengthConstraintValidator(2, true);

    private final MinSizeMapConstraintValidator stringMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final PatternConstraintValidator stringMapPatternPatternConstraintValidator =
            new PatternConstraintValidator("hello", List.of());

    private final PhoneConstraintValidator stringMapPhonePhoneConstraintValidator =
            new PhoneConstraintValidator(true, false);

    private final SizeMapConstraintValidator stringMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final TelegramConstraintValidator stringMapTelegramTelegramConstraintValidator =
            new TelegramConstraintValidator(true, false);

    private final ViberConstraintValidator stringMapViberViberConstraintValidator =
            new ViberConstraintValidator(true, false);

    private final WhatsAppConstraintValidator stringMapWhatsAppWhatsAppConstraintValidator =
            new WhatsAppConstraintValidator(true, false);

    private final MaxSizeMapConstraintValidator instantMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinSizeMapConstraintValidator instantMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator instantMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final TruncatedTimeInstantConstraintValidator instantMapTruncatedTimeTruncatedTimeInstantConstraintValidator =
            new TruncatedTimeInstantConstraintValidator(Truncated.MILLIS);

    private final MaxSizeMapConstraintValidator colorMapMaxSizeMaxSizeMapConstraintValidator =
            new MaxSizeMapConstraintValidator(50, true);

    private final MinSizeMapConstraintValidator colorMapMinSizeMinSizeMapConstraintValidator =
            new MinSizeMapConstraintValidator(2, true);

    private final SizeMapConstraintValidator colorMapSizeSizeMapConstraintValidator =
            new SizeMapConstraintValidator(12);

    private final SubEnumConstraintValidator colorMapSubEnumSubEnumConstraintValidator =
            new SubEnumConstraintValidator(Color.class, List.of("RED", "BLUE"), List.of());

    private final CountryCodeConstraintValidator countryCodeAlpha2CountryCodeCountryCodeConstraintValidator =
            new CountryCodeConstraintValidator(Format.ISO_3166_1_ALPHA_2);

    private final CountryCodeConstraintValidator countryCodeAlpha3CountryCodeCountryCodeConstraintValidator =
            new CountryCodeConstraintValidator(Format.ISO_3166_1_ALPHA_3);

    private final CountryCodeConstraintValidator countryCodeNumericCountryCodeCountryCodeConstraintValidator =
            new CountryCodeConstraintValidator(Format.ISO_3166_1_NUMERIC);

    private final Base64URLEncodedConstraintValidator base64URLEncodedBaseBase64URLEncodedBase64URLEncodedConstraintValidator =
            new Base64URLEncodedConstraintValidator(Alphabet.BASE);

    private final Base64URLEncodedConstraintValidator base64URLEncodedUrlBase64URLEncodedBase64URLEncodedConstraintValidator =
            new Base64URLEncodedConstraintValidator(Alphabet.URL);

    private final IPConstraintValidator ipIPIPConstraintValidator =
            new IPConstraintValidator(List.of(Version.IP_V4, Version.IP_V6));

    private final IPConstraintValidator ip4IPIPConstraintValidator =
            new IPConstraintValidator(List.of(Version.IP_V4));

    private final IPConstraintValidator ip6IPIPConstraintValidator =
            new IPConstraintValidator(List.of(Version.IP_V6));

    private final LatConstraintValidator lat111kmLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_111_KILOMETERS);

    private final LngConstraintValidator lng111kmLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_111_KILOMETERS);

    private final LatConstraintValidator lat11kmLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_11_KILOMETERS);

    private final LngConstraintValidator lng11kmLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_11_KILOMETERS);

    private final LatConstraintValidator lat1kmLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_1_KILOMETER);

    private final LngConstraintValidator lng1kmLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_1_KILOMETER);

    private final LatConstraintValidator lat111mLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_111_METERS);

    private final LngConstraintValidator lng111mLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_111_METERS);

    private final LatConstraintValidator lat11mLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_11_METERS);

    private final LngConstraintValidator lng11mLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_11_METERS);

    private final LatConstraintValidator lat1mLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final LngConstraintValidator lng1mLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_1_METER);

    private final LatConstraintValidator lat11cmLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_11_CENTIMETERS);

    private final LngConstraintValidator lng11cmLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_11_CENTIMETERS);

    private final LatConstraintValidator lat1cmLatLatConstraintValidator =
            new LatConstraintValidator(LocationAccuracy.ACCURACY_1_CENTIMETER);

    private final LngConstraintValidator lng1cmLngLngConstraintValidator =
            new LngConstraintValidator(LocationAccuracy.ACCURACY_1_CENTIMETER);

    @Override
    public void validate(final $$VirtualRequest model,
                         final HttpModelType httpModelType,
                         final String name) {
        notEmptyStringConstraintValidator.validate(model.optionalParameter, HttpModelType.PARAMETER, "optionalParameter");

        requiredConstraintValidator.validate(model.booleanParameter, HttpModelType.PARAMETER, "booleanParameter");
        assertFalseConstraintValidator.validate(model.booleanParameter, HttpModelType.PARAMETER, "booleanParameter");
        assertTrueConstraintValidator.validate(model.booleanParameter, HttpModelType.PARAMETER, "booleanParameter");

        requiredConstraintValidator.validate(model.byteParameter, HttpModelType.PARAMETER, "byteParameter");
        byteParameterMaxIntMaxByteConstraintValidator.validate(model.byteParameter, HttpModelType.PARAMETER, "byteParameter");
        byteParameterMaxNumberMaxByteConstraintValidator.validate(model.byteParameter, HttpModelType.PARAMETER, "byteParameter");
        byteParameterMinIntMinByteConstraintValidator.validate(model.byteParameter, HttpModelType.PARAMETER, "byteParameter");
        byteParameterMinNumberMinByteConstraintValidator.validate(model.byteParameter, HttpModelType.PARAMETER, "byteParameter");

        requiredConstraintValidator.validate(model.shortParameter, HttpModelType.PARAMETER, "shortParameter");
        shortParameterMaxIntMaxShortConstraintValidator.validate(model.shortParameter, HttpModelType.PARAMETER, "shortParameter");
        shortParameterMaxNumberMaxShortConstraintValidator.validate(model.shortParameter, HttpModelType.PARAMETER, "shortParameter");
        shortParameterMinIntMinShortConstraintValidator.validate(model.shortParameter, HttpModelType.PARAMETER, "shortParameter");
        shortParameterMinNumberMinShortConstraintValidator.validate(model.shortParameter, HttpModelType.PARAMETER, "shortParameter");

        requiredConstraintValidator.validate(model.intParameter, HttpModelType.PARAMETER, "intParameter");
        intParameterMaxIntMaxIntConstraintValidator.validate(model.intParameter, HttpModelType.PARAMETER, "intParameter");
        intParameterMaxNumberMaxIntConstraintValidator.validate(model.intParameter, HttpModelType.PARAMETER, "intParameter");
        intParameterMinIntMinIntConstraintValidator.validate(model.intParameter, HttpModelType.PARAMETER, "intParameter");
        intParameterMinNumberMinIntConstraintValidator.validate(model.intParameter, HttpModelType.PARAMETER, "intParameter");

        requiredConstraintValidator.validate(model.longParameter, HttpModelType.PARAMETER, "longParameter");
        longParameterMaxIntMaxLongConstraintValidator.validate(model.longParameter, HttpModelType.PARAMETER, "longParameter");
        longParameterMaxNumberMaxLongConstraintValidator.validate(model.longParameter, HttpModelType.PARAMETER, "longParameter");
        longParameterMinIntMinLongConstraintValidator.validate(model.longParameter, HttpModelType.PARAMETER, "longParameter");
        longParameterMinNumberMinLongConstraintValidator.validate(model.longParameter, HttpModelType.PARAMETER, "longParameter");

        requiredConstraintValidator.validate(model.bigIntParameter, HttpModelType.PARAMETER, "bigIntParameter");
        bigIntParameterMaxNumberMaxBigIntegerNumberConstraintValidator.validate(model.bigIntParameter, HttpModelType.PARAMETER, "bigIntParameter");
        bigIntParameterMinNumberMinBigIntegerNumberConstraintValidator.validate(model.bigIntParameter, HttpModelType.PARAMETER, "bigIntParameter");

        requiredConstraintValidator.validate(model.floatParameter, HttpModelType.PARAMETER, "floatParameter");
        floatParameterMaxDoubleMaxFloatConstraintValidator.validate(model.floatParameter, HttpModelType.PARAMETER, "floatParameter");
        floatParameterMaxNumberMaxFloatConstraintValidator.validate(model.floatParameter, HttpModelType.PARAMETER, "floatParameter");
        floatParameterMinDoubleMinFloatConstraintValidator.validate(model.floatParameter, HttpModelType.PARAMETER, "floatParameter");
        floatParameterMinNumberMinFloatConstraintValidator.validate(model.floatParameter, HttpModelType.PARAMETER, "floatParameter");

        requiredConstraintValidator.validate(model.doubleParameter, HttpModelType.PARAMETER, "doubleParameter");
        doubleParameterMaxDoubleMaxDoubleConstraintValidator.validate(model.doubleParameter, HttpModelType.PARAMETER, "doubleParameter");
        doubleParameterMaxNumberMaxDoubleConstraintValidator.validate(model.doubleParameter, HttpModelType.PARAMETER, "doubleParameter");
        doubleParameterMinDoubleMinDoubleConstraintValidator.validate(model.doubleParameter, HttpModelType.PARAMETER, "doubleParameter");
        doubleParameterMinNumberMinDoubleConstraintValidator.validate(model.doubleParameter, HttpModelType.PARAMETER, "doubleParameter");

        requiredConstraintValidator.validate(model.decimalParameter, HttpModelType.PARAMETER, "decimalParameter");
        decimalParameterLatLatConstraintValidator.validate(model.decimalParameter, HttpModelType.PARAMETER, "decimalParameter");
        decimalParameterLngLngConstraintValidator.validate(model.decimalParameter, HttpModelType.PARAMETER, "decimalParameter");
        decimalParameterMaxNumberMaxBigDecimalNumberConstraintValidator.validate(model.decimalParameter, HttpModelType.PARAMETER, "decimalParameter");
        decimalParameterMinNumberMinBigDecimalNumberConstraintValidator.validate(model.decimalParameter, HttpModelType.PARAMETER, "decimalParameter");
        decimalParameterNumericNumericConstraintValidator.validate(model.decimalParameter, HttpModelType.PARAMETER, "decimalParameter");

        requiredConstraintValidator.validate(model.charParameter, HttpModelType.PARAMETER, "charParameter");
        charParameterEnumerationEnumerationCharacterConstraintValidator.validate(model.charParameter, HttpModelType.PARAMETER, "charParameter");

        requiredConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterBase64URLEncodedBase64URLEncodedConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterCountryCodeCountryCodeConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterEmailEmailConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterEnumerationEnumerationStringConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterHostNameHostNameConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterIPIPConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterLengthLengthConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        lowercaseConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterMaxLengthMaxLengthConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterMinLengthMinLengthConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterPatternPatternConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterPhonePhoneConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        skypeConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterTelegramTelegramConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        uRIConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        uRLEncodedConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        uppercaseConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterViberViberConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");
        stringParameterWhatsAppWhatsAppConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");

        requiredConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instantParameter");
        futureInstantConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instantParameter");
        futureOrPresentInstantConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instantParameter");
        pastInstantConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instantParameter");
        pastOrPresentInstantConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instantParameter");
        instantParameterTruncatedTimeTruncatedTimeInstantConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instantParameter");

        requiredConstraintValidator.validate(model.colorParameter, HttpModelType.PARAMETER, "colorParameter");
        colorParameterSubEnumSubEnumConstraintValidator.validate(model.colorParameter, HttpModelType.PARAMETER, "colorParameter");

        notEmptyStringConstraintValidator.validateIterable(model.optionalList, HttpModelType.PARAMETER, "optionalList");

        requiredListConstraintValidator.validate(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");
        requiredConstraintValidator.validateIterable(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");
        assertFalseConstraintValidator.validateIterable(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");
        assertTrueConstraintValidator.validateIterable(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");
        booleanValuesMaxSizeMaxSizeListConstraintValidator.validate(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");
        booleanValuesMinSizeMinSizeListConstraintValidator.validate(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");
        booleanValuesSizeSizeListConstraintValidator.validate(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");
        uniqueItemsListConstraintValidator.validate(model.booleanValues, HttpModelType.PARAMETER, "booleanValues");

        requiredListConstraintValidator.validate(model.byteValues, HttpModelType.PARAMETER, "byteValues");
        requiredConstraintValidator.validateIterable(model.byteValues, HttpModelType.PARAMETER, "byteValues");
        byteValuesMaxIntMaxByteConstraintValidator.validateIterable(model.byteValues, HttpModelType.PARAMETER, "byteValues");
        byteValuesMaxSizeMaxSizeListConstraintValidator.validate(model.byteValues, HttpModelType.PARAMETER, "byteValues");
        byteValuesMinIntMinByteConstraintValidator.validateIterable(model.byteValues, HttpModelType.PARAMETER, "byteValues");
        byteValuesMinSizeMinSizeListConstraintValidator.validate(model.byteValues, HttpModelType.PARAMETER, "byteValues");
        byteValuesSizeSizeListConstraintValidator.validate(model.byteValues, HttpModelType.PARAMETER, "byteValues");
        uniqueItemsListConstraintValidator.validate(model.byteValues, HttpModelType.PARAMETER, "byteValues");

        requiredListConstraintValidator.validate(model.shortValues, HttpModelType.PARAMETER, "shortValues");
        requiredConstraintValidator.validateIterable(model.shortValues, HttpModelType.PARAMETER, "shortValues");
        shortValuesMaxIntMaxShortConstraintValidator.validateIterable(model.shortValues, HttpModelType.PARAMETER, "shortValues");
        shortValuesMaxSizeMaxSizeListConstraintValidator.validate(model.shortValues, HttpModelType.PARAMETER, "shortValues");
        shortValuesMinIntMinShortConstraintValidator.validateIterable(model.shortValues, HttpModelType.PARAMETER, "shortValues");
        shortValuesMinSizeMinSizeListConstraintValidator.validate(model.shortValues, HttpModelType.PARAMETER, "shortValues");
        shortValuesSizeSizeListConstraintValidator.validate(model.shortValues, HttpModelType.PARAMETER, "shortValues");
        uniqueItemsListConstraintValidator.validate(model.shortValues, HttpModelType.PARAMETER, "shortValues");

        requiredListConstraintValidator.validate(model.intValues, HttpModelType.PARAMETER, "intValues");
        requiredConstraintValidator.validateIterable(model.intValues, HttpModelType.PARAMETER, "intValues");
        intValuesMaxIntMaxIntConstraintValidator.validateIterable(model.intValues, HttpModelType.PARAMETER, "intValues");
        intValuesMaxSizeMaxSizeListConstraintValidator.validate(model.intValues, HttpModelType.PARAMETER, "intValues");
        intValuesMinIntMinIntConstraintValidator.validateIterable(model.intValues, HttpModelType.PARAMETER, "intValues");
        intValuesMinSizeMinSizeListConstraintValidator.validate(model.intValues, HttpModelType.PARAMETER, "intValues");
        intValuesSizeSizeListConstraintValidator.validate(model.intValues, HttpModelType.PARAMETER, "intValues");
        uniqueItemsListConstraintValidator.validate(model.intValues, HttpModelType.PARAMETER, "intValues");

        requiredListConstraintValidator.validate(model.longValues, HttpModelType.PARAMETER, "longValues");
        requiredConstraintValidator.validateIterable(model.longValues, HttpModelType.PARAMETER, "longValues");
        longValuesMaxIntMaxLongConstraintValidator.validateIterable(model.longValues, HttpModelType.PARAMETER, "longValues");
        longValuesMaxSizeMaxSizeListConstraintValidator.validate(model.longValues, HttpModelType.PARAMETER, "longValues");
        longValuesMinIntMinLongConstraintValidator.validateIterable(model.longValues, HttpModelType.PARAMETER, "longValues");
        longValuesMinSizeMinSizeListConstraintValidator.validate(model.longValues, HttpModelType.PARAMETER, "longValues");
        longValuesSizeSizeListConstraintValidator.validate(model.longValues, HttpModelType.PARAMETER, "longValues");
        uniqueItemsListConstraintValidator.validate(model.longValues, HttpModelType.PARAMETER, "longValues");

        requiredListConstraintValidator.validate(model.charValues, HttpModelType.PARAMETER, "charValues");
        requiredConstraintValidator.validateIterable(model.charValues, HttpModelType.PARAMETER, "charValues");
        charValuesEnumerationEnumerationCharacterConstraintValidator.validateIterable(model.charValues, HttpModelType.PARAMETER, "charValues");
        charValuesMaxSizeMaxSizeListConstraintValidator.validate(model.charValues, HttpModelType.PARAMETER, "charValues");
        charValuesMinSizeMinSizeListConstraintValidator.validate(model.charValues, HttpModelType.PARAMETER, "charValues");
        charValuesSizeSizeListConstraintValidator.validate(model.charValues, HttpModelType.PARAMETER, "charValues");
        uniqueItemsListConstraintValidator.validate(model.charValues, HttpModelType.PARAMETER, "charValues");

        requiredListConstraintValidator.validate(model.floatValues, HttpModelType.PARAMETER, "floatValues");
        requiredConstraintValidator.validateIterable(model.floatValues, HttpModelType.PARAMETER, "floatValues");
        floatValuesMaxDoubleMaxFloatConstraintValidator.validateIterable(model.floatValues, HttpModelType.PARAMETER, "floatValues");
        floatValuesMaxSizeMaxSizeListConstraintValidator.validate(model.floatValues, HttpModelType.PARAMETER, "floatValues");
        floatValuesMinDoubleMinFloatConstraintValidator.validateIterable(model.floatValues, HttpModelType.PARAMETER, "floatValues");
        floatValuesMinSizeMinSizeListConstraintValidator.validate(model.floatValues, HttpModelType.PARAMETER, "floatValues");
        floatValuesSizeSizeListConstraintValidator.validate(model.floatValues, HttpModelType.PARAMETER, "floatValues");
        uniqueItemsListConstraintValidator.validate(model.floatValues, HttpModelType.PARAMETER, "floatValues");

        requiredListConstraintValidator.validate(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");
        requiredConstraintValidator.validateIterable(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");
        doubleValuesMaxDoubleMaxDoubleConstraintValidator.validateIterable(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");
        doubleValuesMaxSizeMaxSizeListConstraintValidator.validate(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");
        doubleValuesMinDoubleMinDoubleConstraintValidator.validateIterable(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");
        doubleValuesMinSizeMinSizeListConstraintValidator.validate(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");
        doubleValuesSizeSizeListConstraintValidator.validate(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");
        uniqueItemsListConstraintValidator.validate(model.doubleValues, HttpModelType.PARAMETER, "doubleValues");

        requiredListConstraintValidator.validate(model.decimals, HttpModelType.PARAMETER, "decimals");
        requiredConstraintValidator.validateIterable(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsLatLatConstraintValidator.validateIterable(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsLngLngConstraintValidator.validateIterable(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsMaxNumberMaxBigDecimalNumberConstraintValidator.validateIterable(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsMaxSizeMaxSizeListConstraintValidator.validate(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsMinNumberMinBigDecimalNumberConstraintValidator.validateIterable(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsMinSizeMinSizeListConstraintValidator.validate(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsNumericNumericConstraintValidator.validateIterable(model.decimals, HttpModelType.PARAMETER, "decimals");
        decimalsSizeSizeListConstraintValidator.validate(model.decimals, HttpModelType.PARAMETER, "decimals");
        uniqueItemsListConstraintValidator.validate(model.decimals, HttpModelType.PARAMETER, "decimals");

        requiredListConstraintValidator.validate(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");
        requiredConstraintValidator.validateIterable(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");
        bigIntegersMaxNumberMaxBigIntegerNumberConstraintValidator.validateIterable(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");
        bigIntegersMaxSizeMaxSizeListConstraintValidator.validate(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");
        bigIntegersMinNumberMinBigIntegerNumberConstraintValidator.validateIterable(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");
        bigIntegersMinSizeMinSizeListConstraintValidator.validate(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");
        bigIntegersSizeSizeListConstraintValidator.validate(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");
        uniqueItemsListConstraintValidator.validate(model.bigIntegers, HttpModelType.PARAMETER, "bigIntegers");

        requiredListConstraintValidator.validate(model.strings, HttpModelType.PARAMETER, "strings");
        requiredConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsBase64URLEncodedBase64URLEncodedConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsCountryCodeCountryCodeConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsEmailEmailConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsEnumerationEnumerationStringConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsHostNameHostNameConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsIPIPConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsLengthLengthConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        lowercaseConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsMaxLengthMaxLengthConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsMaxSizeMaxSizeListConstraintValidator.validate(model.strings, HttpModelType.PARAMETER, "strings");
        stringsMinLengthMinLengthConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsMinSizeMinSizeListConstraintValidator.validate(model.strings, HttpModelType.PARAMETER, "strings");
        stringsPatternPatternConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsPhonePhoneConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsSizeSizeListConstraintValidator.validate(model.strings, HttpModelType.PARAMETER, "strings");
        skypeConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsTelegramTelegramConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        uRIConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        uRLEncodedConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        uniqueItemsListConstraintValidator.validate(model.strings, HttpModelType.PARAMETER, "strings");
        uppercaseConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsViberViberConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");
        stringsWhatsAppWhatsAppConstraintValidator.validateIterable(model.strings, HttpModelType.PARAMETER, "strings");

        requiredListConstraintValidator.validate(model.instants, HttpModelType.PARAMETER, "instants");
        requiredConstraintValidator.validateIterable(model.instants, HttpModelType.PARAMETER, "instants");
        futureInstantConstraintValidator.validateIterable(model.instants, HttpModelType.PARAMETER, "instants");
        futureOrPresentInstantConstraintValidator.validateIterable(model.instants, HttpModelType.PARAMETER, "instants");
        instantsMaxSizeMaxSizeListConstraintValidator.validate(model.instants, HttpModelType.PARAMETER, "instants");
        instantsMinSizeMinSizeListConstraintValidator.validate(model.instants, HttpModelType.PARAMETER, "instants");
        pastInstantConstraintValidator.validateIterable(model.instants, HttpModelType.PARAMETER, "instants");
        pastOrPresentInstantConstraintValidator.validateIterable(model.instants, HttpModelType.PARAMETER, "instants");
        instantsSizeSizeListConstraintValidator.validate(model.instants, HttpModelType.PARAMETER, "instants");
        instantsTruncatedTimeTruncatedTimeInstantConstraintValidator.validateIterable(model.instants, HttpModelType.PARAMETER, "instants");
        uniqueItemsListConstraintValidator.validate(model.instants, HttpModelType.PARAMETER, "instants");

        requiredListConstraintValidator.validate(model.colors, HttpModelType.PARAMETER, "colors");
        requiredConstraintValidator.validateIterable(model.colors, HttpModelType.PARAMETER, "colors");
        colorsMaxSizeMaxSizeListConstraintValidator.validate(model.colors, HttpModelType.PARAMETER, "colors");
        colorsMinSizeMinSizeListConstraintValidator.validate(model.colors, HttpModelType.PARAMETER, "colors");
        colorsSizeSizeListConstraintValidator.validate(model.colors, HttpModelType.PARAMETER, "colors");
        colorsSubEnumSubEnumConstraintValidator.validateIterable(model.colors, HttpModelType.PARAMETER, "colors");
        uniqueItemsListConstraintValidator.validate(model.colors, HttpModelType.PARAMETER, "colors");

        notEmptyStringConstraintValidator.validateIterable(model.optionalSet, HttpModelType.PARAMETER, "optionalSet");

        requiredSetConstraintValidator.validate(model.booleanSet, HttpModelType.PARAMETER, "booleanSet");
        requiredConstraintValidator.validateIterable(model.booleanSet, HttpModelType.PARAMETER, "booleanSet");
        assertFalseConstraintValidator.validateIterable(model.booleanSet, HttpModelType.PARAMETER, "booleanSet");
        assertTrueConstraintValidator.validateIterable(model.booleanSet, HttpModelType.PARAMETER, "booleanSet");
        booleanSetMaxSizeMaxSizeSetConstraintValidator.validate(model.booleanSet, HttpModelType.PARAMETER, "booleanSet");
        booleanSetMinSizeMinSizeSetConstraintValidator.validate(model.booleanSet, HttpModelType.PARAMETER, "booleanSet");
        booleanSetSizeSizeSetConstraintValidator.validate(model.booleanSet, HttpModelType.PARAMETER, "booleanSet");

        requiredSetConstraintValidator.validate(model.byteSet, HttpModelType.PARAMETER, "byteSet");
        requiredConstraintValidator.validateIterable(model.byteSet, HttpModelType.PARAMETER, "byteSet");
        byteSetMaxIntMaxByteConstraintValidator.validateIterable(model.byteSet, HttpModelType.PARAMETER, "byteSet");
        byteSetMaxSizeMaxSizeSetConstraintValidator.validate(model.byteSet, HttpModelType.PARAMETER, "byteSet");
        byteSetMinIntMinByteConstraintValidator.validateIterable(model.byteSet, HttpModelType.PARAMETER, "byteSet");
        byteSetMinSizeMinSizeSetConstraintValidator.validate(model.byteSet, HttpModelType.PARAMETER, "byteSet");
        byteSetSizeSizeSetConstraintValidator.validate(model.byteSet, HttpModelType.PARAMETER, "byteSet");

        requiredSetConstraintValidator.validate(model.shortSet, HttpModelType.PARAMETER, "shortSet");
        requiredConstraintValidator.validateIterable(model.shortSet, HttpModelType.PARAMETER, "shortSet");
        shortSetMaxIntMaxShortConstraintValidator.validateIterable(model.shortSet, HttpModelType.PARAMETER, "shortSet");
        shortSetMaxSizeMaxSizeSetConstraintValidator.validate(model.shortSet, HttpModelType.PARAMETER, "shortSet");
        shortSetMinIntMinShortConstraintValidator.validateIterable(model.shortSet, HttpModelType.PARAMETER, "shortSet");
        shortSetMinSizeMinSizeSetConstraintValidator.validate(model.shortSet, HttpModelType.PARAMETER, "shortSet");
        shortSetSizeSizeSetConstraintValidator.validate(model.shortSet, HttpModelType.PARAMETER, "shortSet");

        requiredSetConstraintValidator.validate(model.intSet, HttpModelType.PARAMETER, "intSet");
        requiredConstraintValidator.validateIterable(model.intSet, HttpModelType.PARAMETER, "intSet");
        intSetMaxIntMaxIntConstraintValidator.validateIterable(model.intSet, HttpModelType.PARAMETER, "intSet");
        intSetMaxSizeMaxSizeSetConstraintValidator.validate(model.intSet, HttpModelType.PARAMETER, "intSet");
        intSetMinIntMinIntConstraintValidator.validateIterable(model.intSet, HttpModelType.PARAMETER, "intSet");
        intSetMinSizeMinSizeSetConstraintValidator.validate(model.intSet, HttpModelType.PARAMETER, "intSet");
        intSetSizeSizeSetConstraintValidator.validate(model.intSet, HttpModelType.PARAMETER, "intSet");

        requiredSetConstraintValidator.validate(model.longSet, HttpModelType.PARAMETER, "longSet");
        requiredConstraintValidator.validateIterable(model.longSet, HttpModelType.PARAMETER, "longSet");
        longSetMaxIntMaxLongConstraintValidator.validateIterable(model.longSet, HttpModelType.PARAMETER, "longSet");
        longSetMaxSizeMaxSizeSetConstraintValidator.validate(model.longSet, HttpModelType.PARAMETER, "longSet");
        longSetMinIntMinLongConstraintValidator.validateIterable(model.longSet, HttpModelType.PARAMETER, "longSet");
        longSetMinSizeMinSizeSetConstraintValidator.validate(model.longSet, HttpModelType.PARAMETER, "longSet");
        longSetSizeSizeSetConstraintValidator.validate(model.longSet, HttpModelType.PARAMETER, "longSet");

        requiredSetConstraintValidator.validate(model.charSet, HttpModelType.PARAMETER, "charSet");
        requiredConstraintValidator.validateIterable(model.charSet, HttpModelType.PARAMETER, "charSet");
        charSetEnumerationEnumerationCharacterConstraintValidator.validateIterable(model.charSet, HttpModelType.PARAMETER, "charSet");
        charSetMaxSizeMaxSizeSetConstraintValidator.validate(model.charSet, HttpModelType.PARAMETER, "charSet");
        charSetMinSizeMinSizeSetConstraintValidator.validate(model.charSet, HttpModelType.PARAMETER, "charSet");
        charSetSizeSizeSetConstraintValidator.validate(model.charSet, HttpModelType.PARAMETER, "charSet");

        requiredSetConstraintValidator.validate(model.floatSet, HttpModelType.PARAMETER, "floatSet");
        requiredConstraintValidator.validateIterable(model.floatSet, HttpModelType.PARAMETER, "floatSet");
        floatSetMaxDoubleMaxFloatConstraintValidator.validateIterable(model.floatSet, HttpModelType.PARAMETER, "floatSet");
        floatSetMaxSizeMaxSizeSetConstraintValidator.validate(model.floatSet, HttpModelType.PARAMETER, "floatSet");
        floatSetMinDoubleMinFloatConstraintValidator.validateIterable(model.floatSet, HttpModelType.PARAMETER, "floatSet");
        floatSetMinSizeMinSizeSetConstraintValidator.validate(model.floatSet, HttpModelType.PARAMETER, "floatSet");
        floatSetSizeSizeSetConstraintValidator.validate(model.floatSet, HttpModelType.PARAMETER, "floatSet");

        requiredSetConstraintValidator.validate(model.doubleSet, HttpModelType.PARAMETER, "doubleSet");
        requiredConstraintValidator.validateIterable(model.doubleSet, HttpModelType.PARAMETER, "doubleSet");
        doubleSetMaxDoubleMaxDoubleConstraintValidator.validateIterable(model.doubleSet, HttpModelType.PARAMETER, "doubleSet");
        doubleSetMaxSizeMaxSizeSetConstraintValidator.validate(model.doubleSet, HttpModelType.PARAMETER, "doubleSet");
        doubleSetMinDoubleMinDoubleConstraintValidator.validateIterable(model.doubleSet, HttpModelType.PARAMETER, "doubleSet");
        doubleSetMinSizeMinSizeSetConstraintValidator.validate(model.doubleSet, HttpModelType.PARAMETER, "doubleSet");
        doubleSetSizeSizeSetConstraintValidator.validate(model.doubleSet, HttpModelType.PARAMETER, "doubleSet");

        requiredSetConstraintValidator.validate(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        requiredConstraintValidator.validateIterable(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetLatLatConstraintValidator.validateIterable(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetLngLngConstraintValidator.validateIterable(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetMaxNumberMaxBigDecimalNumberConstraintValidator.validateIterable(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetMaxSizeMaxSizeSetConstraintValidator.validate(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetMinNumberMinBigDecimalNumberConstraintValidator.validateIterable(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetMinSizeMinSizeSetConstraintValidator.validate(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetNumericNumericConstraintValidator.validateIterable(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");
        decimalSetSizeSizeSetConstraintValidator.validate(model.decimalSet, HttpModelType.PARAMETER, "decimalSet");

        requiredSetConstraintValidator.validate(model.bigIntegerSet, HttpModelType.PARAMETER, "bigIntegerSet");
        requiredConstraintValidator.validateIterable(model.bigIntegerSet, HttpModelType.PARAMETER, "bigIntegerSet");
        bigIntegerSetMaxNumberMaxBigIntegerNumberConstraintValidator.validateIterable(model.bigIntegerSet, HttpModelType.PARAMETER, "bigIntegerSet");
        bigIntegerSetMaxSizeMaxSizeSetConstraintValidator.validate(model.bigIntegerSet, HttpModelType.PARAMETER, "bigIntegerSet");
        bigIntegerSetMinNumberMinBigIntegerNumberConstraintValidator.validateIterable(model.bigIntegerSet, HttpModelType.PARAMETER, "bigIntegerSet");
        bigIntegerSetMinSizeMinSizeSetConstraintValidator.validate(model.bigIntegerSet, HttpModelType.PARAMETER, "bigIntegerSet");
        bigIntegerSetSizeSizeSetConstraintValidator.validate(model.bigIntegerSet, HttpModelType.PARAMETER, "bigIntegerSet");

        requiredSetConstraintValidator.validate(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        requiredConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetBase64URLEncodedBase64URLEncodedConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetCountryCodeCountryCodeConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetEmailEmailConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetEnumerationEnumerationStringConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetHostNameHostNameConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetIPIPConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetLengthLengthConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        lowercaseConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetMaxLengthMaxLengthConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetMaxSizeMaxSizeSetConstraintValidator.validate(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetMinLengthMinLengthConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetMinSizeMinSizeSetConstraintValidator.validate(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetPatternPatternConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetPhonePhoneConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetSizeSizeSetConstraintValidator.validate(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        skypeConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetTelegramTelegramConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        uRIConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        uRLEncodedConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        uppercaseConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetViberViberConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");
        stringSetWhatsAppWhatsAppConstraintValidator.validateIterable(model.stringSet, HttpModelType.PARAMETER, "stringSet");

        requiredSetConstraintValidator.validate(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        requiredConstraintValidator.validateIterable(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        futureInstantConstraintValidator.validateIterable(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        futureOrPresentInstantConstraintValidator.validateIterable(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        instantSetMaxSizeMaxSizeSetConstraintValidator.validate(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        instantSetMinSizeMinSizeSetConstraintValidator.validate(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        pastInstantConstraintValidator.validateIterable(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        pastOrPresentInstantConstraintValidator.validateIterable(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        instantSetSizeSizeSetConstraintValidator.validate(model.instantSet, HttpModelType.PARAMETER, "instantSet");
        instantSetTruncatedTimeTruncatedTimeInstantConstraintValidator.validateIterable(model.instantSet, HttpModelType.PARAMETER, "instantSet");

        requiredSetConstraintValidator.validate(model.colorSet, HttpModelType.PARAMETER, "colorSet");
        requiredConstraintValidator.validateIterable(model.colorSet, HttpModelType.PARAMETER, "colorSet");
        colorSetMaxSizeMaxSizeSetConstraintValidator.validate(model.colorSet, HttpModelType.PARAMETER, "colorSet");
        colorSetMinSizeMinSizeSetConstraintValidator.validate(model.colorSet, HttpModelType.PARAMETER, "colorSet");
        colorSetSizeSizeSetConstraintValidator.validate(model.colorSet, HttpModelType.PARAMETER, "colorSet");
        colorSetSubEnumSubEnumConstraintValidator.validateIterable(model.colorSet, HttpModelType.PARAMETER, "colorSet");

        notEmptyStringConstraintValidator.validateMapValues(model.optionalMap, HttpModelType.PARAMETER, "optionalMap");

        requiredMapConstraintValidator.validate(model.booleanMap, HttpModelType.PARAMETER, "booleanMap");
        requiredConstraintValidator.validateMapValues(model.booleanMap, HttpModelType.PARAMETER, "booleanMap");
        assertFalseConstraintValidator.validateMapValues(model.booleanMap, HttpModelType.PARAMETER, "booleanMap");
        assertTrueConstraintValidator.validateMapValues(model.booleanMap, HttpModelType.PARAMETER, "booleanMap");
        booleanMapMaxSizeMaxSizeMapConstraintValidator.validate(model.booleanMap, HttpModelType.PARAMETER, "booleanMap");
        booleanMapMinSizeMinSizeMapConstraintValidator.validate(model.booleanMap, HttpModelType.PARAMETER, "booleanMap");
        booleanMapSizeSizeMapConstraintValidator.validate(model.booleanMap, HttpModelType.PARAMETER, "booleanMap");

        requiredMapConstraintValidator.validate(model.byteMap, HttpModelType.PARAMETER, "byteMap");
        requiredConstraintValidator.validateMapValues(model.byteMap, HttpModelType.PARAMETER, "byteMap");
        byteMapMaxIntMaxByteConstraintValidator.validateMapValues(model.byteMap, HttpModelType.PARAMETER, "byteMap");
        byteMapMaxSizeMaxSizeMapConstraintValidator.validate(model.byteMap, HttpModelType.PARAMETER, "byteMap");
        byteMapMinIntMinByteConstraintValidator.validateMapValues(model.byteMap, HttpModelType.PARAMETER, "byteMap");
        byteMapMinSizeMinSizeMapConstraintValidator.validate(model.byteMap, HttpModelType.PARAMETER, "byteMap");
        byteMapSizeSizeMapConstraintValidator.validate(model.byteMap, HttpModelType.PARAMETER, "byteMap");

        requiredMapConstraintValidator.validate(model.shortMap, HttpModelType.PARAMETER, "shortMap");
        requiredConstraintValidator.validateMapValues(model.shortMap, HttpModelType.PARAMETER, "shortMap");
        shortMapMaxIntMaxShortConstraintValidator.validateMapValues(model.shortMap, HttpModelType.PARAMETER, "shortMap");
        shortMapMaxSizeMaxSizeMapConstraintValidator.validate(model.shortMap, HttpModelType.PARAMETER, "shortMap");
        shortMapMinIntMinShortConstraintValidator.validateMapValues(model.shortMap, HttpModelType.PARAMETER, "shortMap");
        shortMapMinSizeMinSizeMapConstraintValidator.validate(model.shortMap, HttpModelType.PARAMETER, "shortMap");
        shortMapSizeSizeMapConstraintValidator.validate(model.shortMap, HttpModelType.PARAMETER, "shortMap");

        requiredMapConstraintValidator.validate(model.intMap, HttpModelType.PARAMETER, "intMap");
        requiredConstraintValidator.validateMapValues(model.intMap, HttpModelType.PARAMETER, "intMap");
        intMapMaxIntMaxIntConstraintValidator.validateMapValues(model.intMap, HttpModelType.PARAMETER, "intMap");
        intMapMaxSizeMaxSizeMapConstraintValidator.validate(model.intMap, HttpModelType.PARAMETER, "intMap");
        intMapMinIntMinIntConstraintValidator.validateMapValues(model.intMap, HttpModelType.PARAMETER, "intMap");
        intMapMinSizeMinSizeMapConstraintValidator.validate(model.intMap, HttpModelType.PARAMETER, "intMap");
        intMapSizeSizeMapConstraintValidator.validate(model.intMap, HttpModelType.PARAMETER, "intMap");

        requiredMapConstraintValidator.validate(model.longMap, HttpModelType.PARAMETER, "longMap");
        requiredConstraintValidator.validateMapValues(model.longMap, HttpModelType.PARAMETER, "longMap");
        longMapMaxIntMaxLongConstraintValidator.validateMapValues(model.longMap, HttpModelType.PARAMETER, "longMap");
        longMapMaxSizeMaxSizeMapConstraintValidator.validate(model.longMap, HttpModelType.PARAMETER, "longMap");
        longMapMinIntMinLongConstraintValidator.validateMapValues(model.longMap, HttpModelType.PARAMETER, "longMap");
        longMapMinSizeMinSizeMapConstraintValidator.validate(model.longMap, HttpModelType.PARAMETER, "longMap");
        longMapSizeSizeMapConstraintValidator.validate(model.longMap, HttpModelType.PARAMETER, "longMap");

        requiredMapConstraintValidator.validate(model.charMap, HttpModelType.PARAMETER, "charMap");
        requiredConstraintValidator.validateMapValues(model.charMap, HttpModelType.PARAMETER, "charMap");
        charMapEnumerationEnumerationCharacterConstraintValidator.validateMapValues(model.charMap, HttpModelType.PARAMETER, "charMap");
        charMapMaxSizeMaxSizeMapConstraintValidator.validate(model.charMap, HttpModelType.PARAMETER, "charMap");
        charMapMinSizeMinSizeMapConstraintValidator.validate(model.charMap, HttpModelType.PARAMETER, "charMap");
        charMapSizeSizeMapConstraintValidator.validate(model.charMap, HttpModelType.PARAMETER, "charMap");

        requiredMapConstraintValidator.validate(model.floatMap, HttpModelType.PARAMETER, "floatMap");
        requiredConstraintValidator.validateMapValues(model.floatMap, HttpModelType.PARAMETER, "floatMap");
        floatMapMaxDoubleMaxFloatConstraintValidator.validateMapValues(model.floatMap, HttpModelType.PARAMETER, "floatMap");
        floatMapMaxSizeMaxSizeMapConstraintValidator.validate(model.floatMap, HttpModelType.PARAMETER, "floatMap");
        floatMapMinDoubleMinFloatConstraintValidator.validateMapValues(model.floatMap, HttpModelType.PARAMETER, "floatMap");
        floatMapMinSizeMinSizeMapConstraintValidator.validate(model.floatMap, HttpModelType.PARAMETER, "floatMap");
        floatMapSizeSizeMapConstraintValidator.validate(model.floatMap, HttpModelType.PARAMETER, "floatMap");

        requiredMapConstraintValidator.validate(model.doubleMap, HttpModelType.PARAMETER, "doubleMap");
        requiredConstraintValidator.validateMapValues(model.doubleMap, HttpModelType.PARAMETER, "doubleMap");
        doubleMapMaxDoubleMaxDoubleConstraintValidator.validateMapValues(model.doubleMap, HttpModelType.PARAMETER, "doubleMap");
        doubleMapMaxSizeMaxSizeMapConstraintValidator.validate(model.doubleMap, HttpModelType.PARAMETER, "doubleMap");
        doubleMapMinDoubleMinDoubleConstraintValidator.validateMapValues(model.doubleMap, HttpModelType.PARAMETER, "doubleMap");
        doubleMapMinSizeMinSizeMapConstraintValidator.validate(model.doubleMap, HttpModelType.PARAMETER, "doubleMap");
        doubleMapSizeSizeMapConstraintValidator.validate(model.doubleMap, HttpModelType.PARAMETER, "doubleMap");

        requiredMapConstraintValidator.validate(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        requiredConstraintValidator.validateMapValues(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapLatLatConstraintValidator.validateMapValues(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapLngLngConstraintValidator.validateMapValues(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapMaxNumberMaxBigDecimalNumberConstraintValidator.validateMapValues(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapMaxSizeMaxSizeMapConstraintValidator.validate(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapMinNumberMinBigDecimalNumberConstraintValidator.validateMapValues(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapMinSizeMinSizeMapConstraintValidator.validate(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapNumericNumericConstraintValidator.validateMapValues(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");
        decimalMapSizeSizeMapConstraintValidator.validate(model.decimalMap, HttpModelType.PARAMETER, "decimalMap");

        requiredMapConstraintValidator.validate(model.bigIntegerMap, HttpModelType.PARAMETER, "bigIntegerMap");
        requiredConstraintValidator.validateMapValues(model.bigIntegerMap, HttpModelType.PARAMETER, "bigIntegerMap");
        bigIntegerMapMaxNumberMaxBigIntegerNumberConstraintValidator.validateMapValues(model.bigIntegerMap, HttpModelType.PARAMETER, "bigIntegerMap");
        bigIntegerMapMaxSizeMaxSizeMapConstraintValidator.validate(model.bigIntegerMap, HttpModelType.PARAMETER, "bigIntegerMap");
        bigIntegerMapMinNumberMinBigIntegerNumberConstraintValidator.validateMapValues(model.bigIntegerMap, HttpModelType.PARAMETER, "bigIntegerMap");
        bigIntegerMapMinSizeMinSizeMapConstraintValidator.validate(model.bigIntegerMap, HttpModelType.PARAMETER, "bigIntegerMap");
        bigIntegerMapSizeSizeMapConstraintValidator.validate(model.bigIntegerMap, HttpModelType.PARAMETER, "bigIntegerMap");

        requiredMapConstraintValidator.validate(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        requiredConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapBase64URLEncodedBase64URLEncodedConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapCountryCodeCountryCodeConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapEmailEmailConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapEnumerationEnumerationStringConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapHostNameHostNameConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapIPIPConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapLatinAlphabetOnlyLatinAlphabetOnlyConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapLengthLengthConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        lowercaseConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapMaxLengthMaxLengthConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapMaxSizeMaxSizeMapConstraintValidator.validate(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapMinLengthMinLengthConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapMinSizeMinSizeMapConstraintValidator.validate(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapPatternPatternConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapPhonePhoneConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapSizeSizeMapConstraintValidator.validate(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        skypeConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapTelegramTelegramConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        uRIConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        uRLEncodedConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        uppercaseConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapViberViberConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");
        stringMapWhatsAppWhatsAppConstraintValidator.validateMapValues(model.stringMap, HttpModelType.PARAMETER, "stringMap");

        requiredMapConstraintValidator.validate(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        requiredConstraintValidator.validateMapValues(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        futureInstantConstraintValidator.validateMapValues(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        futureOrPresentInstantConstraintValidator.validateMapValues(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        instantMapMaxSizeMaxSizeMapConstraintValidator.validate(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        instantMapMinSizeMinSizeMapConstraintValidator.validate(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        pastInstantConstraintValidator.validateMapValues(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        pastOrPresentInstantConstraintValidator.validateMapValues(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        instantMapSizeSizeMapConstraintValidator.validate(model.instantMap, HttpModelType.PARAMETER, "instantMap");
        instantMapTruncatedTimeTruncatedTimeInstantConstraintValidator.validateMapValues(model.instantMap, HttpModelType.PARAMETER, "instantMap");

        requiredMapConstraintValidator.validate(model.colorMap, HttpModelType.PARAMETER, "colorMap");
        requiredConstraintValidator.validateMapValues(model.colorMap, HttpModelType.PARAMETER, "colorMap");
        colorMapMaxSizeMaxSizeMapConstraintValidator.validate(model.colorMap, HttpModelType.PARAMETER, "colorMap");
        colorMapMinSizeMinSizeMapConstraintValidator.validate(model.colorMap, HttpModelType.PARAMETER, "colorMap");
        colorMapSizeSizeMapConstraintValidator.validate(model.colorMap, HttpModelType.PARAMETER, "colorMap");
        colorMapSubEnumSubEnumConstraintValidator.validateMapValues(model.colorMap, HttpModelType.PARAMETER, "colorMap");

        requiredAndNotEmptyStringConstraintValidator.validate(model.countryCodeAlpha2, HttpModelType.PARAMETER, "countryCodeAlpha2");
        countryCodeAlpha2CountryCodeCountryCodeConstraintValidator.validate(model.countryCodeAlpha2, HttpModelType.PARAMETER, "countryCodeAlpha2");

        requiredAndNotEmptyStringConstraintValidator.validate(model.countryCodeAlpha3, HttpModelType.PARAMETER, "countryCodeAlpha3");
        countryCodeAlpha3CountryCodeCountryCodeConstraintValidator.validate(model.countryCodeAlpha3, HttpModelType.PARAMETER, "countryCodeAlpha3");

        requiredAndNotEmptyStringConstraintValidator.validate(model.countryCodeNumeric, HttpModelType.PARAMETER, "countryCodeNumeric");
        countryCodeNumericCountryCodeCountryCodeConstraintValidator.validate(model.countryCodeNumeric, HttpModelType.PARAMETER, "countryCodeNumeric");

        requiredAndNotEmptyStringConstraintValidator.validate(model.base64URLEncodedBase, HttpModelType.PARAMETER, "base64URLEncodedBase");
        base64URLEncodedBaseBase64URLEncodedBase64URLEncodedConstraintValidator.validate(model.base64URLEncodedBase, HttpModelType.PARAMETER, "base64URLEncodedBase");

        requiredAndNotEmptyStringConstraintValidator.validate(model.base64URLEncodedUrl, HttpModelType.PARAMETER, "base64URLEncodedUrl");
        base64URLEncodedUrlBase64URLEncodedBase64URLEncodedConstraintValidator.validate(model.base64URLEncodedUrl, HttpModelType.PARAMETER, "base64URLEncodedUrl");

        requiredAndNotEmptyStringConstraintValidator.validate(model.ip, HttpModelType.PARAMETER, "ip");
        ipIPIPConstraintValidator.validate(model.ip, HttpModelType.PARAMETER, "ip");

        requiredAndNotEmptyStringConstraintValidator.validate(model.ip4, HttpModelType.PARAMETER, "ip4");
        ip4IPIPConstraintValidator.validate(model.ip4, HttpModelType.PARAMETER, "ip4");

        requiredAndNotEmptyStringConstraintValidator.validate(model.ip6, HttpModelType.PARAMETER, "ip6");
        ip6IPIPConstraintValidator.validate(model.ip6, HttpModelType.PARAMETER, "ip6");

        requiredConstraintValidator.validate(model.lat111km, HttpModelType.PARAMETER, "lat111km");
        lat111kmLatLatConstraintValidator.validate(model.lat111km, HttpModelType.PARAMETER, "lat111km");

        requiredConstraintValidator.validate(model.lng111km, HttpModelType.PARAMETER, "lng111km");
        lng111kmLngLngConstraintValidator.validate(model.lng111km, HttpModelType.PARAMETER, "lng111km");

        requiredConstraintValidator.validate(model.lat11km, HttpModelType.PARAMETER, "lat11km");
        lat11kmLatLatConstraintValidator.validate(model.lat11km, HttpModelType.PARAMETER, "lat11km");

        requiredConstraintValidator.validate(model.lng11km, HttpModelType.PARAMETER, "lng11km");
        lng11kmLngLngConstraintValidator.validate(model.lng11km, HttpModelType.PARAMETER, "lng11km");

        requiredConstraintValidator.validate(model.lat1km, HttpModelType.PARAMETER, "lat1km");
        lat1kmLatLatConstraintValidator.validate(model.lat1km, HttpModelType.PARAMETER, "lat1km");

        requiredConstraintValidator.validate(model.lng1km, HttpModelType.PARAMETER, "lng1km");
        lng1kmLngLngConstraintValidator.validate(model.lng1km, HttpModelType.PARAMETER, "lng1km");

        requiredConstraintValidator.validate(model.lat111m, HttpModelType.PARAMETER, "lat111m");
        lat111mLatLatConstraintValidator.validate(model.lat111m, HttpModelType.PARAMETER, "lat111m");

        requiredConstraintValidator.validate(model.lng111m, HttpModelType.PARAMETER, "lng111m");
        lng111mLngLngConstraintValidator.validate(model.lng111m, HttpModelType.PARAMETER, "lng111m");

        requiredConstraintValidator.validate(model.lat11m, HttpModelType.PARAMETER, "lat11m");
        lat11mLatLatConstraintValidator.validate(model.lat11m, HttpModelType.PARAMETER, "lat11m");

        requiredConstraintValidator.validate(model.lng11m, HttpModelType.PARAMETER, "lng11m");
        lng11mLngLngConstraintValidator.validate(model.lng11m, HttpModelType.PARAMETER, "lng11m");

        requiredConstraintValidator.validate(model.lat1m, HttpModelType.PARAMETER, "lat1m");
        lat1mLatLatConstraintValidator.validate(model.lat1m, HttpModelType.PARAMETER, "lat1m");

        requiredConstraintValidator.validate(model.lng1m, HttpModelType.PARAMETER, "lng1m");
        lng1mLngLngConstraintValidator.validate(model.lng1m, HttpModelType.PARAMETER, "lng1m");

        requiredConstraintValidator.validate(model.lat11cm, HttpModelType.PARAMETER, "lat11cm");
        lat11cmLatLatConstraintValidator.validate(model.lat11cm, HttpModelType.PARAMETER, "lat11cm");

        requiredConstraintValidator.validate(model.lng11cm, HttpModelType.PARAMETER, "lng11cm");
        lng11cmLngLngConstraintValidator.validate(model.lng11cm, HttpModelType.PARAMETER, "lng11cm");

        requiredConstraintValidator.validate(model.lat1cm, HttpModelType.PARAMETER, "lat1cm");
        lat1cmLatLatConstraintValidator.validate(model.lat1cm, HttpModelType.PARAMETER, "lat1cm");

        requiredConstraintValidator.validate(model.lng1cm, HttpModelType.PARAMETER, "lng1cm");
        lng1cmLngLngConstraintValidator.validate(model.lng1cm, HttpModelType.PARAMETER, "lng1cm");
    }
}
