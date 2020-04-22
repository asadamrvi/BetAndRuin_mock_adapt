package exceptions;

public class InsufficientCash extends Exception{
	private static final long serialVersionUID = 1L;

	public InsufficientCash() {
		super();
	}
	
	/**This exception is triggered if the user has insufficient money for a bet
	  *@param s String of the exception
	  */
	  public InsufficientCash(String s)
	  {
	    super(s);
	  }
	

}
