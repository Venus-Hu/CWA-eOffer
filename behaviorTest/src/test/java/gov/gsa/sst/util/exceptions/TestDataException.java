package gov.gsa.sst.util.exceptions;

public class TestDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TestDataException() {
	}

	public TestDataException(String message) {
		super(message);
	}

	public TestDataException(Throwable cause) {
		super(cause);
	}

	public TestDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestDataException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
