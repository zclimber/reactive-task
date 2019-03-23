package ru.mkorchagin.sd.reactive.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.mkorchagin.sd.reactive.domain.Currency;

public interface CurrencyRepo extends ReactiveMongoRepository<Currency, String> {
}
