package nl.rabobank.validator;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
public class ObjectValidator {

    private final LocalValidatorFactoryBean validator;

    @Autowired
    protected ObjectValidator(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    private static String extractMessages(final Set<ConstraintViolation<Object>> constraintViolations) {
        return constraintViolations
                .stream()
                .map(ObjectValidator::getMessage)
                .collect(Collectors.joining(", "));
    }

    private static String getMessage(ConstraintViolation<Object> v) {
        return v.getPropertyPath() + " " + v.getMessage();
    }

    protected String validateAndReturnErrorMessage(final Object object) {

        if (object == null) {
            return "";
        }

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            return extractMessages(constraintViolations);
        }
        return "";
    }

    protected String validateAndReturnErrorMessage(final Object[] objects) {

        if (objects == null || objects.length == 0) {
            return "";
        }

        final StringBuilder allErrorMessages = new StringBuilder();
        for (final Object object : objects) {
            final String errorMessage = validateAndReturnErrorMessage(object);
            if (StringUtils.isNotBlank(errorMessage)) {
                allErrorMessages.append(errorMessage).append(", ");
            }
        }
        return allErrorMessages.toString();
    }

    protected String validateAndReturnErrorMessage(final Collection<Object> objects) {

        if (CollectionUtils.isEmpty(objects)) {
            return "";
        }
        return validateAndReturnErrorMessage(objects.toArray());
    }

    protected void validateAndThrowConstraintViolationException(final Object object) {

        final String errorMessage = validateAndReturnErrorMessage(object);
        if (StringUtils.isNotBlank(errorMessage)) {
            throw new ConstraintViolationException(errorMessage, null);
        }

    }

    protected void validateAndThrowConstraintViolationException(final Object[] objects) {

        if (objects == null || objects.length == 0) {
            return;
        }

        final String errorMessage = validateAndReturnErrorMessage(objects);
        if (StringUtils.isNotBlank(errorMessage)) {
            throw new ConstraintViolationException(errorMessage, null);
        }

    }

    protected void validateAndThrowConstraintViolationException(final Collection<Object> objects) {

        if (CollectionUtils.isEmpty(objects)) {
            return;
        }

        validateAndThrowConstraintViolationException(objects.toArray());
    }
}
