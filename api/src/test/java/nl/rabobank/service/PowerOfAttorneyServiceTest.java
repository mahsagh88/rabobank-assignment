package nl.rabobank.service;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
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

import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.model.UserModel;
import nl.rabobank.mongo.MongoConfiguration;

/**
 * @author Provide
 *
 * 
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@ContextConfiguration
@EnableMongoRepositories("nl.rabobank.repository")
class PowerOfAttorneyServiceTest {
	@Autowired
	 private PowerOfAttorneyServices powerService;
	
	@Test
	public void inserPowerTest() {
		UserModel userAccount = new UserModel("1", "mahsa");
		UserModel userGrantor = new UserModel("1", "mahsa");
		UserModel userGrantee = new UserModel("2", "Ali");
        Account account = new SavingsAccount("111", userAccount.getUserId(), 13.410);
        PowerOfAttorney powerData = PowerOfAttorney.builder().granteeName(userGrantee.getUserId())
		.grantorName()
		.accountNumber(account)
		.authorization(Authorization.valueOf("READ"))
		.build();
        powerofattorney powerdata = new powerofattorney (usergrantee.getuserid(), usergrantor.getuserid(), account, authorization.read);
        powerService.save(powerData);
    }
    
    @Test
    public void findAccountTest() {
    	UserModel userModel = new UserModel("1", "mahsa");
    	var account = new SavingsAccount("111", userModel.getUserId(), 13.410);
    	powerService.save(account);
        Account account1 = powerService.findById("111");
        Assert.assertEquals(account.getAccountNumber(),account1.getAccountNumber());
        Assert.assertEquals(account.getAccountHolderName(),account1.getAccountHolderName());
        Assert.assertEquals(account.getBalance(),account1.getBalance());
    }
        
    @Test
    public void findByAccountHolderNameTest() {
    	List<Account> accounts=new ArrayList<>();
    	UserModel userData = new UserModel("1", "mahsa");
    	UserModel userData2 = new UserModel("2", "Mona");
    	var account1 = new SavingsAccount("111", userData.getUserId(), 13.410);
    	var account2 = new PaymentAccount("222", userData2.getUserId(), 12.02);
    	accounts.add(account1);
    	accounts.add(account2);
        powerService.save(account1);
        powerService.save(account2);
        List<Account> accountlist = powerService.findByAccountHolderName("1");
        Assert.assertEquals(account1.getAccountNumber(),accountlist.get(0).getAccountNumber());
        Assert.assertEquals(account1.getAccountHolderName(),accountlist.get(0).getAccountHolderName());
        Assert.assertEquals(account1.getBalance(),accountlist.get(0).getBalance());
        Assert.assertEquals(account2.getAccountNumber(),accountlist.get(1).getAccountNumber());
        Assert.assertEquals(account2.getAccountHolderName(),accountlist.get(1).getAccountHolderName());
        Assert.assertEquals(account2.getBalance(),accountlist.get(1).getBalance());
    }
        
}