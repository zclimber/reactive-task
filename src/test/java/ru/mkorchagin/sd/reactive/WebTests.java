package ru.mkorchagin.sd.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebTests {
    @Autowired
    private ApplicationContext context;

    protected WebTestClient testClient;

    @Before
    public void setTestClient() {
        testClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void clientConstructs() {
        Assert.notNull(testClient, "WebTestClient is not constructed");
    }
}
