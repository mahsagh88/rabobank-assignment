package nl.rabobank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.exception.NotFoundException;
import nl.rabobank.model.AccountData;
import nl.rabobank.model.AccountType;
import nl.rabobank.model.UserData;
import nl.rabobank.model.UserModel;
import nl.rabobank.repository.AccountRepository;
import nl.rabobank.validator.AccountValidator;

/**
 * @author Provide
 *
 * 
 */
@Service
@Slf4j
public class AccountServices {

	@Autowired
    private AccountRepository accountRepo;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private AccountValidator accountValidator;
	
	public void save(Account account) {
		log.debug("Start saving account [{}]", account);
		accountValidator.validateAndThrowConstraintViolationException(account);
    	if(accountRepo.findById(account.getAccountNumber()).isPresent()) {
            throw new RuntimeException("Account with number: " + account.getAccountNumber() + " is already exist");
    	}
		String accountType = AccountType.Saving.label;
		if(PaymentAccount.class.getName().equals(account.getClass().getName()))
		{
			accountType = AccountType.Payment.label;
		}
		UserModel userModel = userServices.findById(account.getAccountHolderName());
		AccountData accountData = new AccountData(account.getAccountNumber(), new UserData(userModel.getUserId(), userModel.getUserName()), account.getBalance(), accountType);
		accountRepo.save(accountData);
		log.debug("Saved finished for account [{}]", account);
	}
    
    public void delete(String id)
    {
    	log.debug("Start deleting account with number [{}]", id);
    	Optional<AccountData> accountData = accountRepo.findById(id);
    	if (!accountData.isPresent()) {
            throw new NotFoundException("Account with number: " + id + " not found");
        }
    	accountData.ifPresent(accountRepo::delete);
    	log.debug("Delete finished for account number: [{}]", id);
    }
    
  
    public List<Account> findAll()
    {
    	List<AccountData> accountDataList = accountRepo.findAll();
    	return accountDataList.stream().map(acc -> acc.getAccountType().equalsIgnoreCase(AccountType.Saving.label) ?
    			new SavingsAccount(acc.getAccountNumber(), acc.getAccountHolderName().getUserId(), acc.getBalance()) :
    			new PaymentAccount(acc.getAccountNumber(), acc.getAccountHolderName().getUserId(), acc.getBalance()))
    			.collect(Collectors.toList());
    }
    
    public List<Account> findByAccountHolderName(String accountHolderName) {
    	List<AccountData> accountDataList = accountRepo.findByAccountHolderName(accountHolderName);
    	return accountDataList.stream().map(acc -> acc.getAccountType().equalsIgnoreCase(AccountType.Saving.label) ?
    			new SavingsAccount(acc.getAccountNumber(), acc.getAccountHolderName().getUserId(), acc.getBalance()) :
    			new PaymentAccount(acc.getAccountNumber(), acc.getAccountHolderName().getUserId(), acc.getBalance()))
    			.collect(Collectors.toList());
    }
    
    public Account findById(String accountNumber) {
    	Optional<AccountData> accountData = accountRepo.findById(accountNumber);
    	if(!accountData.isPresent()) {
            throw new NotFoundException("Account with number: " + accountNumber + " not found");
    	}
    	return accountData.map(acc -> acc.getAccountType().equalsIgnoreCase(AccountType.Saving.id) ?
    			new SavingsAccount(acc.getAccountNumber(), acc.getAccountHolderName().getUserId(), acc.getBalance()) :
    			new PaymentAccount(acc.getAccountNumber(), acc.getAccountHolderName().getUserId(), acc.getBalance())).orElse(null);
    }
    
}
