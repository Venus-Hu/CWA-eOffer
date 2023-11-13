package gov.gsa.sst.util.exceptions;

public class TestExecutionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TestExecutionException() {
	}

	public TestExecutionException(String message) {
		super(message);
	}

	public TestExecutionException(Throwable cause) {
		super(cause);
	}

	public TestExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestExecutionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
