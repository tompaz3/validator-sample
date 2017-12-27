package com.tp.samples.validator;

@FunctionalInterface
public interface ValidationRule<T, K> {
    /**
     * Validates given object returning the validation result.
     *
     * @param t object to be validated.
     * @return validation result.
     */
    K validate(T t);

    /**
     * Returns the information whether validator should stop executing
     * further validation rules and fail, whenever this rule fails.
     * <p>
     * Returns {@code false} by default.
     *
     * @return boolean.
     */
    default boolean isFailFast() {
        return false;
    }
}