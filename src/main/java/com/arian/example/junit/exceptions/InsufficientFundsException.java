package com.arian.example.junit.exceptions;

public class InsufficientFundsException extends RuntimeException {

   public InsufficientFundsException(final String message) {
      super(message);
   }

}
