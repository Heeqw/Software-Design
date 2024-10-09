package refactoring.rental.p2;

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
            double thisAmount = each.getAmount();
            result += "\t" + each.getMovie().getTitle() + "\t" + String.format("%.2f", thisAmount) + "\n";
        }
        double totalAmount = getTotalAmount();
        int frequentRenterPoints = getTotalFrequentRenterPoints();
        result += "Amount owned is " + String.format("%.2f", totalAmount) + "\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points";
        return result;
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        for (Rental each : this.rentals) {
            totalAmount += each.getAmount();
        }
        return totalAmount;
    }

    public int getTotalFrequentRenterPoints() {
        int totalFrequentRenterPoints = 0;
        for (Rental each : this.rentals) {
            totalFrequentRenterPoints += each.getFrequentRenterPoints();
        }
        return totalFrequentRenterPoints;
    }

}
