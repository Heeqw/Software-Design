package refactoring.rental.p3.pricestrategy;

public class NewReleaseStrategy implements PriceStrategy {

    private static PriceStrategy instance;

    private NewReleaseStrategy() {
    }

    public static PriceStrategy getInstance() {
        if (instance == null) {
            instance = new NewReleaseStrategy();
        }
        return instance;
    }

    @Override
    public double getCharge(int daysRented) {
        return daysRented * 3;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        if (daysRented > 1) {
            return 2;
        } else {
            return 1;
        }
    }

}
