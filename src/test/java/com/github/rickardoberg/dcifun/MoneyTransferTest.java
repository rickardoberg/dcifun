package com.github.rickardoberg.dcifun;

import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Test doing a money transfer
 */
public class MoneyTransferTest
{
    @Test
    public void testTransfer()
    {
        Account from = new Account(100);
        Account to = new Account(0);
        MoneyTransfer moneyTransfer = new MoneyTransfer(from, to);

        // Get bind function
        Function<MoneyTransfer, Function<MoneyTransfer.Transfer, MoneyTransfer>> bind = MoneyTransfer.bind();

        // Bind to a particular context
        Function<MoneyTransfer.Transfer, MoneyTransfer> boundContext = bind.apply(moneyTransfer);

        // Perform transfer
        moneyTransfer = boundContext.apply(new MoneyTransfer.Transfer(50));

        assertThat(moneyTransfer.getFrom().getBalance(), equalTo(50));
        assertThat(moneyTransfer.getTo().getBalance(), equalTo(50));
    }
}
