package nl.rabobank.model;
/**
 * @author Provide
 *
 * 
 */
public enum AccountType {

	Saving("saving","0"),
	Payment("payment","1");
	 public final String label;
	 public final String id;

	    private AccountType(String label,String id) {
	        this.label = label;
	        this.id = id;
	    }
	    
}
