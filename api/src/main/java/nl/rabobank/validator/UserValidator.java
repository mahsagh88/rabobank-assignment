package nl.rabobank.validator;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import nl.rabobank.model.UserDto;
import nl.rabobank.model.UserModel;

@Component
public class UserValidator extends ObjectValidator {

    public static final String EMPTY_USER_ID = "User id cannot be null or empty.";

    @Autowired
    public UserValidator(LocalValidatorFactoryBean validator) {
        super(validator);
    }

    public void validateAndThrowConstraintViolationException(final @Valid UserDto userDto) {

        if (StringUtils.isEmpty(userDto.getUserId())) {
            throw new ConstraintViolationException(EMPTY_USER_ID, null);
        }
        super.validateAndThrowConstraintViolationException(userDto);
    }
    
    public void validateAndThrowConstraintViolationException(final @Valid UserModel userModel) {

        if (StringUtils.isEmpty(userModel.getUserId())) {
            throw new ConstraintViolationException(EMPTY_USER_ID, null);
        }
        super.validateAndThrowConstraintViolationException(userModel);

    }

}
