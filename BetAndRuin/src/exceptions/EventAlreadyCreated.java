package exceptions;

@SuppressWarnings("serial")
public class EventAlreadyCreated extends Exception {

	public EventAlreadyCreated() {
		super();
	}
	
	public EventAlreadyCreated(String s) {
		super(s);
	}

}
