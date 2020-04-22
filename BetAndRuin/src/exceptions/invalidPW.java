package exceptions;

public class invalidPW extends Exception{
	private static final long serialVersionUID = 1L;

	public invalidPW() {
		super();
	}

	  /**This exception is triggered if there is already an existing user in the database with the given ID
	  *@param s String of the exception
	  */
	public invalidPW(String message) {
		super(message);
	}
	
	

} 
