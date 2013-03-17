package com.github.rickardoberg.dcifun;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.github.rickardoberg.dcifun.Account.deposit;
import static com.github.rickardoberg.dcifun.Account.withdraw;

/**
 * The MoneyTransfer context holds the immutable state, in this case
 * the Accounts (which are also immutable).
 *
 * Doing a money transfer involves creating and invoking functions that produce
 * a new MoneyTransfer context with new Account state.
 */
public class MoneyTransfer
{
    private Account from;
    private Account to;

    public MoneyTransfer(Account from, Account to)
    {
        this.from = from;
        this.to = to;
    }

    public Account getFrom()
    {
        return from;
    }

    public Account getTo()
    {
        return to;
    }

    /**
     * Create a function which given a MoneyTransfer can create a function to receive
     * a transfer and produce a new MoneyTransfer state
     * @return
     */
    public static Function<MoneyTransfer, Function<Transfer, MoneyTransfer>> bind()
    {
        return moneyTransfer -> transfer -> MoneySource.transfer().
                apply(moneyTransfer.getFrom(), MoneySink.transferTo().apply(moneyTransfer.getTo())).
                apply(transfer);
    }

    // MoneySource Role
    private static class MoneySource
    {
        static BiFunction<Account, Function<Transfer, Account>, Function<Transfer, MoneyTransfer>> transfer()
        {
            return (account, transferTo) -> transfer ->
                    new MoneyTransfer(withdraw().apply(account).apply(transfer.getAmount()),
                                     transferTo.apply(transfer));
        }
    }

    // MoneySink Role
    private static class MoneySink
    {
        static Function<Account, Function<Transfer, Account>> transferTo()
        {
            return account -> transfer -> deposit().apply(account).apply(transfer.getAmount());
        }
    }

    // A Transfer expressed as a command
    public static class Transfer
    {
        int amount;

        public Transfer(int amount)
        {
            this.amount = amount;
        }

        public int getAmount()
        {
            return amount;
        }
    }
}
