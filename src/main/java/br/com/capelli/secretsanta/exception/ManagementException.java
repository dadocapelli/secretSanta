package br.com.capelli.secretsanta.exception;


public class ManagementException extends Exception {

	private static final long serialVersionUID = 1L;

	public ManagementException() {
		super();
	}

	public ManagementException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagementException(String message) {
		super(message);
	}

	public ManagementException(Throwable cause) {
		super(cause);
	}
	
}
