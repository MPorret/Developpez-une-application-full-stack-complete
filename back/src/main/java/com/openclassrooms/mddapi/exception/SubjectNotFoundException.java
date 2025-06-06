package com.openclassrooms.mddapi.exception;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(Integer subjectId) {
        super("Subject not found with id : " + subjectId);
    }
}
