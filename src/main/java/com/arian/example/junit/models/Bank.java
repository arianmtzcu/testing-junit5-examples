package com.arian.example.junit.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Bank {

   private List<Account> accounts;

   private String name;

   public Bank() {
      accounts = new ArrayList<>();
   }

   public void addAccount(final Account account) {
      account.setBank(this);
      accounts.add(account);
   }

   public void transfer(final Account origin, final Account destination, final BigDecimal amount) {
      origin.debit(amount);
      destination.credit(amount);
   }

}
