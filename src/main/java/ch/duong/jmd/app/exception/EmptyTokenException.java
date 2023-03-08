package ch.duong.jmd.#APP_ABBREVIATION.exception;

public class EmptyTokenException extends Exception {
    public EmptyTokenException() {
        super();
    }

    public EmptyTokenException(String msg) {
        super(msg);
    }
}
