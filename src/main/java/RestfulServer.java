import spark.Spark;

import java.util.ArrayList;

import spark.Request;
import spark.Response;


//Testing

public class RestfulServer {

	public ArrayList<protectedMessage> messages = new ArrayList<protectedMessage>();

	public RestfulServer() {
		this.configureRestfulApiServer();
		this.processRestfulApiRequests();
	}
	
	private void configureRestfulApiServer() {
		Spark.port(8080);
		System.out.println("Server configured to listen on port 8080");
	}
	
	private void processRestfulApiRequests() {
		Spark.get("/", this::echoRequest);
	}
	
	private String echoRequest(Request request, Response response) {
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.status(200); // HTTP Status Code for success

		return HttpRequestToJson(request);
	}
	
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
		else {// Index == -1, There is not a space, only a password
			int password = Integer.parseInt(body);
			returnString =  ("The protected message was: " + checkPassword(password));
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
				returnMessage = ("The previous message was: " + messages.get(i).getMessage());
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
	
	
	
	
	private String HttpRequestToJson(Request request) {
		
		//String body;// = "2301 Test Message";
		//MessageHandler messageHandler = new MessageHandler();
		//body = messageHandler.bodyHandler(request.body());
		//System.out.println(body);
		//return body;
		
		System.out.println(request.body());
		
		String newBody;
		newBody = bodyHandler(request.body());
		
		return newBody;
		
		/*
		return "{\n"
				+ "\"attributes\":\""		+ request.attributes()		+ "\",\n"
				+ "\"body\":\""				+ request.body()			+ "\",\n"
				+ "\"contentLength\":\""	+ request.contentLength()	+ "\",\n"
				+ "\"contentType\":\""		+ request.contentType()		+ "\",\n"
				+ "\"contextPath\":\""		+ request.contextPath()		+ "\",\n"
				+ "\"cookies\":\""			+ request.cookies()			+ "\",\n"
				+ "\"headers\":\""			+ request.headers()			+ "\",\n"
				+ "\"host\":\""				+ request.host()			+ "\",\n"
				+ "\"ip\":\""				+ request.ip()				+ "\",\n"
				+ "\"params\":\""			+ request.params()			+ "\",\n"
				+ "\"pathInfo\":\""			+ request.pathInfo()		+ "\",\n"
				+ "\"serverPort\":\""		+ request.port()			+ "\",\n"
				+ "\"protocol\":\""			+ request.protocol()		+ "\",\n"
				+ "\"queryParams\":\""		+ request.queryParams()		+ "\",\n"
				+ "\"requestMethod\":\""	+ request.requestMethod()	+ "\",\n"
				+ "\"scheme\":\""			+ request.scheme()			+ "\",\n"
				+ "\"servletPath\":\""		+ request.servletPath()		+ "\",\n"
				+ "\"session\":\""			+ request.session()			+ "\",\n"
				+ "\"uri()\":\""			+ request.uri()				+ "\",\n"
				+ "\"url()\":\""			+ request.url()				+ "\",\n"
				+ "\"userAgent\":\""		+ request.userAgent()		+ "\",\n"
				+ "It seems like this worked!"							+ "\n"
				+ "}";	*/
	}
	
	public static void main(String[] programArgs) {
		RestfulServer restfulServer = new RestfulServer(); // Never returns
	}
}
