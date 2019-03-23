package ru.mkorchagin.sd.reactive.web;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.mkorchagin.sd.reactive.domain.User;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

import java.util.function.Function;

public class BaseHandler {

    private final UserRepo userRepo;

    public BaseHandler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    protected Mono<ServerResponse> userCheckingHandler(ServerRequest request, Function<User, Mono<ServerResponse>> handler) {
        return userRepo.findById((String) request.attribute("user").get())
                .flatMap(handler)
                .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromObject("Invalid user")));
    }
}
