package nl.rabobank.validator;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import nl.rabobank.account.Account;
import nl.rabobank.model.AccountDto;

@Component
public class AccountValidator extends ObjectValidator {

    public static final String EMPTY_ACCOUNT_NUMBER = "Account number cannot be null or empty.";
    public static final String EMPTY_ACCOUNT_HOLDER_NAME = "Account holderName cannot be null or empty.";

    @Autowired
    public AccountValidator(LocalValidatorFactoryBean validator) {
        super(validator);
    }

    public void validateAndThrowConstraintViolationException(final @Valid AccountDto accountDto) {

        if (StringUtils.isEmpty(accountDto.getAccountNumber())) {
            throw new ConstraintViolationException(EMPTY_ACCOUNT_NUMBER, null);
        }
        if (StringUtils.isEmpty(accountDto.getAccountHolderName())) {
			throw new ConstraintViolationException(EMPTY_ACCOUNT_HOLDER_NAME, null);
        }
        super.validateAndThrowConstraintViolationException(accountDto);

    }
    public void validateAndThrowConstraintViolationException(final @Valid Account account) {

        if (StringUtils.isEmpty(account.getAccountNumber())) {
            throw new ConstraintViolationException(EMPTY_ACCOUNT_NUMBER, null);
        }
        if (StringUtils.isEmpty(account.getAccountHolderName())) {
            throw new ConstraintViolationException(EMPTY_ACCOUNT_HOLDER_NAME, null);
        }
        //super.validateAndThrowConstraintViolationException(accountDto);

    }

}
