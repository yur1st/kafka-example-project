package name.yurist.datagenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {

    private String number;

    private CardType cardType;

    private String agrNo;

}
