package nl.rabobank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
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
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.exception.NotFoundException;
import nl.rabobank.model.PowerOfAttorneyDto;
import nl.rabobank.service.AccountServices;
import nl.rabobank.service.PowerOfAttorneyServices;
import nl.rabobank.validator.PowerOfAttorneyValidator;

/**
 * @author Provide
 *
 * 
 */
@RestController
@Slf4j
public class PowerOfAttorneyController {
	
	private PowerOfAttorneyServices powerService;
	private AccountServices accountService;
	private PowerOfAttorneyValidator powerValidator;
	
	@Autowired
	public PowerOfAttorneyController(PowerOfAttorneyServices powerService, AccountServices accountService,
			PowerOfAttorneyValidator powerValidator) {
		super();
		this.powerService = powerService;
		this.accountService = accountService;
		this.powerValidator = powerValidator;
	}

	@GetMapping(value = "/findAllPowerAttorneies")
	public @ResponseBody List<PowerOfAttorneyDto> showAllPowerAttornies() {
		return powerService.findAll().stream()
				.map(power -> new PowerOfAttorneyDto(power.getGranteeName(), 
				power.getGrantorName(), power.getAccountNumber().getAccountNumber(), 
				power.getAuthorization().toString()))
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/findPowerAttorneyByGranteeId/{Id}")
	public @ResponseBody List<PowerOfAttorneyDto> findByGranteeName(@PathVariable("Id") String granteeName) {
		List<PowerOfAttorney> powerOfAttornies = powerService.findByGranteeName(granteeName);
		return powerOfAttornies.stream().map(m -> new PowerOfAttorneyDto(m.getGranteeName(), 
				m.getGrantorName(), m.getAccountNumber().getAccountNumber(), m.getAuthorization().toString())).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/findPowerAttorneyByGrantorId/{Id}")
	public @ResponseBody List<PowerOfAttorneyDto> findByGrantorName(@PathVariable("Id") String grantorName) {
		List<PowerOfAttorney> powerOfAttornies = powerService.findByGrantorName(grantorName);
		return powerOfAttornies.stream().map(m -> new PowerOfAttorneyDto(m.getGranteeName(), 
				m.getGrantorName(), m.getAccountNumber().getAccountNumber(), m.getAuthorization().name())).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/findPowerAttorneyByAccountNumber/{accountNumber}")
	public @ResponseBody List<PowerOfAttorneyDto> findByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
		List<PowerOfAttorney> powerOfAttornies = powerService.findByAccountNumber(accountNumber);
		return powerOfAttornies.stream().map(m -> new PowerOfAttorneyDto(m.getGranteeName(), 
				m.getGrantorName(), m.getAccountNumber().getAccountNumber(), m.getAuthorization().name())).collect(Collectors.toList());
	}
	
	@PostMapping(value = "/savePowerAttorney")
	public void save(@RequestBody PowerOfAttorneyDto powerOfAttorneyDto) {
		log.info("Start saving powerOfAttorney [{}]", powerOfAttorneyDto);
		powerValidator.validateAndThrowConstraintViolationException(powerOfAttorneyDto);
		Account account = accountService.findById(powerOfAttorneyDto.getAccountNumber());
		if(account == null)
		{
            throw new NotFoundException("Account with number: " + powerOfAttorneyDto.getAccountNumber() + " is not Found");
		}

		if(!EnumUtils.isValidEnum(Authorization.class, powerOfAttorneyDto.getAuthorization()))
		{
            throw new NotFoundException("Authorization with type: " + powerOfAttorneyDto.getAuthorization() + " is undefined");
		}
		PowerOfAttorney powerOfAttorney = PowerOfAttorney.builder().granteeName(powerOfAttorneyDto.getGranteeName())
					.grantorName(powerOfAttorneyDto.getGrantorName())
					.accountNumber(account)
					.authorization(Authorization.valueOf(powerOfAttorneyDto.getAuthorization()))
					.build();
		powerService.save(powerOfAttorney);
		log.info("Saved finished for powerOfAttorney [{}]", powerOfAttorneyDto);
	}
	
	@DeleteMapping(value = "/deletePowerOfAttorneyByAccountNumber/{accountNumber}")
	public void deletePowerByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
		log.info("Start deleting powerOfAttorney with account Number [{}]", accountNumber);
		powerService.deleteAllPowerByAccountNumber(accountNumber);
        log.info("Delete Finished for powerOfAttorney with account number [{}]", accountNumber);
	}
	
	@DeleteMapping(value = "/deletePowerOfAttorneyByGrantor/{grantorName}")
	public void deletePowerByGranorName(@PathVariable("grantorName") String grantorName) {
		log.info("Start deleting powerOfAttorney with grantor name [{}]", grantorName);
		powerService.deleteAllPowerByGrantorName(grantorName);
        log.info("Delete Finished for powerOfAttorney with grantor name [{}]", grantorName);
	}

}
