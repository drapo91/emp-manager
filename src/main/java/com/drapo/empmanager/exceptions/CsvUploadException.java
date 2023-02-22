package com.drapo.empmanager.exceptions;

public class CsvUploadException extends RuntimeException {
    public CsvUploadException(final String message) {
        super(message);
    }

    public CsvUploadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
