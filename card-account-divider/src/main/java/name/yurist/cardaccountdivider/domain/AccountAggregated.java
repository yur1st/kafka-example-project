package name.yurist.cardaccountdivider.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountAggregated {

    private String agreNo;

    private List<Account> accounts = new ArrayList<>();
}
