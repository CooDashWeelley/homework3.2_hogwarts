package ru.hogwarts.school.exception;

public class AgeLessOneException extends RuntimeException {
    public AgeLessOneException(String message) {
        super(message);
    }
}
