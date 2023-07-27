package name.yurist.cardaccountdivider.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import name.yurist.cardaccountdivider.dto.AccountDto;
import name.yurist.cardaccountdivider.dto.CardDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    private String number;

    private CardType cardType;

    private String agrNo;

    private Set<Account> accounts;

    public static Card fromDtos(CardDto cardDto, AccountAggregated accounts) {
        return new Card(cardDto.getNumber(), cardDto.getCardType(), cardDto.getAgrNo(),
                new HashSet<>(accounts.getAccounts()));
    }

}
