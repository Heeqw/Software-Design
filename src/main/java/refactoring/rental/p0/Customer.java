package refactoring.rental.p0;

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
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for " + this.getName() + "\n";
        for (Rental each : this.rentals) {
            double thisAmount = 0;
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR: {
                    thisAmount += 2;
                    if (each.getDaysRented() > 2)
                        thisAmount += (each.getDaysRented() - 2) * 1.5;
                    break;
                }
                case Movie.NEW_RELEASE: {
                    thisAmount += each.getDaysRented() * 3;
                    break;
                }
                case Movie.CHILDREN: {
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3)
                        thisAmount += (each.getDaysRented() - 3) * 1.5;
                    break;
                }
            }
            frequentRenterPoints++;
            if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE && each.getDaysRented() > 1)
                frequentRenterPoints++;
            result += "\t" + each.getMovie().getTitle() + "\t" + String.format("%.2f", thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        result += "Amount owned is " + String.format("%.2f", totalAmount) + "\n";

        result += "You earned " + frequentRenterPoints + " frequent renter points";
        return result;
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Tom");
        Movie movie1 = new Movie("movie1", Movie.NEW_RELEASE);
        Movie movie2 = new Movie("movie2", Movie.REGULAR);
        Movie movie3 = new Movie("movie3", Movie.CHILDREN);
        customer.addRental(new Rental(movie1, 3));
        customer.addRental(new Rental(movie2, 4));
        customer.addRental(new Rental(movie3, 5));
        System.out.println(customer.statement());
    }
}
