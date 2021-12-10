package nl.rabobank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.exception.NotFoundException;
import nl.rabobank.model.AccountData;
import nl.rabobank.model.AccountType;
import nl.rabobank.model.PowerOfAttorneyData;
import nl.rabobank.model.UserData;
import nl.rabobank.model.UserModel;
import nl.rabobank.repository.PowerOfAttorneyRepository;
import nl.rabobank.validator.PowerOfAttorneyValidator;

/**
 * @author Provide
 *
 * 
 */
@Service
@Slf4j
public class PowerOfAttorneyServices {

	@Autowired
	private PowerOfAttorneyRepository powerRepo;

	@Autowired
	private AccountServices accountService;
	
	@Autowired
	private UserServices userService;
	
	@Autowired
	private PowerOfAttorneyValidator powerValidator;

	public List<PowerOfAttorney> findAll() {
		return powerRepo.findAll().stream().map(p -> PowerOfAttorney.builder().granteeName(p.getGranteeName().getUserName())
					.grantorName(p.getGrantorName().getUserName())
					.accountNumber(accountService.findById(p.getAccountNumber().getAccountNumber()))
					.authorization(Authorization.valueOf(p.getAuthorization()))
					.build()).collect(Collectors.toList());		
	}

	public List<PowerOfAttorney> findByGranteeName(String granteeName) {
		List<PowerOfAttorneyData> powerOfAttorneyDatas = powerRepo.findByGranteeName(granteeName);
		return powerOfAttorneyDatas.stream().map(p -> PowerOfAttorney.builder().granteeName(p.getGranteeName().getUserName())
				.grantorName(p.getGrantorName().getUserName())
				.accountNumber(accountService.findById(p.getAccountNumber().getAccountNumber()))
				.authorization(Authorization.valueOf(p.getAuthorization()))
				.build()).collect(Collectors.toList());
	}

	public List<PowerOfAttorney> findByGrantorName(String grantorName) {
		if(userService.findById(grantorName) == null)
		{
			throw new NotFoundException("Grantor with id: [{}] is not found" + grantorName);
		}
		List<PowerOfAttorneyData> powerOfAttorneyDatas = powerRepo.findByGrantorName(grantorName);
		return powerOfAttorneyDatas.stream().map(p -> PowerOfAttorney.builder().granteeName(p.getGranteeName().getUserName())
				.grantorName(p.getGrantorName().getUserName())
				.accountNumber(accountService.findById(p.getAccountNumber().getAccountNumber()))
				.authorization(Authorization.valueOf(p.getAuthorization()))
				.build()).collect(Collectors.toList());
	}
	
	public List<PowerOfAttorney> findByAccountNumber(String accountNumber) {
		List<PowerOfAttorneyData> powerOfAttorneyDatas = powerRepo.findByAccountNumber(accountNumber);
		return powerOfAttorneyDatas.stream().map(p -> PowerOfAttorney.builder().granteeName(p.getGranteeName().getUserName())
				.grantorName(p.getGrantorName().getUserName())
				.accountNumber(accountService.findById(accountNumber))
				.authorization(Authorization.valueOf(p.getAuthorization()))
				.build()).collect(Collectors.toList());
	}
	
	public void save(PowerOfAttorney powerOfAttorney) {
		log.debug("Start saving powerOfAttorney [{}]", powerOfAttorney);
		powerValidator.validateAndThrowConstraintViolationException(powerOfAttorney);
		List<PowerOfAttorneyData> powerData = powerRepo.findByAllData(powerOfAttorney.getGrantorName(), 
				powerOfAttorney.getGranteeName(), powerOfAttorney.getAccountNumber().getAccountNumber(), powerOfAttorney.getAuthorization().name());
    	if(powerData != null && !powerData.isEmpty()) {
            throw new RuntimeException("PowerOfAttorney with current data: " + powerOfAttorney + " is already exist");
    	}
    	if(userService.findById(powerOfAttorney.getGranteeName()) == null)
		{
			throw new NotFoundException("Grantee with id :[{}] is not found" + powerOfAttorney.getGranteeName());
		}
		if(userService.findById(powerOfAttorney.getGrantorName()) == null)
		{
			throw new NotFoundException("Grantor with id :[{}] is not found" + powerOfAttorney.getGrantorName());
		}
		if(!accountService.findByAccountHolderName(powerOfAttorney.getGrantorName()).stream()
				.filter(ac -> ac.getAccountNumber().equalsIgnoreCase(powerOfAttorney.getAccountNumber().getAccountNumber()))
				.findAny().isPresent())
		{
			throw new RuntimeException("Not allowed. grantor is not the owner of the account");
		} else {

		UserModel userGranteeModel = userService.findById(powerOfAttorney.getGranteeName());
		UserModel userGranorModel = userService.findById(powerOfAttorney.getGrantorName());
		Account account = accountService.findById(powerOfAttorney.getAccountNumber().getAccountNumber());
		String accountType = (PaymentAccount.class.getClass().equals(account) ) ? accountType = AccountType.Payment.id : AccountType.Saving.id;
		UserModel userAccountModel = userService.findById(account.getAccountHolderName());
		AccountData accountData = new AccountData(account.getAccountNumber(), new UserData(userAccountModel.getUserId(), userAccountModel.getUserName()),
				account.getBalance(), accountType);
		powerRepo.save(new PowerOfAttorneyData(new UserData(userGranteeModel.getUserId(), userGranteeModel.getUserName()),
				new UserData(userGranorModel.getUserId(), userGranorModel.getUserName()), 
				accountData, 
				powerOfAttorney.getAuthorization().toString()));
		}
	}
	
	public void deleteAllPowerByAccountNumber(String accountNumber) {
		log.debug("Start deleting powerOfAttorneies with account number [{}]", accountNumber);
    	List<PowerOfAttorneyData> powerData = powerRepo.findByAccountNumber(accountNumber);
    	if (powerData != null && !powerData.isEmpty()) {
    		powerRepo.deleteAllPowerByAccountNumber(accountNumber);
        } else {
            throw new NotFoundException("powerOfAttorneies with account number: " + accountNumber + " not found");
        }
    	log.debug("Delete finished for powerOfAttorneies with account number: [{}]", accountNumber);
	}
	
	public void deleteAllPowerByGrantorName(String grantorName) {
		log.debug("Start deleting powerOfAttorneies with grantor name [{}]", grantorName);
    	List<PowerOfAttorneyData> powerData = powerRepo.findByGrantorName(grantorName);
    	if (powerData != null && !powerData.isEmpty()) {
    		powerRepo.deleteAllPowerByGrantorName(grantorName);
        } else {
            throw new NotFoundException("powerOfAttorneies with grantor name: " + grantorName + " not found");
        }
    	log.debug("Delete finished for powerOfAttorneies with grantor name: [{}]", grantorName);
	}

}
