package nl.rabobank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Provide
 *
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserData {

	@Id
	private String userId;
	private String userName;
	
}
