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
}