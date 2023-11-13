package exception;

@SuppressWarnings("serial")
public class UnsupportedDriverException extends Exception {
	
	public UnsupportedDriverException() {
		super();
	}
	
	public UnsupportedDriverException(String message, Throwable t) {
		super(message, t);
	}
	
	public UnsupportedDriverException(Throwable t) {
		super(t);
	}
	
	public UnsupportedDriverException(String message) {
		super(message);
	}
}
