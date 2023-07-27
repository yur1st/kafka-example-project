package name.yurist.cardaccountdivider.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import name.yurist.cardaccountdivider.dto.AccountDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String number;

    private Currency currency;

    public static Account from(AccountDto accountDto) {
        Currency cur = switch (accountDto.getNumber().substring(5,8)) {
            case "810" -> Currency.RUB;
            case "840" -> Currency.USD;
            case "978" -> Currency.EUR;
            default -> Currency.UNDEFINED;
        };
        return new Account(accountDto.getNumber(), cur);
    }
}
