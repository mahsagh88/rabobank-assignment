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
@Document(collection = "accounts")
public class AccountData {

	@Id
	private String accountNumber;
	private UserData accountHolderName;
	private Double balance;
	private String accountType;
    
}
