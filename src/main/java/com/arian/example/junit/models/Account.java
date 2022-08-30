package com.arian.example.junit.models;

import java.math.BigDecimal;
import java.util.Locale;

import com.arian.example.junit.exceptions.InsufficientFundsException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Account {

   private String person;

   private Bank bank;

   private BigDecimal balance;

   public Account(final String person, final BigDecimal balance) {
      this.person = person.toUpperCase(Locale.ROOT);
      this.balance = balance;
   }

   /**
    * BigDecimal IS INMUTABLE!
    **/
   public void debit(final BigDecimal monto) {
      if (monto.compareTo(this.balance) == 1) {
         throw new InsufficientFundsException("Insufficient funds or money!");
      }
      this.balance = this.balance.subtract(monto);
   }

   public void credit(final BigDecimal monto) {
      this.balance = this.balance.add(monto);
   }
}
