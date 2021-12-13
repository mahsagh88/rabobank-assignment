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
@Document(collection = "accounts")
public class AccountData {

	@Id
	private String accountNumber;
	@NonNull
	private UserData accountHolderName;
	
	private Double balance;
	@NonNull
	private String accountType;
    
}
