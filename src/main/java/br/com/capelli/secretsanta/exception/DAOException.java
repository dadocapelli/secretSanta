package br.com.capelli.secretsanta.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOException extends Exception {

	private static final long serialVersionUID = 5197766900831117699L;

	private ErrorDAO error;
	private String message;
	private Throwable cause;

	private Logger logger = Logger.getLogger("DAOException");

	public static enum ErrorDAO {

		NoResult(600, "Nenhum resultado foi encontrado"), PersistenceError(601,
				"A entidade não pode ser salva na base de dados"), RunTimeError(
				500, "O sistema não pode realizar essa operação");

		private ErrorDAO(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public final int getCode() {
			return code;
		}

		public final String getMessage() {
			return message;
		}

	}

	public DAOException(String msg) {
		this.message = msg;
		logger.log(Level.SEVERE,
				new StringBuilder(
						"Ocorreu um erro ao interagir com a base de dados: [")
						.append(msg).append("]").toString());
	}

	public DAOException(Throwable t) {
		this.cause = t;
		logger.log(Level.SEVERE,
				new StringBuilder(
						"Ocorreu um erro ao interagir com a base de dados: [")
						.append(cause.getMessage()).append("]").toString(),
				cause);
	}

	public DAOException(ErrorDAO error, Throwable cause) {
		this.error = error;
		logger.log(Level.SEVERE, new StringBuilder(error.message).append(" [")
				.append(cause.getMessage()).append("]").append(" code=[")
				.append(error.code).append("]").toString());
	}

	public DAOException(ErrorDAO error, String message, Throwable cause) {
		this.error = error;
		this.message = message;
		this.cause = cause;

		logger.log(Level.SEVERE,
				new StringBuilder(message).append(" [").append(error.message)
						.append("] ").append(" code=[").append(error.code)
						.append("]").toString());

	}

	public DAOException(String msg, Throwable t) {
		this.message = msg;
		this.cause = t;
		logger.log(Level.SEVERE,
				new StringBuilder(msg).append(" [").toString(), cause);
	}

	public DAOException(ErrorDAO error) {
		this.error = error;
		this.message = error.getMessage();

		logger.log(
				Level.SEVERE,
				new StringBuilder(error.message).append(" code=[")
						.append(error.code).append("]").toString());
	}

	public ErrorDAO getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getCause() {
		return cause;
	}

}
