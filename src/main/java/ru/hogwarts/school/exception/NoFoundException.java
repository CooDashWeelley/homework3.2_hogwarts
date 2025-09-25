package ru.hogwarts.school.exception;

public class NoFoundException extends RuntimeException {
    public NoFoundException(String message) {
        super(message);
    }
}
