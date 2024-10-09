package refactoring.rental.p2;

public class Movie {
    private String title;
    private RentalType priceCode;

    public Movie(String title, RentalType priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public RentalType getPriceCode() {
        return priceCode;
    }
}