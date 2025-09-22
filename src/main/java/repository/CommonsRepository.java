package repository;

import static lombok.AccessLevel.PRIVATE;
import static model.BankService.ACCOUNT;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import exception.NoFundsEnoughException;
import lombok.NoArgsConstructor;
import model.Money;
import model.MoneyAudit;
import model.Wallet;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    public static void checkFundsForTransaction(final Wallet source, final long amount){
        if (source.getFunds() < amount){
            throw new NoFundsEnoughException("Sua conta não tem dinheiro o suficiente para realizar essa transação");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description){
        var history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }

}
