package edu.javacourse.register.exception;

public class CheckPersonException extends  Exception {
    public CheckPersonException() {
    }

    public CheckPersonException(String s) {
        super(s);
    }

    public CheckPersonException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CheckPersonException(Throwable throwable) {
        super(throwable);
    }

    public CheckPersonException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
