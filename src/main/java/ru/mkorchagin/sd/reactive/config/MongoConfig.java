package ru.mkorchagin.sd.reactive.config;

import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories(basePackages = {"ru.mkorchagin.sd.reactive.repo"})
public class MongoConfig {
}
