package name.yurist.mvcprocessor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    private String number;

    private CardType cardType;

    private String agrNo;

    private Set<Account> accounts;

    private Person person;

}
