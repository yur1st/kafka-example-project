package name.yurist.cardaccountdivider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import name.yurist.cardaccountdivider.domain.CardType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private String number;

    private CardType cardType;

    private String agrNo;

}
