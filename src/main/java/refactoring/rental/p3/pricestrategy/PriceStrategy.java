package refactoring.rental.p3.pricestrategy;

public interface PriceStrategy {
    double getCharge(int daysRented);

    int getFrequentRenterPoints(int daysRented);
}
