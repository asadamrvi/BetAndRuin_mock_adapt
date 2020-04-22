package exceptions;

public class invalidID extends Exception{
	private static final long serialVersionUID = 1L;

	public invalidID() {
		super();
	}

	  /**This exception is triggered if there is already an existing user in the database with the given ID
	  *@param s String of the exception
	  */
	public invalidID(String message) {
		super(message);
	}
	
	

}
