package br.com.capelli.secretsanta.exception;

public class RestricaoSorteioException extends Exception {

    private static final long serialVersionUID = 1L;

    public RestricaoSorteioException() {
        super();
    }

    public RestricaoSorteioException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestricaoSorteioException(String message) {
        super(message);
    }

    public RestricaoSorteioException(Throwable cause) {
        super(cause);
    }

}
