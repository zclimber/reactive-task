package ru.mkorchagin.sd.reactive.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.mkorchagin.sd.reactive.domain.User;

public interface UserRepo extends ReactiveMongoRepository<User, String> {
}
