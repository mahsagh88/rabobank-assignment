package nl.rabobank.service;

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

import nl.rabobank.model.UserModel;
import nl.rabobank.mongo.MongoConfiguration;

/**
 * @author Provide
 *
 * 
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@ContextConfiguration
@EnableMongoRepositories("nl.rabobank.repository")
class UserServiceTest {
	@Autowired
	 private UserServices userService;
	
	@Test
	public void inserUserTest() {
    	UserModel userModel = new UserModel("1", "mahsa");
        userService.save(userModel);
    }
    
    @Test
    public void findUserTest() {
    	UserModel userModel = new UserModel("1", "mahsa");
    	userService.save(userModel);
        UserModel user = userService.findById("111");
        Assert.assertEquals(user.getUserId(),userModel.getUserId());
        Assert.assertEquals(user.getUserName(),userModel.getUserName());
    }
        
}