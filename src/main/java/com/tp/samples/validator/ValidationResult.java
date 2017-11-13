package com.tp.samples.validator;

import java.util.Optional;

public interface ValidationResult extends Validation {
    /**
     * Default success result - contains no reason.
     */
    ValidationResult SUCCESS = of(null, Boolean.TRUE);

    /**
     * Method returning validation failure reason in form of {@link Optional}.
     *
     * @return validation failure reason.
     */
    Optional<String> getReason();

    /**
     * Convenience method for creating validation result instance
     * with given validation failure reason.
     *
     * @param reason validation failure reason.
     * @return validation failure result.
     */
    static ValidationResult invalid(String reason) {
        return of(reason, Boolean.FALSE);
    }

    /**
     * Convenience method for creating validation result instance
     * with given validation failure reason and information
     * whether the validation was successful or not.
     *
     * @param reason validation failure reason.
     * @param valid  value informing whether the validation was successful or not.
     * @return validation result.
     */
    static ValidationResult of(String reason, Boolean valid) {
        return new ValidationResult() {
            @Override
            public Optional<String> getReason() {
                return Optional.ofNullable(reason);
            }

            @Override
            public Boolean isValid() {
                return valid;
            }
        };
    }
}
