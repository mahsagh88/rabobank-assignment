package nl.rabobank.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import nl.rabobank.exception.NotFoundException;
import nl.rabobank.model.UserModel;

/**
 * @author Provide
 *
 * 
 */
@SpringBootTest
@ComponentScan(basePackages ={"nl.rabobank.service","nl.rabobank.validator"})
@TestInstance(PER_CLASS)
class UserServiceTest {
	@Autowired
	 private UserServices userService;
	
	@Test
	public void inserUserTest() {
    	UserModel userModel = new UserModel("1", "Mahsa");
        userService.save(userModel);
        UserModel user1 = userService.findById("1");
        Assert.assertEquals(user1.getUserId(),userModel.getUserId());
        Assert.assertEquals(user1.getUserName(),userModel.getUserName());
    }
	
	@Test
	public void inserUserExceptionTest() {
    	UserModel userModel = new UserModel("2", "Mona");
        userService.save(userModel);
        UserModel userModel1 = new UserModel("2", "Pari");
        Exception exception = assertThrows(RuntimeException.class, () -> userService.save(userModel1));

		String expectedMessage = "User with id: 2 is already exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
        
    }
    
    @Test
    public void findUserTest() {
    	UserModel userModel = new UserModel("3", "Pari");
    	userService.save(userModel);
        UserModel user = userService.findById("3");
        Assert.assertEquals(user.getUserId(),userModel.getUserId());
        Assert.assertEquals(user.getUserName(),userModel.getUserName());
    }
    
    @Test
    public void findUserExceptionTest() {
    	UserModel userModel = new UserModel("4", "Kimi");
    	userService.save(userModel);
    	Exception exception = assertThrows(NotFoundException.class, () -> userService.findById("5"));

		String expectedMessage = "User with id: 5 not found";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
    }
        
}
