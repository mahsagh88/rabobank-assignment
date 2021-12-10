package nl.rabobank.repository;

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

import nl.rabobank.model.AccountData;
import nl.rabobank.model.AccountType;
import nl.rabobank.model.UserData;
import nl.rabobank.mongo.MongoConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=MongoConfiguration.class)
@TestInstance(PER_CLASS)
@ContextConfiguration
@EnableMongoRepositories("nl.rabobank.repository")
public class AccountRepositoryTest {
	
	 @Autowired
	 private AccountRepository accountRepository;

	    @Test
	    public void inserAccountTest() {
	    	UserData userData = new UserData("101010", "mahsa");
	        var account = new AccountData("056", userData, 13.410,AccountType.Saving.label);
	        accountRepository.save(account);
	    }
	    
	    @Test
	    public void findAccountTest() {
	    	UserData userData = new UserData("101010", "mahsa");
	    	var account = new AccountData("056", userData, 13.410, AccountType.Saving.label);
	        accountRepository.save(account);
	        AccountData account1 = accountRepository.findById("056").get();
	        Assert.assertEquals(account.getAccountNumber(),account1.getAccountNumber());
	        Assert.assertEquals(account.getAccountHolderName(),account1.getAccountHolderName());
	        Assert.assertEquals(account.getBalance(),account1.getBalance());
	    }
	        
	    @Test
	    public void findByAccountHolderNameTest() {
	    	List<AccountData> accounts=new ArrayList<AccountData>();
	    	UserData userData = new UserData("101010", "mahsa");
	    	UserData userData2 = new UserData("202020", "Ali");
	    	var account1 = new AccountData("056", userData, 13.410, AccountType.Saving.label);
	    	var account2 = new AccountData("057", userData2, 12.02, AccountType.Saving.label);
	    	accounts.add(account1);
	    	accounts.add(account2);
	        accountRepository.saveAll(accounts);
	        List<AccountData> accountlist = accountRepository.findByAccountHolderName("mahsa khanum");
	        Assert.assertEquals(account1.getAccountNumber(),accountlist.get(0).getAccountNumber());
	        Assert.assertEquals(account1.getAccountHolderName(),accountlist.get(0).getAccountHolderName());
	        Assert.assertEquals(account1.getBalance(),accountlist.get(0).getBalance());
	        Assert.assertEquals(account2.getAccountNumber(),accountlist.get(1).getAccountNumber());
	        Assert.assertEquals(account2.getAccountHolderName(),accountlist.get(1).getAccountHolderName());
	        Assert.assertEquals(account2.getBalance(),accountlist.get(1).getBalance());
	    }
	        
	
}
