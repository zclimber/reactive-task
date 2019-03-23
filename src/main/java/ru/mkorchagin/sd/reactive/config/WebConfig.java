package ru.mkorchagin.sd.reactive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.mkorchagin.sd.reactive.web.ItemsHandler;
import ru.mkorchagin.sd.reactive.web.UserHandler;

@EnableWebFlux
public class WebConfig {
    @Autowired
    ItemsHandler itemsHandler;
    @Autowired
    UserHandler usersHandler;

    @Bean
    public RouterFunction<ServerResponse> itemsHandlerRouter() {
        return RouterFunctions.route()
                .GET("/list", itemsHandler::listAllItemsView)
                .GET("/addItem", itemsHandler::addItemView)
                .POST("/addItem", itemsHandler::addItem)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> usersHandlerRouter() {
        return RouterFunctions.route()
                .GET("/addUser", usersHandler::addUserView)
                .POST("/addUser", usersHandler::addUser)
                .build();
    }
}
