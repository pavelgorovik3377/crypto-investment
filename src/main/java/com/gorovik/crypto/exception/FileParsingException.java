package com.gorovik.crypto.exception;

public class FileParsingException extends RuntimeException {

    public FileParsingException() {
        super();
    }

    public FileParsingException(String message) {
        super(message);
    }

    public FileParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileParsingException(Throwable cause) {
        super(cause);
    }

    protected FileParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
