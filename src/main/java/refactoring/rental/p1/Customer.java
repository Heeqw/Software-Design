package refactoring.rental.p1;

import java.util.List;
import java.util.ArrayList;

public class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public String statement() {

        String result = "Rental Record for " + this.getName() + "\n";
        for (Rental each : this.rentals) {
            double thisAmount = getThisAmount(each);
            result += "\t" + each.getMovie().getTitle() + "\t" + String.format("%.2f", thisAmount) + "\n";
        }
        double totalAmount = getTotalAmount();
        int frequentRenterPoints = getTotalFrequentRenterPoints();
        result += "Amount owned is " + String.format("%.2f", totalAmount) + "\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points";
        return result;
    }

    private double getTotalAmount() {
        double totalAmount = 0;
        for (Rental each : this.rentals) {
            totalAmount += getThisAmount(each);
        }
        return totalAmount;
    }

    private int getTotalFrequentRenterPoints() {
        int totalFrequentRenterPoints = 0;
        for (Rental each : this.rentals) {
            totalFrequentRenterPoints += getThisFP(each);
        }
        return totalFrequentRenterPoints;
    }

    private int getThisFP(Rental each) {
        int frequentRenterPoints = 1;
        if (each.getMovie().getPriceCode() == RentalType.NEW_RELEASE && each.getDaysRented() > 1)
            frequentRenterPoints++;
        return frequentRenterPoints;
    }

    private double getThisAmount(Rental each) {
        double thisAmount = 0;
        switch (each.getMovie().getPriceCode()) {
            case REGULAR: {
                thisAmount += 2;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
                break;
            }
            case NEW_RELEASE: {
                thisAmount += each.getDaysRented() * 3;
                break;
            }
            case CHILDREN: {
                thisAmount += 1.5;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
                break;
            }
        }
        return thisAmount;
    }

}
