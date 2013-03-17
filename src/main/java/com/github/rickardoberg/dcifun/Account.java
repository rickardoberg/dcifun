package com.github.rickardoberg.dcifun;

import java.util.function.Function;

/**
 * An immutable Account that holds the balance as the state.
 *
 * Doing deposits or withdrawals creates new Account state.
 */
public class Account
{
    private int balance;

    public Account(int balance)
    {
        this.balance = balance;
    }

    public int getBalance()
    {
        return balance;
    }

    public static Function<Account, Integer> balance()
    {
        return Account::getBalance;
    }

    public static Function<Account, Function<Integer, Account>> deposit()
    {
        return account -> amount -> new Account(account.getBalance()+amount);
    }

    public static Function<Account, Function<Integer, Account>> withdraw()
    {
        return account -> amount -> new Account(account.getBalance()-amount);
    }
}
