package ru.mkorchagin.sd.reactive.domain;

import org.springframework.data.annotation.Id;

public class Item {
    @Id
    private String name;
    private Double price;
    private Currency currency;

    public Item() {
    }

    public Item(String name, Double price, Currency currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }

    public double priceInThisCurrency(Currency currency) {
        return currency.priceFromDollarToThis(getCurrency().priceFromThisToDollars(getPrice()));
    }

    public Item converted(Currency newCurrency) {
        return new Item(getName(), priceInThisCurrency(newCurrency), newCurrency);
    }
}
