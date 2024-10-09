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
        // int frequentRenterPoints = 1;
        // if (this.getMovie().getPriceCode() == RentalType.NEW_RELEASE &&
        // this.getDaysRented() > 1)
        // frequentRenterPoints++;
        // return frequentRenterPoints;
    }

    public double getAmount() {
        return this.getMovie().getPriceCode().getCharge(this.getDaysRented());
        // double thisAmount = 0;
        // switch (this.getMovie().getPriceCode()) {
        // case REGULAR: {
        // thisAmount += 2;
        // if (this.getDaysRented() > 2)
        // thisAmount += (this.getDaysRented() - 2) * 1.5;
        // break;
        // }
        // case NEW_RELEASE: {
        // thisAmount += this.getDaysRented() * 3;
        // break;
        // }
        // case CHILDREN: {
        // thisAmount += 1.5;
        // if (this.getDaysRented() > 3)
        // thisAmount += (this.getDaysRented() - 3) * 1.5;
        // break;
        // }
        // }
        // return thisAmount;
    }

}