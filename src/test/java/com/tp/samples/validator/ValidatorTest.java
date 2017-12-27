package com.tp.samples.validator;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * {@link Validator} test.
 */
public class ValidatorTest {

    private static final ValidationResult INVALID_RESULT = ValidationResult.invalid("ERROR");
    private static final ValidationRule<Void, ValidationResult> INVALID_RULE = (data) -> INVALID_RESULT;
    private static final ValidationRule<Void, ValidationResult> VALID_RULE = (data) -> ValidationResult.SUCCESS;

    @Test
    public void shouldReturnAllValidationResults() {
        List<ValidationResult> result =
                new Validator<Void, ValidationResult>().add(VALID_RULE).add(INVALID_RULE).add(VALID_RULE).add(INVALID_RULE).add(INVALID_RULE).validate(null);

        Assertions.assertThat(result)
                .hasSize(5) // result should contain 5 entries
                .contains(ValidationResult.SUCCESS) // result should contain success
                .contains(INVALID_RESULT); // result should contain invalid
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFailFast() {
        ValidationRule<Void, ValidationResult> invalidRule = Mockito.mock(ValidationRule.class);
        Mockito.when(invalidRule.validate(Mockito.any())).thenReturn(INVALID_RESULT);
        ValidationRule<Void, ValidationResult> validRule = Mockito.mock(ValidationRule.class);
        Mockito.when(validRule.validate(Mockito.any())).thenReturn(ValidationResult.SUCCESS);

        List<ValidationResult> result =
                new Validator<Void, ValidationResult>().add(validRule).add(validRule).add(invalidRule).add(invalidRule).add(invalidRule)
                        .validateFailFast(null);
        // result size should be 1
        Assertions.assertThat(result).hasSize(1);
        // validRule should be called twice
        Mockito.verify(validRule, Mockito.times(2)).validate(Mockito.any());
        // invalid rule should be called once
        Mockito.verify(invalidRule, Mockito.times(1)).validate(Mockito.any());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAllResultsUpToFailFastRule() {
        ValidationRule<Void, ValidationResult> invalidRule = Mockito.mock(ValidationRule.class);
        Mockito.when(invalidRule.validate(Mockito.any())).thenReturn(INVALID_RESULT);
        ValidationRule<Void, ValidationResult> invalidFailFastRule = Mockito.mock(ValidationRule.class);
        Mockito.when(invalidFailFastRule.validate(Mockito.any())).thenReturn(INVALID_RESULT);
        Mockito.when(invalidFailFastRule.isFailFast()).thenReturn(true);
        ValidationRule<Void, ValidationResult> validRule = Mockito.mock(ValidationRule.class);
        Mockito.when(validRule.validate(Mockito.any())).thenReturn(ValidationResult.SUCCESS);

        List<ValidationResult> result =
                new Validator<Void, ValidationResult>().add(validRule).add(invalidRule).add(validRule).add(invalidFailFastRule)
                        .add(invalidFailFastRule).add(invalidRule).add(validRule)
                        .validate(null);
        // result size should be 4
        Assertions.assertThat(result).hasSize(4);
        // valid rule should be called twice
        Mockito.verify(validRule, Mockito.times(2)).validate(Mockito.any());
        // invalid rule should be called once
        Mockito.verify(invalidRule, Mockito.times(1)).validate(Mockito.any());
        // invalid fail fast rule should be called once
        Mockito.verify(invalidFailFastRule, Mockito.times(1)).validate(Mockito.any());
    }
}
