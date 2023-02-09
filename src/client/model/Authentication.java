 package client.model;
import common.*;

/**
 * 
 * @author harry
 *
 */
public class Authentication {
	
	public boolean checkUser(User user){
		return new Link().send(user);
	}
}
