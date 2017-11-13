package com.tp.samples.validator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Validator. Performs validation using registered rules and returns all their results.
 *
 * @param <T> validated object type.
 * @param <K> validation result type.
 */
public class Validator<T, K> {

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
        List<K> results = new LinkedList<>();
        rules.forEach(r -> results.add(r.validate(t)));
        return results;
    }
}
