package slogo.exceptions;

import slogo.visualcontroller.ErrorSeverity;

public abstract class LogicalException extends RuntimeException {

    protected ErrorSeverity myErrorSeverity;

    public LogicalException() {
        super();
    }
}