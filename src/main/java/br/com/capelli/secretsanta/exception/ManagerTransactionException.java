package br.com.capelli.secretsanta.exception;

public class ManagerTransactionException extends Exception {

	private static final long serialVersionUID = 1L;

	public ManagerTransactionException() {
		super();
	}

	public ManagerTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagerTransactionException(String message) {
		super(message);
	}

	public ManagerTransactionException(Throwable cause) {
		super(cause);
	}

}
