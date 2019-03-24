package ru.mkorchagin.sd.reactive;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

public class UsersTests extends WebTests {
    @Autowired
    private UserRepo userRepo;

    @Test
    public void itemAddLoads() {
        testClient.get().uri("/addUser")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    public void itemAddAction() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", "testUserTrump");
        formData.add("currency", "USD");
        testClient.post().uri("/addUser").body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
        testClient.post().uri("/addUser").body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Void.class);
        Assert.assertNotNull(userRepo.findById("testUserTrump").block());
    }
}
