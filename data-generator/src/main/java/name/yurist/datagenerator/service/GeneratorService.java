package name.yurist.datagenerator.service;

import com.github.javafaker.Faker;
import name.yurist.datagenerator.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class GeneratorService {

    private final Faker faker = new Faker();
    private final String[] currencies = {"RUB", "EUR", "USD"};
    private final Random random = new Random();

    public List<Card> generateCards(int quantity) {
        List<Card> cards = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            String lastNum = String.valueOf(random.nextInt(quantity));
            CardType cardType = CardType.values()[random.nextInt(0, 3)];
            Card card = new Card(String.format("2200%%0%dd%%s".formatted(12 - lastNum.length()), 0, lastNum), cardType, lastNum);
            cards.add(card);
        }
        return cards;
    }

    public List<Account> generateAccount(int quantity) {
        List<Account> accounts = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            String lastNum = String.valueOf(random.nextInt(quantity));
            Account accRub = new Account(String.format("40817810%%0%dd%%s".formatted(12 - lastNum.length()), 0, lastNum), lastNum);
            Account accEur = new Account(String.format("40817978%%0%dd%%s".formatted(12 - lastNum.length()), 0, lastNum), lastNum);
            Account accUsd = new Account(String.format("40817840%%0%dd%%s".formatted(12 - lastNum.length()), 0, lastNum), lastNum);
            accounts.add(accRub);
            accounts.add(accEur);
            accounts.add(accUsd);
        }
        return accounts;
    }

    public List<Person> generatePerson(int quantity) {
        List<Person> personList = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            String name = faker.name().name();
            Person person = new Person(name, faker.phoneNumber().cellPhone(), String.valueOf(random.nextInt(quantity)));
            personList.add(person);
        }
        return personList;
    }

    public List<Nepc> generateNepc(int quantity, int cardQuantity) {
        List<Nepc> list = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            Nepc nepc = new Nepc();
            String lastNum = String.valueOf(random.nextInt(cardQuantity));
            nepc.setNumber(String.format("2200%%0%dd%%s".formatted(12 - lastNum.length()), 0, lastNum));
            nepc.setPhone(faker.phoneNumber().cellPhone());
            nepc.setCurrency(currencies[random.nextInt(0, 3)]);
            nepc.setSum(faker.commerce().price());
            list.add(nepc);
        }
        return list;
    }

}
