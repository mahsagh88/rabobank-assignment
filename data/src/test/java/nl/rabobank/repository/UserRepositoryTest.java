package nl.rabobank.repository;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import nl.rabobank.model.UserData;
import nl.rabobank.mongo.MongoConfiguration;

@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@EnableMongoRepositories("nl.rabobank.repository")
public class UserRepositoryTest {
	
	 @Autowired
	 private UserRepository userRepository;

	    @Test
	    public void inserUTest() {
	    	UserData userData = new UserData("1", "mahsa");
	        userRepository.save(userData);
	        UserData user1 = userRepository.findById("1").get();
	        Assert.assertEquals(user1.getUserId(),userData.getUserId());
	        Assert.assertEquals(user1.getUserName(),userData.getUserName());
	    }
	    
	    @Test
	    public void findUserTest() {
	    	UserData userData = new UserData("2", "Mona");
	    	userRepository.save(userData);
	        UserData user = userRepository.findById("2").get();
	        Assert.assertEquals(user.getUserId(),userData.getUserId());
	        Assert.assertEquals(user.getUserName(),userData.getUserName());
	    }
	        
}
