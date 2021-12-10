package nl.rabobank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.exception.NotFoundException;
import nl.rabobank.model.AccountDto;
import nl.rabobank.model.AccountType;
import nl.rabobank.service.AccountServices;
import nl.rabobank.service.UserServices;
import nl.rabobank.validator.AccountValidator;

/**
 * @author Provide
 *
 * 
 */
@RestController
@Slf4j
public class AccountController {
	
	private final AccountServices accountService;
	private final UserServices userService;
	private final AccountValidator accountValidator;
	@Autowired
	public AccountController(AccountServices accountService, UserServices userService, AccountValidator accountValidator) {
		super();
		this.accountService = accountService;
		this.userService = userService;
		this.accountValidator = accountValidator;
	}
	
	@GetMapping(value = "/findAllAccounts")
    public @ResponseBody List<AccountDto> showAccounts()
    {
		List<Account> accounts = accountService.findAll();
		return accounts.stream().map(acc -> acc instanceof PaymentAccount ?
    			new AccountDto(acc.getAccountNumber(), userService.findById(acc.getAccountHolderName()).getUserName(), acc.getBalance(), AccountType.Payment.label) :
    			new AccountDto(acc.getAccountNumber(), userService.findById(acc.getAccountHolderName()).getUserName(), acc.getBalance(), AccountType.Saving.label))
    			.collect(Collectors.toList());
    }
    
	@PostMapping(value = "/saveAccount")
    public void save(@RequestBody AccountDto accountDto)
    {
		log.info("Start saving account [{}]", accountDto);
    	accountValidator.validateAndThrowConstraintViolationException(accountDto);
    	final Account account;
		if(accountDto.getAccountType().equalsIgnoreCase(AccountType.Payment.id))
		{
			account = new PaymentAccount(accountDto.getAccountNumber(), accountDto.getAccountHolderName(), accountDto.getBalance());
		} else if(accountDto.getAccountType().equalsIgnoreCase(AccountType.Saving.id)){
			account = new SavingsAccount(accountDto.getAccountNumber(), accountDto.getAccountHolderName(), accountDto.getBalance());
		} else {
            throw new NotFoundException("Account with type: " + accountDto.getAccountType() + " is undefined");
		}
        accountService.save(account);
        log.info("Saved finished for account [{}]", accountDto);
    }
    
    @DeleteMapping(value = "/deleteAccount/{accountNumber}")
    public void delete(@PathVariable("accountNumber") String accountNumber)
    { 
    	log.info("Start deleting account with account Number [{}]", accountNumber);
        accountService.delete(accountNumber);
        log.info("Delete Finished for account number [{}]", accountNumber);
    }

    @GetMapping(value = "/findAccountByNumber/{accountNumber}")
    public @ResponseBody AccountDto findByAccountNumber(@PathVariable("accountNumber") String accountNumber)
    {
    	Account account = accountService.findById(accountNumber);
		String accountType = (PaymentAccount.class.getClass().equals(account) ) ? AccountType.Payment.label : AccountType.Saving.label;
    	return new AccountDto(account.getAccountNumber(), userService.findById(account.getAccountHolderName()).getUserName(), account.getBalance(), accountType);
    }
    
    @GetMapping(value = "/findAccountByHolderName/{name}")
    public @ResponseBody List<AccountDto> findByHolderName(@PathVariable("name") String holderName)
    {
    	List<Account> accounts = accountService.findByAccountHolderName(holderName);
    	return accounts.stream().map(acc -> acc instanceof PaymentAccount ?
    			new AccountDto(acc.getAccountNumber(), userService.findById(acc.getAccountHolderName()).getUserName(), acc.getBalance(), AccountType.Payment.label) :
    			new AccountDto(acc.getAccountNumber(), userService.findById(acc.getAccountHolderName()).getUserName(), acc.getBalance(), AccountType.Saving.label))
    			.collect(Collectors.toList());
    }
    
}
