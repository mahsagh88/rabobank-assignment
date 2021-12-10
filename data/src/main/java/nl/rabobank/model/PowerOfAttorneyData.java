package nl.rabobank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author Provide
 *
 * 
 */
@Document(collection = "powerofattorney")
public class PowerOfAttorneyData {

	
	@Id
    private String id;
 	private UserData granteeName;
    private UserData grantorName;
    private AccountData accountNumber;
	private String authorization;
	
	public PowerOfAttorneyData(UserData granteeName, UserData grantorName, AccountData accountNumber,
			String authorization) {
		super();
		this.granteeName = granteeName;
		this.grantorName = grantorName;
		this.accountNumber = accountNumber;
		this.authorization = authorization;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserData getGranteeName() {
		return granteeName;
	}
	public void setGranteeName(UserData granteeName) {
		this.granteeName = granteeName;
	}
	public UserData getGrantorName() {
		return grantorName;
	}
	public void setGrantorName(UserData grantorName) {
		this.grantorName = grantorName;
	}
	public AccountData getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(AccountData accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	
	
}
