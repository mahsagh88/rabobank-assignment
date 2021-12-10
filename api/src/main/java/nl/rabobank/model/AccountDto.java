package nl.rabobank.model;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Provide
 *
 * 
 */

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	
	String accountNumber;
    String accountHolderName;
    Double balance;
    String accountType;
    
  
}
