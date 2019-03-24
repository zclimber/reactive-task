package ru.mkorchagin.sd.reactive;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import ru.mkorchagin.sd.reactive.domain.User;
import ru.mkorchagin.sd.reactive.repo.ItemRepo;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

public class ItemsTests extends WebTests {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ItemRepo itemRepo;

    @Test
    public void itemsListLoads() {
        User user = userRepo.findAll().blockFirst();
        testClient.get().uri("/listItems?user=" + user.getName())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
        testClient.get().uri("/listItems")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Void.class);
    }

    @Test
    public void itemAddLoads() {
        testClient.get().uri("/addItem")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    public void itemAddAction() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", "testProductBurger");
        formData.add("price", "150");
        formData.add("currency", "USD");
        testClient.post().uri("/addItem").body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
        testClient.post().uri("/addItem").body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Void.class);
        Assert.assertNotNull(itemRepo.findById("testProductBurger").block());
    }
}
