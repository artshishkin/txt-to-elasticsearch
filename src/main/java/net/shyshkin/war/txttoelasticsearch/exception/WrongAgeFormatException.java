package net.shyshkin.war.txttoelasticsearch.exception;

public class WrongAgeFormatException extends RuntimeException{

    public WrongAgeFormatException() {
        super();
    }

    public WrongAgeFormatException(String message) {
        super(message);
    }

    public WrongAgeFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAgeFormatException(Throwable cause) {
        super(cause);
    }
}
