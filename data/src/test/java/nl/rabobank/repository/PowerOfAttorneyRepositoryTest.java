package nl.rabobank.repository;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;

import nl.rabobank.model.AccountData;
import nl.rabobank.model.AccountType;
import nl.rabobank.model.PowerOfAttorneyData;
import nl.rabobank.model.UserData;
import nl.rabobank.mongo.MongoConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@ContextConfiguration
@EnableMongoRepositories("nl.rabobank.repository")
public class PowerOfAttorneyRepositoryTest {
	
	 @Autowired
	 private PowerOfAttorneyRepository powerRepository;

	    @Test
	    public void inserPowerTest() {
	    	UserData userAccount = new UserData("1", "mahsa");
	    	UserData userGrantor = new UserData("1", "mahsa");
	    	UserData userGrantee = new UserData("2", "Ali");
	        var account = new AccountData("111", userAccount, 13.410,AccountType.Saving.label);
	        PowerOfAttorneyData powerData = new PowerOfAttorneyData(userGrantee, userGrantor, account, "READ");
	        powerRepository.save(powerData);
	    }
	    
	    @Test
	    public void findPowerByAccountNumberTest() {
	    	UserData userAccount = new UserData("1", "mahsa");
	    	UserData userGrantor = new UserData("1", "mahsa");
	    	UserData userGrantee = new UserData("2", "Ali");
	    	var account = new AccountData("111", userAccount, 13.410, AccountType.Payment.label);
	    	PowerOfAttorneyData powerData = new PowerOfAttorneyData(userGrantee, userGrantor, account, "READ");
	        powerRepository.save(powerData);
	        List<PowerOfAttorneyData> powers = powerRepository.findByAccountNumber("111");
	        Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
	        Assert.assertEquals(powerData.getGranteeName(),powers.get(0).getGranteeName());
	        Assert.assertEquals(powerData.getGrantorName(),powers.get(0).getGrantorName());
	        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
	    }
	        
	    @Test
	    public void findByGrantorNameTest() {
	    	UserData userAccount = new UserData("1", "mahsa");
	    	UserData userGrantor = new UserData("1", "mahsa");
	    	UserData userGrantee = new UserData("2", "Ali");
	    	var account = new AccountData("111", userAccount, 13.410, AccountType.Payment.label);
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
	    	UserData userAccount = new UserData("1", "mahsa");
	    	UserData userGrantor = new UserData("1", "mahsa");
	    	UserData userGrantee = new UserData("2", "Ali");
	    	var account = new AccountData("111", userAccount, 13.410, AccountType.Payment.label);
	    	PowerOfAttorneyData powerData = new PowerOfAttorneyData(userGrantee, userGrantor, account, "READ");
	        powerRepository.save(powerData);
	        List<PowerOfAttorneyData> powers = powerRepository.findByGranteeName("1");
	        Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
	        Assert.assertEquals(powerData.getGranteeName(),powers.get(0).getGranteeName());
	        Assert.assertEquals(powerData.getGrantorName(),powers.get(0).getGrantorName());
	        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
	    }
	        
	
}
