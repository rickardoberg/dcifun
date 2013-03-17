package com.github.rickardoberg.dcifun;

import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Test Account
 */
public class AccountTest
{
    @Test
    public void testDeposit()
    {
        Account acct = new Account(100);

        Function<Account, Function<Integer, Account>> fn = Account.deposit();

        acct = fn.apply(acct).apply(50);

        assertThat(acct.getBalance(), equalTo(150));
    }

    @Test
    public void testWithdraw()
    {
        Account acct = new Account(100);

        Function<Account, Function<Integer, Account>> fn = Account.withdraw();

        acct = fn.apply(acct).apply(50);

        assertThat(acct.getBalance(), equalTo(50));
    }
}
