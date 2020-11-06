import java.util.ArrayList;

public class MessageHandler {

	ArrayList<protectedMessage> messages = new ArrayList<protectedMessage>();
	
	/*********************************************************
	 * Handles the splitting and managing of the new message *
	 * @param body											 *
	 * @return												 *
	 *********************************************************/
	public String bodyHandler(String body) {
		String returnString = "an error occured, not sure what happened.";
		if (body.indexOf(' ') >= 0) {// Index >= -1, There is a space
			String stringPassword = body.substring(0,body.indexOf(' '));
			int password = Integer.parseInt(stringPassword);
			String newMessage = body.substring(body.indexOf(' ') + 1);
			
			String oldMessage = checkPassword(password);
			setMessage(password, newMessage);
			
			returnString = oldMessage;
		}
		else {// Index == -1, There is not a space
			int password = Integer.parseInt(body);
			returnString =  checkPassword(password);
		}
		return returnString;
	}
	
	
	/**********************************************************************
	 * Checks a password to see if it's used, creates a new entry if not. *
	 * @param  password													  *
	 * @return returns the existing message or a default message		  *
	 **********************************************************************/
	public String checkPassword (int password) {
		String returnMessage = ""; 
		for (int i = 0; i < messages.size(); i++) { // Searches through the arraylist for the password. If the password exists, it grabs the associated message
			if (password == messages.get(i).getPassword()) {
				returnMessage = messages.get(i).getMessage();
			}
		}
		if (returnMessage.length() == 0) { // If there is no message, the message won't have any length, so it creates a new entry for it.
			returnMessage = "No existing message, creating new entry";
			protectedMessage newMessage = new protectedMessage(password, "Null");
			messages.add(newMessage);
		}
		return returnMessage;
	}
	
	/**********************************************************************************
	 * Sets the message for an existing password, assumes the password already exists *
	 * @param password																  *
	 * @param newMessage															  *
	 **********************************************************************************/
	public void setMessage (int password, String newMessage) {
		for (int i = 0; i < messages.size(); i++) {
			if (password == messages.get(i).getPassword()) {
				messages.get(i).setMessage(newMessage);
			}
		}
		return;
	}
	
	
}



/*******************
 * Message Objects *
 *******************/
class protectedMessage{
	int password;
	String message;
	
	
	protectedMessage(){}
	
	protectedMessage(int pass, String text){
		this.password = pass;
		this.message = text;
	}
	
	/*******************
	 * Getters/Setters *
	 *******************/
	void setPassword(int newPass) {
		this.password = newPass;
	}
	int getPassword() {
		return this.password;
	}
	
	void setMessage(String newMessage) {
		this.message = newMessage;
	}
	String getMessage() {
		return this.message;
	}
	
	
}
