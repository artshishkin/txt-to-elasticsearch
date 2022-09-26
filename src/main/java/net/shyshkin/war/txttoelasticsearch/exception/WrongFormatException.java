package net.shyshkin.war.txttoelasticsearch.exception;

public class WrongFormatException extends RuntimeException {

    public WrongFormatException() {
        super();
    }

    public WrongFormatException(String message) {
        super(message);
    }

    public WrongFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongFormatException(Throwable cause) {
        super(cause);
    }

    public static boolean isCauseOf(Throwable e) {
        if (e == null) return false;
        return e instanceof WrongFormatException || isCauseOf(e.getCause());
    }
}
