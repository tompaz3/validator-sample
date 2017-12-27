package com.tp.samples.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Validator. Performs validation using registered rules and returns their results.
 *
 * @param <T> validated object type.
 * @param <K> validation result type.
 */
public class Validator<T, K extends Validation> {

    protected final List<ValidationRule<T, K>> rules;

    public Validator() {
        this.rules = new LinkedList<>();
    }

    /**
     * Adds validation rule to the current validator.
     *
     * @param rule validation rule.
     * @return validator instance.
     */
    public Validator<T, K> add(ValidationRule<T, K> rule) {
        this.rules.add(rule);
        return this;
    }

    /**
     * Adds validation rules to the current validator.
     *
     * @param rules validation rules.
     * @return validator instance.
     */
    public Validator<T, K> addAll(Collection<ValidationRule<T, K>> rules) {
        this.rules.addAll(rules);
        return this;
    }

    /**
     * Adds validation rules to the current validator.
     *
     * @return validator instance.
     */
    public Validator<T, K> addAll(Validator<T, K> validator) {
        this.rules.addAll(validator.getRules());
        return this;
    }

    /**
     * Returns assigned validation rules.
     *
     * @return validation rules.
     */
    public List<ValidationRule<T, K>> getRules() {
        return rules;
    }

    /**
     * Validates given object using registered validation rules.
     *
     * @param t object to be validated.
     * @return validation result list.
     */
    public List<K> validate(T t) {
        List<K> results = new ArrayList<>(1);
        for (ValidationRule<T, K> rule : rules) {
            K result = rule.validate(t);
            results.add(result);
            if (rule.isFailFast() && !Boolean.TRUE.equals(result.isValid())) {
                break;
            }
        }
        return results;
    }

    /**
     * Validates given object using registered validation rules
     * applying 'fail-fast' execution policy.
     * Returns first found invalid result and does not perform any further validation.
     *
     * @param t object to be validated.
     * @return first invalid validation result found.
     * Returns {@code null} if no invalid results are found.
     */
    public List<K> validateFailFast(T t) {
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
