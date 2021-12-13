package nl.rabobank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import nl.rabobank.account.SavingsAccount;
import nl.rabobank.model.UserModel;
import nl.rabobank.service.AccountServices;

/**
 * @author Provide
 *
 * 
 */
@SpringBootTest
@ComponentScan(basePackages ={"nl.rabobank.service","nl.rabobank.validator"})
public class RaboAssignmentApplicationTest {


	    @Test
	    void contextLoads() {
	    }

	    @Bean
	    public org.springframework.validation.Validator validatorFactory () {
	        return new LocalValidatorFactoryBean();
	    }
		

}
