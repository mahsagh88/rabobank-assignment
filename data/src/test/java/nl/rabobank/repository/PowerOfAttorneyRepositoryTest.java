package nl.rabobank.repository;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import nl.rabobank.model.AccountData;
import nl.rabobank.model.AccountType;
import nl.rabobank.model.PowerOfAttorneyData;
import nl.rabobank.model.UserData;
import nl.rabobank.mongo.MongoConfiguration;

@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@EnableMongoRepositories("nl.rabobank.repository")
@Testable
public class PowerOfAttorneyRepositoryTest {
	
	 @Autowired
	 private PowerOfAttorneyRepository powerRepository;
	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private AccountRepository accountRepository;
	 
	 @BeforeAll
		public void setUp() {
			UserData userModel = new UserData("1", "Mahsa");
			userRepository.save(userModel);
			UserData userModel2 = new UserData("2", "Mona");
			userRepository.save(userModel2);
			UserData userModel3 = new UserData("3", "Pari");
			userRepository.save(userModel3);
			
			AccountData accountData = new AccountData("111", userModel, 10.65, AccountType.Saving.id);
			accountRepository.save(accountData);
			AccountData AccountData2 = new AccountData("222", userModel2, 13.65, AccountType.Payment.id);
			accountRepository.save(AccountData2);
			AccountData AccountData3 = new AccountData("333", userModel3, 10.665, AccountType.Saving.id);
			accountRepository.save(AccountData3);
	 }

	    @Test
	    public void inserPowerTest() {
	    	UserData userAccount = new UserData("1", "Mahsa");
	    	UserData userGrantor = new UserData("1", "Mahsa");
	    	UserData userGrantee = new UserData("2", "Mona");
	        var account = new AccountData("111", userAccount, 10.65, AccountType.Saving.label);
	        PowerOfAttorneyData powerData = new PowerOfAttorneyData(userGrantee, userGrantor, account, "READ");
	        powerRepository.save(powerData);
	        List<PowerOfAttorneyData> power1 = powerRepository.findByAllData(userGrantor.getUserId(), userGrantee.getUserId(), account.getAccountNumber(), "READ");
	        Assert.assertEquals(powerData.getGrantorName(), power1.get(0).getGrantorName());
	        Assert.assertEquals(powerData.getGranteeName(), power1.get(0).getGranteeName());
	        Assert.assertEquals(powerData.getAccountNumber(), power1.get(0).getAccountNumber());
	        Assert.assertEquals(powerData.getAuthorization(), power1.get(0).getAuthorization());
	    }
	    
	    @Test
	    public void findPowerByAccountNumberTest() {
	    	UserData userAccount = new UserData("3", "Pari");
	    	UserData userGrantor = new UserData("3", "Pari");
	    	UserData userGrantee = new UserData("2", "Mona");
	    	var account = new AccountData("333", userAccount, 10.665, AccountType.Saving.id);
	    	PowerOfAttorneyData powerData = new PowerOfAttorneyData(userGrantee, userGrantor, account, "READ");
	        powerRepository.save(powerData);
	        List<PowerOfAttorneyData> powers = powerRepository.findByAccountNumber("333");
	        Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
	        Assert.assertEquals(powerData.getGranteeName(),powers.get(0).getGranteeName());
	        Assert.assertEquals(powerData.getGrantorName(),powers.get(0).getGrantorName());
	        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
	    }
	        
	    @Test
	    public void findByGrantorNameTest() {
	    	UserData userAccount = new UserData("1", "Mahsa");
	    	UserData userGrantor = new UserData("1", "Mahsa");
	    	UserData userGrantee = new UserData("2", "Mona");
	    	var account = new AccountData("111", userAccount, 10.65, AccountType.Saving.label);
	    	PowerOfAttorneyData powerData = new PowerOfAttorneyData(userGrantee, userGrantor, account, "READ");
	        powerRepository.save(powerData);
	        List<PowerOfAttorneyData> powers = powerRepository.findByGrantorName("1");
	        Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
	        Assert.assertEquals(powerData.getGranteeName(),powers.get(0).getGranteeName());
	        Assert.assertEquals(powerData.getGrantorName(),powers.get(0).getGrantorName());
	        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
	    }
	    
	    @Test
	    public void findByGranteeNameTest() {
	    	UserData userAccount = new UserData("2", "Mona");
	    	UserData userGrantor = new UserData("2", "Mona");
	    	UserData userGrantee = new UserData("3", "Pari");
	    	var account = new AccountData("222", userAccount, 13.65, AccountType.Payment.label);
	    	PowerOfAttorneyData powerData = new PowerOfAttorneyData(userGrantee, userGrantor, account, "READ");
	        powerRepository.save(powerData);
	        List<PowerOfAttorneyData> powers = powerRepository.findByGranteeName("3");
	        Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
	        Assert.assertEquals(powerData.getGranteeName(),powers.get(0).getGranteeName());
	        Assert.assertEquals(powerData.getGrantorName(),powers.get(0).getGrantorName());
	        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
	    }
	
}
