package nl.rabobank.repository;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import nl.rabobank.model.AccountData;
import nl.rabobank.model.AccountType;
import nl.rabobank.model.UserData;
import nl.rabobank.mongo.MongoConfiguration;

@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@EnableMongoRepositories("nl.rabobank.repository")

public class AccountRepositoryTest {
	
	@BeforeAll
	public void setUp() {
		UserData userModel = new UserData("1", "Mahsa");
		userRepository.save(userModel);
		UserData userModel2 = new UserData("2", "Mona");
		userRepository.save(userModel2);
		UserData userModel3 = new UserData("3", "Pari");
		userRepository.save(userModel3);
	}
	
	 @Autowired
	 private AccountRepository accountRepository;
	 @Autowired
	 private UserRepository userRepository;

	    @Test
	    public void inserAccountTest() {
	    	UserData userData = new UserData("1", "Mahsa");
	        var account = new AccountData("011", userData, 13.410,AccountType.Saving.label);
	        accountRepository.save(account);
	        AccountData account1 = accountRepository.findById("011").get();
	        Assert.assertEquals(account.getAccountNumber(),account1.getAccountNumber());
	        Assert.assertEquals(account.getAccountHolderName(),account1.getAccountHolderName());
	        Assert.assertEquals(account.getBalance(),account1.getBalance());
	    }
	    
	    @Test
	    public void findAccountTest() {
	    	UserData userData = new UserData("3", "Pari");
	    	var account = new AccountData("333", userData, 13.510, AccountType.Saving.label);
	        accountRepository.save(account);
	        AccountData account1 = accountRepository.findById("333").get();
	        Assert.assertEquals(account.getAccountNumber(),account1.getAccountNumber());
	        Assert.assertEquals(account.getAccountHolderName(),account1.getAccountHolderName());
	        Assert.assertEquals(account.getBalance(),account1.getBalance());
	    }
	        
	    @Test
	    public void findByAccountHolderNameTest() {
	    	UserData userData = new UserData("2", "Mona");
	    	var account1 = new AccountData("022", userData, 13.410, AccountType.Saving.label);
	        accountRepository.save(account1);
	        List<AccountData> accountlist = accountRepository.findByAccountHolderName("2");
	        Assert.assertEquals(account1.getAccountNumber(),accountlist.get(0).getAccountNumber());
	        Assert.assertEquals(account1.getAccountHolderName(),accountlist.get(0).getAccountHolderName());
	        Assert.assertEquals(account1.getBalance(),accountlist.get(0).getBalance());
	    }
	        
	
}
