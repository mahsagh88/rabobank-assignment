package nl.rabobank.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import nl.rabobank.model.PowerOfAttorneyData;

/**
 * @author Provide
 *
 * 
 */
@Repository
public interface PowerOfAttorneyRepository extends MongoRepository<PowerOfAttorneyData, String> {
	
	@Query(value = "{'granteeName.userId': ?0}")
	List<PowerOfAttorneyData> findByGranteeName(String name);

	@Query(value = "{'grantorName.userId': ?0}")
	List<PowerOfAttorneyData> findByGrantorName(String name);

	@Query(value = "{'accountNumber.accountNumber': ?0}")
	List<PowerOfAttorneyData> findByAccountNumber(String account);
	
	@DeleteQuery(value="{'accountNumber.accountNumber' : ?0}")
	void deleteAllPowerByAccountNumber(String accountNumber);
	
	@DeleteQuery(value="{'grantorName.userId' : ?0}")
	void deleteAllPowerByGrantorName(String grantorName);

	@Query(value = "{'grantorName.userId': ?0,'granteeName.userId': ?1,'accountNumber.accountNumber': ?2,'authorization': ?3}")
	List<PowerOfAttorneyData> findByAllData(String grantorName, String granteeName, String accountNumber, String authorization);

}
