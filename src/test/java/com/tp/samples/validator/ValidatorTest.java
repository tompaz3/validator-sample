package com.tp.samples.validator;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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
}
