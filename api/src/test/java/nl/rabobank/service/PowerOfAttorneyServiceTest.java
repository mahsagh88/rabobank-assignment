package nl.rabobank.service;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.model.UserModel;

/**
 * @author Provide
 *
 * 
 */
@SpringBootTest
@ComponentScan(basePackages ={"nl.rabobank.service","nl.rabobank.validator"})
@TestInstance(PER_CLASS)

class PowerOfAttorneyServiceTest {
	@Autowired
	 private PowerOfAttorneyServices powerService;
	@Autowired
	 private UserServices userService;
	 @Autowired
	 private AccountServices accountServices;
	
	@BeforeAll
	public void setUp() {
		UserModel userModel = new UserModel("1", "Mahsa");
		userService.save(userModel);
		UserModel userModel2 = new UserModel("2", "Mona");
		userService.save(userModel2);
		UserModel userModel3 = new UserModel("3", "Pari");
		userService.save(userModel3);
		UserModel userModel4 = new UserModel("4", "Kimi");
		userService.save(userModel4);
		UserModel userModel5 = new UserModel("5", "Ali");
		userService.save(userModel5);
		UserModel userModel6 = new UserModel("6", "Ahmad");
		userService.save(userModel6);
		UserModel userModel7 = new UserModel("7", "Sedi");
		userService.save(userModel7);
		UserModel userModel8 = new UserModel("8", "Amir");
		userService.save(userModel8);
		
		Account account = new SavingsAccount("111", userModel.getUserId(), 10.65);
		accountServices.save(account);
		Account account2 = new PaymentAccount("222", userModel2.getUserId(), 13.65);
		accountServices.save(account2);
		Account account3 = new SavingsAccount("333", userModel3.getUserId(), 10.665);
		accountServices.save(account3);
		Account account4 = new SavingsAccount("444", userModel4.getUserId(), 1.5);
		accountServices.save(account4);
		Account account5 = new PaymentAccount("555", userModel5.getUserId(), 15.65);
		accountServices.save(account5);
		Account account6 = new SavingsAccount("666", userModel6.getUserId(), 80.665);
		accountServices.save(account6);
		Account account7 = new PaymentAccount("777", userModel7.getUserId(), 70.665);
		accountServices.save(account7);
		Account account8 = new SavingsAccount("888", userModel8.getUserId(), 80.888);
		accountServices.save(account8);
	}
	
	@Test
	public void inserPowerTest() {
		UserModel userAccount = new UserModel("1", "Mahsa");
		UserModel userGrantor = new UserModel("1", "Mahsa");
		UserModel userGrantee = new UserModel("2", "Mona");
        Account account = new SavingsAccount("111", userAccount.getUserId(), 10.65);
        PowerOfAttorney powerData = PowerOfAttorney.builder().granteeName(userGrantee.getUserId())
		.grantorName(userGrantor.getUserId())
		.accountNumber(account)
		.authorization(Authorization.valueOf("READ"))
		.build();
        powerService.save(powerData);
        List<PowerOfAttorney> power1 = powerService.findByAllData(userGrantor.getUserId(), userGrantee.getUserId(), account.getAccountNumber(), "READ");
        Assert.assertEquals(userGrantor.getUserName(), power1.get(0).getGrantorName());
        Assert.assertEquals(userGrantee.getUserName(), power1.get(0).getGranteeName());
        Assert.assertEquals(powerData.getAccountNumber(), power1.get(0).getAccountNumber());
        Assert.assertEquals(powerData.getAuthorization(), power1.get(0).getAuthorization());
    }
    
    @Test
    public void findPowerByAccountNumberTest() {
    	UserModel userAccount = new UserModel("3", "Pari");
    	UserModel userGrantor = new UserModel("3", "Pari");
    	UserModel userGrantee = new UserModel("4", "Kimi");
    	var account = new SavingsAccount("333", userAccount.getUserId(), 10.665);
    	PowerOfAttorney powerData = PowerOfAttorney.builder().granteeName(userGrantee.getUserId())
    			.grantorName(userGrantor.getUserId())
    			.accountNumber(account)
    			.authorization(Authorization.valueOf("READ"))
    			.build();
    	powerService.save(powerData);
    	List<PowerOfAttorney> powers = powerService.findByAccountNumber("333");
    	Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
        Assert.assertEquals(userGrantee.getUserName(),powers.get(0).getGranteeName());
        Assert.assertEquals(userGrantor.getUserName(),powers.get(0).getGrantorName());
        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
    	        
    }
    
    @Test
    public void findByGrantorNameTest() {
    	UserModel userAccount = new UserModel("5", "Ali");
    	UserModel userGrantor = new UserModel("5", "Ali");
    	UserModel userGrantee = new UserModel("6", "Ahmad");
    	PaymentAccount account = new PaymentAccount("555", userAccount.getUserId(), 15.65);
    	PowerOfAttorney powerData = PowerOfAttorney.builder().granteeName(userGrantee.getUserId())
    			.grantorName(userGrantor.getUserId())
    			.accountNumber(account)
    			.authorization(Authorization.valueOf("READ"))
    			.build();
    	powerService.save(powerData);
        List<PowerOfAttorney> powers = powerService.findByGrantorName("5");
        Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
        Assert.assertEquals(userGrantee.getUserName(),powers.get(0).getGranteeName());
        Assert.assertEquals(userGrantor.getUserName(),powers.get(0).getGrantorName());
        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
    }
    
    @Test
    public void findByGranteeNameTest() {
    	UserModel userAccount = new UserModel("7", "Sedi");
    	UserModel userGrantor = new UserModel("7", "Sedi");
    	UserModel userGrantee = new UserModel("8", "Amir");
    	var account = new PaymentAccount("777", userAccount.getUserId(), 70.665);
    	PowerOfAttorney powerData = PowerOfAttorney.builder().granteeName(userGrantee.getUserId())
    			.grantorName(userGrantor.getUserId())
    			.accountNumber(account)
    			.authorization(Authorization.valueOf("READ"))
    			.build();
    	powerService.save(powerData);
    	List<PowerOfAttorney> powers = powerService.findByGranteeName("8");
        Assert.assertEquals(powerData.getAccountNumber(),powers.get(0).getAccountNumber());
        Assert.assertEquals(userGrantee.getUserName(),powers.get(0).getGranteeName());
        Assert.assertEquals(userGrantor.getUserName(),powers.get(0).getGrantorName());
        Assert.assertEquals(powerData.getAuthorization(),powers.get(0).getAuthorization());
    }
    
}
