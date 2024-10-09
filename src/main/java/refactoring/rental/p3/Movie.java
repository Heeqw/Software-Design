package refactoring.rental.p3;

import refactoring.rental.p3.pricestrategy.PriceStrategy;

public class Movie {
    private String title;
    private PriceStrategy priceCode;

    public Movie(String title, PriceStrategy priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public PriceStrategy getPriceCode() {
        return priceCode;
    }
}