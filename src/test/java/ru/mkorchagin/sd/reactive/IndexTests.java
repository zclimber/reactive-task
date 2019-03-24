package ru.mkorchagin.sd.reactive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mkorchagin.sd.reactive.domain.User;
import ru.mkorchagin.sd.reactive.repo.UserRepo;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexTests extends WebTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void indexLoads() {
        testClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    public void indexHasAllUsers() {
        List<User> users = userRepo.findAll().collectList().block();
        testClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(body -> {
            Assert.assertNotNull(body.getResponseBody());
            String str = new String(body.getResponseBody(), StandardCharsets.UTF_8);
            for (User user : users) {
                Assert.assertTrue(str.contains(user.getName()));
            }
        });
    }
}
