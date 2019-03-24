package ru.mkorchagin.sd.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.mkorchagin.sd.reactive.web.IndexHandler;
import ru.mkorchagin.sd.reactive.web.ItemsHandler;
import ru.mkorchagin.sd.reactive.web.UsersHandler;

@Configuration
public class RouterConfig {
    private final ItemsHandler itemsHandler;
    private final UsersHandler usersHandler;
    private final IndexHandler indexHandler;

    public RouterConfig(ItemsHandler itemsHandler, UsersHandler usersHandler, IndexHandler indexHandler) {
        this.itemsHandler = itemsHandler;
        this.usersHandler = usersHandler;
        this.indexHandler = indexHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> itemsHandlerRouter() {
        return RouterFunctions.route()
                .GET("/listItems", itemsHandler::listAllItemsView)
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

    @Bean
    public RouterFunction<ServerResponse> indexHandlerRouter() {
        return RouterFunctions.route()
                .GET("/", indexHandler::indexView)
                .POST("/addUser", indexHandler::indexView)
                .build();
    }
}
