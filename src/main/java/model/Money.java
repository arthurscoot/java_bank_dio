package model;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Money {

    private final List<MoneyAudit> history = new ArrayList<>();

    public Money(final MoneyAudit history) {
        this.history.add(history);
    }

    public void addHistory(final MoneyAudit history){
        this.history.add(history);
    }

    // Getter manual para history
    public List<MoneyAudit> getHistory() {
        return history;
    }
}
