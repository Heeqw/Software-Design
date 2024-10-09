package refactoring.rental.p3.pricestrategy;

public class RegularStrategy implements PriceStrategy {

    private static PriceStrategy instance;

    private RegularStrategy() {
    }

    public static PriceStrategy getInstance() {
        if (instance == null) {
            instance = new RegularStrategy();
        }
        return instance;
    }

    @Override
    public double getCharge(int daysRented) {
        double thisAmount = 2;
        if (daysRented > 2)
            thisAmount += (daysRented - 2) * 1.5;
        return thisAmount;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return 1;
    }
}
