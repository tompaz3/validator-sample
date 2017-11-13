package com.tp.samples.validator;

@FunctionalInterface
public interface Validation {
    /**
     * Returns the information whether object has been successfully validated or not.
     *
     * @return {@code true} if object has been successfully validated, {@code false} otherwise.
     */
    Boolean isValid();
}
