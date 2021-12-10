package nl.rabobank.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import nl.rabobank.model.AccountData;

/**
 * @author Provide
 *
 * 
 */
@Repository
public interface AccountRepository extends MongoRepository<AccountData, String> {
	
	@Query(value = "{'accountHolderName.userId': ?0}")
	List<AccountData> findByAccountHolderName(String holderName);
}
	