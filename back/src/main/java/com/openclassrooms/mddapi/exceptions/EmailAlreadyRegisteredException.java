package com.openclassrooms.mddapi.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
  public EmailAlreadyRegisteredException(String message) {
      super(message);
  }
}
