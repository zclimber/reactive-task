package ru.mkorchagin.sd.reactive.web;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;
import ru.mkorchagin.sd.reactive.domain.User;
import ru.mkorchagin.sd.reactive.repo.CurrencyRepo;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

import java.util.HashMap;
import java.util.Map;

public class UserHandler extends BaseHandler {
    private final UserRepo userRepo;
    private final CurrencyRepo currencyRepo;

    public UserHandler(UserRepo userRepo, CurrencyRepo currencyRepo) {
        super(userRepo);
        this.userRepo = userRepo;
        this.currencyRepo = currencyRepo;
    }

    public Mono<ServerResponse> addUserView(ServerRequest request) {
        Map<String, Object> modelAttributes = new HashMap<>();
        modelAttributes.put("currencies", new ReactiveDataDriverContextVariable(
                currencyRepo.findAll()
        ));
        return ServerResponse.ok().render("addUser", modelAttributes);
    }

    public Mono<ServerResponse> addUser(ServerRequest request) {
        return request.formData().flatMap(
                data -> userRepo.findById(data.getFirst("user")).flatMap(
                        name -> ServerResponse.badRequest().body(BodyInserters.fromObject("User already registered"))
                ).switchIfEmpty(
                        currencyRepo.findById(data.getFirst("currency")).flatMap(
                                currency -> userRepo.save(new User(data.getFirst("user"), currency)))
                                .flatMap(e -> ServerResponse.ok().body(BodyInserters.fromObject("User " + e.getName() + " registered")))
                )
        ).onErrorResume(e -> ServerResponse.badRequest().build());
    }
}
