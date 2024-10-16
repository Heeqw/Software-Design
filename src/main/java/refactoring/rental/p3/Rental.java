package refactoring.rental.p3;

public class Rental {

    private Movie movie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public int getFrequentRenterPoints() {
        return this.getMovie().getPriceCode().getFrequentRenterPoints(this.getDaysRented());
    }

    public double getAmount() {
        return this.getMovie().getPriceCode().getCharge(this.getDaysRented());

    }

}