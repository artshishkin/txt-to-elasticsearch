package net.shyshkin.war.txttoelasticsearch.exception;

public class WebApiServiceException extends RuntimeException{
    public WebApiServiceException() {
        super();
    }

    public WebApiServiceException(String message) {
        super(message);
    }

    public WebApiServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebApiServiceException(Throwable cause) {
        super(cause);
    }
}
