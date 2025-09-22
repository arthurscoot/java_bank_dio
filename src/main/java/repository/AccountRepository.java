package repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import exception.PixInUseException;
import model.AccountWallet;
import model.MoneyAudit;

import static java.time.temporal.ChronoUnit.SECONDS;
import static repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();

    public AccountWallet create(final List<String> pix, final long initialFunds){
        if (!accounts.isEmpty()) {
            var pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
            for (var p : pix) {
                if (pixInUse.contains(p)) {
                    throw new PixInUseException("O pix '" + p + "'já está em uso");
                }
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) throws AccountNotFoundException{
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "depósito");
    }

    public long withdraw(final String pix, final long amount) throws AccountNotFoundException{
        var source  = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) throws AccountNotFoundException{
        var source  = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "pix enviado de '" + sourcePix + "' para '" + targetPix + "'";
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public AccountWallet findByPix(final String pix) throws AccountNotFoundException {
        if (pix == null) {
            throw new IllegalArgumentException("Pix não pode ser nulo");
        }

        for (AccountWallet account : accounts) {
            if (account.getPix() != null && account.getPix().contains(pix)) {
                return account;
            }
        }

        throw new AccountNotFoundException(
            "A conta com a chave pix '" + pix + "' não existe ou foi encerrada"
        );
    }

    public List<AccountWallet> list(){
        return this.accounts;
    }

    public Map<OffsetDateTime, List<MoneyAudit>> getHistory(final String pix) throws AccountNotFoundException{
        var wallet = findByPix(pix);
        var audit = wallet.getFinancialTransactions();
        return audit.stream()
                .collect(Collectors.groupingBy(t -> t.createdAt().truncatedTo(SECONDS)));
    }

}