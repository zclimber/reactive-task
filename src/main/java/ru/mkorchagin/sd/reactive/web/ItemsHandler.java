package ru.mkorchagin.sd.reactive.web;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;
import ru.mkorchagin.sd.reactive.domain.Item;
import ru.mkorchagin.sd.reactive.repo.CurrencyRepo;
import ru.mkorchagin.sd.reactive.repo.ItemRepo;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

import java.util.HashMap;
import java.util.Map;

@Component
public class ItemsHandler extends BaseHandler {

    private final CurrencyRepo currencyRepo;
    private final ItemRepo itemRepo;

    public ItemsHandler(UserRepo userRepo, CurrencyRepo currencyRepo, ItemRepo itemRepo) {
        super(userRepo);
        this.currencyRepo = currencyRepo;
        this.itemRepo = itemRepo;
    }

    public Mono<ServerResponse> listAllItemsView(ServerRequest request) {
        return userCheckingHandler(request, user -> {
            Map<String, Object> modelAttributes = new HashMap<>();
            modelAttributes.put("user", user);
            modelAttributes.put("items", new ReactiveDataDriverContextVariable(
                    itemRepo.findAll().map(e -> e.converted(user.getPriceViewCurrency()))
            ));
            return ServerResponse.ok().render("listItems", modelAttributes);
        });
    }

    public Mono<ServerResponse> addItemView(ServerRequest request) {
        Map<String, Object> modelAttributes = new HashMap<>();
        modelAttributes.put("currencies", new ReactiveDataDriverContextVariable(
                currencyRepo.findAll())
        );
        return ServerResponse.ok().render("addItem", modelAttributes);
    }

    public Mono<ServerResponse> addItem(ServerRequest request) {
        return request.formData().flatMap(
                data -> itemRepo.findById(data.getFirst("name")).flatMap(
                        name -> ServerResponse.badRequest().body(BodyInserters.fromObject("Item already present"))
                ).switchIfEmpty(
                        currencyRepo.findById(data.getFirst("currency")).flatMap(
                                currency -> itemRepo.save(new Item(data.getFirst("name"), Double.parseDouble(data.getFirst("price")), currency)))
                                .flatMap(e -> ServerResponse.ok().body(BodyInserters.fromObject("Item " + e.getName() + " created")))
                )
        ).onErrorResume(e -> ServerResponse.badRequest().build());
    }
}
