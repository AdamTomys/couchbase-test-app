package org.cachetester.exceptions;

public class CacheTestingException extends RuntimeException {

    public CacheTestingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
