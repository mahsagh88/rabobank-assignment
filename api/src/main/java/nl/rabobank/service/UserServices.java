package nl.rabobank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.exception.NotFoundException;
import nl.rabobank.model.UserData;
import nl.rabobank.model.UserModel;
import nl.rabobank.repository.UserRepository;
import nl.rabobank.validator.UserValidator;

/**
 * @author Provide
 *
 * 
 */
@Service
@Slf4j
public class UserServices {

	@Autowired
    private UserRepository userRepo;
	@Autowired
	private UserValidator userValidator;
	
	public void save(UserModel userModel) {
		log.debug("Start saving user [{}]", userModel);
		userValidator.validateAndThrowConstraintViolationException(userModel);
		if(userRepo.findById(userModel.getUserId()).isPresent()) {
            throw new RuntimeException("User with id: " + userModel.getUserId() + " is already exist");
    	}
		userRepo.save(new UserData(userModel.getUserId(), userModel.getUserName()));
		log.debug("Saved finished for user [{}]", userModel);
	}
    
    public void delete(String id)
    {
    	log.debug("Start deleting user with id [{}]", id);
    	Optional<UserData> userData = userRepo.findById(id);
    	if (!userData.isPresent()) {
            throw new NotFoundException("User with id: " + id + " not found");
        }
    	userData.ifPresent(userRepo::delete);
    	log.debug("Delete finished for user id: [{}]", id);
    }
  
    public List<UserModel> findAll()
    {
    	return userRepo.findAll().stream()
    	.map(u -> new UserModel (u.getUserId(), u.getUserName()))
    	.collect(Collectors.toList());
    }
    
    public UserModel findById(String userId) {
    	Optional<UserData> userData = userRepo.findById(userId);
    	if(!userData.isPresent()) {
            throw new NotFoundException("User with id: " + userId + " not found");
    	}
    	return userData.map(u -> new UserModel(u.getUserId(), u.getUserName())).orElse(null);
    }
    
}
