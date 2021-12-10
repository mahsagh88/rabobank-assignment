package nl.rabobank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nl.rabobank.model.UserData;

/**
 * @author Provide
 *
 * 
 */
@Repository
public interface UserRepository extends MongoRepository<UserData, String> {
	
}
