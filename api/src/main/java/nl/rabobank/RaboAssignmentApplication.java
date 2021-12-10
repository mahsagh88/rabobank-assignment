package nl.rabobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import nl.rabobank.mongo.MongoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@EntityScan("nl.rabobank.model")
@EnableMongoRepositories("nl.rabobank.repository")
@ComponentScan(basePackages ={"nl.rabobank.controller","nl.rabobank.service","nl.rabobank.validator"})
@Import(MongoConfiguration.class)
public class RaboAssignmentApplication
{
    public static void main(final String[] args)
    {
    	
        SpringApplication.run(RaboAssignmentApplication.class, args);
    }
    @Bean
    public org.springframework.validation.Validator validatorFactory () {
        return new LocalValidatorFactoryBean();
    }

}
