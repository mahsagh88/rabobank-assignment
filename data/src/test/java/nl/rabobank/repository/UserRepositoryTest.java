package nl.rabobank.repository;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;

import nl.rabobank.model.UserData;
import nl.rabobank.mongo.MongoConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@ContextConfiguration
@EnableMongoRepositories("nl.rabobank.repository")
public class UserRepositoryTest {
	
	 @Autowired
	 private UserRepository userRepository;

	    @Test
	    public void inserUTest() {
	    	UserData userData = new UserData("1", "mahsa");
	        userRepository.save(userData);
	    }
	    
	    @Test
	    public void findUserTest() {
	    	UserData userData = new UserData("1", "mahsa");
	    	userRepository.save(userData);
	        UserData user = userRepository.findById("1").get();
	        Assert.assertEquals(user.getUserId(),userData.getUserId());
	        Assert.assertEquals(user.getUserName(),userData.getUserName());
	    }
	        
}
