package nl.rabobank.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
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
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.exception.NotFoundException;
import nl.rabobank.model.UserModel;

/**
 * @author Provide
 *
 * 
 */
@SpringBootTest
@ComponentScan(basePackages = { "nl.rabobank.service", "nl.rabobank.validator" })
@TestInstance(PER_CLASS)
class AccountServiceTest {
	@Autowired
	private AccountServices accountService;
	@Autowired
	private UserServices userService;

	@BeforeAll
	public void setUp() {
		UserModel userModel = new UserModel("1", "Mahsa");
		userService.save(userModel);
		UserModel userModel2 = new UserModel("2", "Mona");
		userService.save(userModel2);
		UserModel userModel3 = new UserModel("3", "Pari");
		userService.save(userModel3);
		UserModel userModel4 = new UserModel("4", "kimi");
		userService.save(userModel4);
	}

	@Test
	public void inserAccountTest() {
		UserModel userModel = new UserModel("3", "Pari");
		var account = new SavingsAccount("333", userModel.getUserId(), 13.510);
		accountService.save(account);
		Account account1 = accountService.findById("333");
		Assert.assertEquals(account.getAccountNumber(), account1.getAccountNumber());
		Assert.assertEquals(account.getAccountHolderName(), account1.getAccountHolderName());
		Assert.assertEquals(account.getBalance(), account1.getBalance());
	}

	@Test
	public void inserAccountExceptionTest() {
		UserModel userModel = new UserModel("3", "Pari");
		var account = new SavingsAccount("333", userModel.getUserId(), 13.510);

		Exception exception = assertThrows(RuntimeException.class, () -> accountService.save(account));

		String expectedMessage = "333 is already exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void findAccountTest() {
		UserModel userModel = new UserModel("1", "Mahsa");
		var account = new SavingsAccount("111", userModel.getUserId(), 13.410);
		accountService.save(account);
		Account account1 = accountService.findById("111");
		Assert.assertEquals(account.getAccountNumber(), account1.getAccountNumber());
		Assert.assertEquals(account.getAccountHolderName(), account1.getAccountHolderName());
		Assert.assertEquals(account.getBalance(), account1.getBalance());
	}

	@Test
	public void findAccountExceptionTest() {
		UserModel userModel = new UserModel("4", "kimi");
		var account = new SavingsAccount("444", userModel.getUserId(), 13.410);
		accountService.save(account);
		Exception exception = assertThrows(NotFoundException.class, () -> accountService.findById("908"));

		String expectedMessage = "Account with number: 908 not found";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void findByAccountHolderNameTest() {
		UserModel userData = new UserModel("2", "Mona");
		var account1 = new SavingsAccount("022", userData.getUserId(), 13.410);
		accountService.save(account1);
		List<Account> accountlist = accountService.findByAccountHolderName("2");
		Assert.assertEquals(account1.getAccountNumber(), accountlist.get(0).getAccountNumber());
		Assert.assertEquals(account1.getAccountHolderName(), accountlist.get(0).getAccountHolderName());
		Assert.assertEquals(account1.getBalance(), accountlist.get(0).getBalance());
	}

}
