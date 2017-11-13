package com.tp.samples.validator;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * {@link FailFastValidator} test.
 */
public class FailFastValidatorTest {

    private static final ValidationResult INVALID_RESULT = ValidationResult.invalid("ERROR");

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFailOnFirstErrorAndStopFurtherValidation() {
        ValidationRule<Void, ValidationResult> invalidRule = Mockito.mock(ValidationRule.class);
        Mockito.when(invalidRule.validate(Mockito.any())).thenReturn(INVALID_RESULT);
        ValidationRule<Void, ValidationResult> validRule = Mockito.mock(ValidationRule.class);
        Mockito.when(validRule.validate(Mockito.any())).thenReturn(ValidationResult.SUCCESS);
        List<ValidationResult> result =
                new FailFastValidator<Void, ValidationResult>().add(validRule).add(validRule).add(invalidRule).add(invalidRule).add(invalidRule)
                        .validate(null);
        // result size should be 1
        Assertions.assertThat(result).hasSize(1);
        // validRule should be called twice
        Mockito.verify(validRule, Mockito.times(2)).validate(Mockito.any());
        // invalid rule should be called once
        Mockito.verify(invalidRule, Mockito.times(1)).validate(Mockito.any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnEmptyListWhenAllResultsAreValid() {
        ValidationRule<Void, ValidationResult> validRule = Mockito.mock(ValidationRule.class);
        Mockito.when(validRule.validate(Mockito.any())).thenReturn(ValidationResult.SUCCESS);

        List<ValidationResult> result = new FailFastValidator<Void, ValidationResult>().add(validRule).add(validRule).add(validRule).validate(null);
        // result should be empty
        Assertions.assertThat(result).isEmpty();
        // valid rule should be called three times
        Mockito.verify(validRule, Mockito.times(3)).validate(Mockito.any());
    }
}
