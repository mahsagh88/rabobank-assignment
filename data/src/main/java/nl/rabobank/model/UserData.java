package nl.rabobank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
	@NonNull
	private String userName;
	
}
