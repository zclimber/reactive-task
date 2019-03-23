package ru.mkorchagin.sd.reactive.web;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

import java.util.HashMap;
import java.util.Map;

@Component
public class IndexHandler extends BaseHandler {

    private final UserRepo userRepo;

    public IndexHandler(UserRepo userRepo) {
        super(userRepo);
        this.userRepo = userRepo;
    }

    public Mono<ServerResponse> indexView(ServerRequest request) {
        Map<String, Object> modelAttributes = new HashMap<>();
        modelAttributes.put("users", new ReactiveDataDriverContextVariable(
                userRepo.findAll()
        ));
        return ServerResponse.ok().render("index", modelAttributes);
    }
}
