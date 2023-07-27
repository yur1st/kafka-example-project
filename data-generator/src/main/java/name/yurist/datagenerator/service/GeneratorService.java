package name.yurist.datagenerator.service;

import name.yurist.datagenerator.domain.Account;
import name.yurist.datagenerator.domain.Card;
import name.yurist.datagenerator.domain.CardType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class GeneratorService {

    private final Random random = new Random();

    public List<Card> generateCards(int quantity) {
        List<Card> cards = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            String lastNum = String.valueOf(random.nextInt(quantity));
            CardType cardType = CardType.values()[random.nextInt(0, 3)];
            Card card = new Card("2200" + String.format("%0" + (12 - lastNum.length()) + "d%s", 0, lastNum), cardType, lastNum);
            cards.add(card);
        }
        return cards;
    }

    public List<Account> generateAccount(int quantity) {
        List<Account> accounts = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            String lastNum = String.valueOf(random.nextInt(quantity));
            Account accRub = new Account("40817810" + String.format("%0" + (12 - lastNum.length()) + "d%s", 0, lastNum), lastNum);
            Account accEur = new Account("40817978" + String.format("%0" + (12 - lastNum.length()) + "d%s", 0, lastNum), lastNum);
            Account accUsd = new Account("40817840" + String.format("%0" + (12 - lastNum.length()) + "d%s", 0, lastNum), lastNum);
            accounts.add(accRub);
            accounts.add(accEur);
            accounts.add(accUsd);
        }
        return accounts;
    }


}
