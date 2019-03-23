package ru.mkorchagin.sd.reactive.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.mkorchagin.sd.reactive.domain.Item;

public interface ItemRepo extends ReactiveMongoRepository<Item, String> {
}
