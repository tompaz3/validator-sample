package com.tp.samples.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator. Performs validation using registered rules and returns the first invalid result.
 *
 * @param <T> validated object type.
 * @param <K> validation result type.
 * @deprecated use {@link Validator#validateFailFast(Object)} instead.
 */
@Deprecated
public class FailFastValidator<T, K extends Validation> extends Validator<T, K> {

    /**
     * Validates given object using registered validation rules
     * applying 'fail-fast' execution policy.
     * Returns first found invalid result and does not perform any further validation.
     *
     * @param t object to be validated.
     * @return first invalid validation result found.
     * Returns {@code null} if no invalid results are found.
     */
    @Override
    public List<K> validate(T t) {
        List<K> results = new ArrayList<>(1);
        for (ValidationRule<T, K> rule : rules) {
            K result = rule.validate(t);
            if (!Boolean.TRUE.equals(result.isValid())) {
                results.add(result);
                break;
            }
        }
        return results;
    }
}
