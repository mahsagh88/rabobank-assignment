package nl.rabobank.model;

import org.springframework.data.annotation.Id;

import lombok.Value;

/**
 * @author Provide
 *
 * 
 */
@Value
public class UserModel {
	@Id
	String userId;
	String userName;
	
}
