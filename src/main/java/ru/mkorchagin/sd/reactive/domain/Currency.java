package ru.mkorchagin.sd.reactive.domain;

import org.springframework.data.annotation.Id;

public class Currency {
    @Id
    private String name;
    private Double thisToDollar;

    public Currency() {
    }

    public Currency(String name, Double thisToDollar) {
        this.name = name;
        this.thisToDollar = thisToDollar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getThisToDollar() {
        return thisToDollar;
    }

    public void setThisToDollar(Double thisToDollar) {
        this.thisToDollar = thisToDollar;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", thisToDollar=" + thisToDollar +
                '}';
    }

    public double priceFromThisToDollars(double price) {
        return price * thisToDollar;
    }

    public double priceFromDollarToThis(double price) {
        return price / thisToDollar;
    }
}
