package nl.rabobank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.model.UserDto;
import nl.rabobank.model.UserModel;
import nl.rabobank.service.UserServices;
import nl.rabobank.validator.UserValidator;

/**
 * @author Provide
 *
 * 
 */
@RestController
@Slf4j
public class UserController {
	
	
	private final UserServices userService;
	private final UserValidator userValidator;
	
	@Autowired
	public UserController(UserValidator userValidator, UserServices userServices) {
		super();
		this.userValidator = userValidator;
		this.userService = userServices;
	}

	@GetMapping(value = "/findAllUsers")
    public @ResponseBody List<UserDto> showUsers()
    {
		return userService.findAll().stream()
			.map(u -> new UserDto(u.getUserId(), u.getUserName()))
			.collect(Collectors.toList());
    }
    
    @PostMapping(value = "/saveUser")
    public void save(@RequestBody UserDto userDto)
    {
    	log.info("Start saving user [{}]", userDto);
    	userValidator.validateAndThrowConstraintViolationException(userDto);
    	userService.save(new UserModel(userDto.getUserId(), userDto.getUserName()));
        log.info("Saved finished for user [{}]", userDto);
    }
    
    @DeleteMapping(value = "/deleteUser/{id}")
    public void delete(@PathVariable("id") String id)
    { 
    	log.info("Start deleting user with id [{}]", id);
        userService.delete(id);
        log.info("Delete Finished for user id [{}]", id);
    }

    @GetMapping(value = "/findUserById/{id}")
    public @ResponseBody UserDto findByUserId(@PathVariable("id") String userId)
    {
    	UserModel userModel = userService.findById(userId);
    	return new UserDto(userModel.getUserId(), userModel.getUserName());
    }
    
}
