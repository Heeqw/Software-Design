package refactoring.rental.p3.pricestrategy;

public class ChildrenStrategy implements PriceStrategy {

    private static PriceStrategy instance = new ChildrenStrategy();

    private ChildrenStrategy() {
    }

    public static PriceStrategy getInstance() {
        return instance;
    }

    @Override
    public double getCharge(int daysRented) {
        double charge = 1.5;
        if (daysRented > 3) {
            charge += (daysRented - 3) * 1.5;
        }
        return charge;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return 1;
    }

}
