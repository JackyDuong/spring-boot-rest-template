package ch.duong.jmd.#APP_ABBREVIATION.exception;

public class EmptyDataException extends Exception {
    public EmptyDataException() {
        super();
    }

    public EmptyDataException(String msg) {
        super(msg);
    }
}
