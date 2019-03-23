package ru.mkorchagin.sd.reactive.domain;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    private String name;
    private Currency priceViewCurrency;

    public User() {
    }

    public User(String name, Currency priceViewCurrency) {
        this.name = name;
        this.priceViewCurrency = priceViewCurrency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getPriceViewCurrency() {
        return priceViewCurrency;
    }

    public void setPriceViewCurrency(Currency priceViewCurrency) {
        this.priceViewCurrency = priceViewCurrency;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", priceViewCurrency=" + priceViewCurrency +
                '}';
    }
}
