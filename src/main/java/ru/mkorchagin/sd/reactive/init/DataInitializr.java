package ru.mkorchagin.sd.reactive.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.mkorchagin.sd.reactive.domain.Currency;
import ru.mkorchagin.sd.reactive.domain.Item;
import ru.mkorchagin.sd.reactive.domain.User;
import ru.mkorchagin.sd.reactive.repo.CurrencyRepo;
import ru.mkorchagin.sd.reactive.repo.ItemRepo;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

@Component
public class DataInitializr implements CommandLineRunner {

    private final CurrencyRepo currencyRepo;
    private final ItemRepo itemRepo;
    private final UserRepo userRepo;

    public DataInitializr(CurrencyRepo currencyRepo, ItemRepo itemRepo, UserRepo userRepo) {
        this.currencyRepo = currencyRepo;
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Currency[] currencies = {new Currency("USD", 1.),
                new Currency("EUR", 1.1),
                new Currency("RUB", 0.02)};
        Item[] items = {new Item("Beer", 1., currencies[1]), new Item("Vodka", 100., currencies[2])};
        User[] users = {new User("John", currencies[0]), new User("Ivan", currencies[2])};
        currencyRepo.deleteAll().thenMany(
                Flux.just(currencies).flatMap(currencyRepo::save)
        ).then(itemRepo.deleteAll()).thenMany(
                Flux.just(items).flatMap(itemRepo::save)
        ).then(userRepo.deleteAll()).thenMany(
                Flux.just(users).flatMap(userRepo::save)
        ).blockLast();
    }
}
