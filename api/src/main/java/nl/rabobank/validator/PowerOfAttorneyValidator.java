package nl.rabobank.validator;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.model.PowerOfAttorneyDto;

@Component
public class PowerOfAttorneyValidator extends ObjectValidator {

    public static final String EMPTY_ACCOUNT_NUMBER = "account number cannot be null or empty.";
    public static final String EMPTY_GRANTOR_NAME = "grantor name cannot be null or empty.";
    public static final String EMPTY_GRANTEE_NAME = "grantee name cannot be null or empty.";

    @Autowired
    public PowerOfAttorneyValidator(LocalValidatorFactoryBean validator) {
        super(validator);
    }

    public void validateAndThrowConstraintViolationException(final @Valid PowerOfAttorneyDto powerDto) {

        if (StringUtils.isEmpty(powerDto.getAccountNumber())) {
            throw new ConstraintViolationException(EMPTY_ACCOUNT_NUMBER, null);
        }
        if (StringUtils.isEmpty(powerDto.getGrantorName())) {
            throw new ConstraintViolationException(EMPTY_GRANTOR_NAME, null);
        }
        if (StringUtils.isEmpty(powerDto.getGranteeName())) {
        	throw new ConstraintViolationException(EMPTY_GRANTEE_NAME, null);
        }
        super.validateAndThrowConstraintViolationException(powerDto);
    }
    public void validateAndThrowConstraintViolationException(final @Valid PowerOfAttorney powerOfAttorney) {

        if (StringUtils.isEmpty(powerOfAttorney.getGrantorName())) {
            throw new ConstraintViolationException(EMPTY_GRANTOR_NAME, null);
        }
    	if (ObjectUtils.isEmpty(powerOfAttorney.getAccountNumber().getAccountNumber())) {
    		throw new ConstraintViolationException(EMPTY_ACCOUNT_NUMBER, null);
    	}
    	if (StringUtils.isEmpty(powerOfAttorney.getAccountNumber().getAccountNumber())) {
    		throw new ConstraintViolationException(EMPTY_ACCOUNT_NUMBER, null);
    	}

        if (StringUtils.isEmpty(powerOfAttorney.getGranteeName())) {
        	throw new ConstraintViolationException(EMPTY_GRANTEE_NAME, null);
        }
        super.validateAndThrowConstraintViolationException(powerOfAttorney);

    }

}
