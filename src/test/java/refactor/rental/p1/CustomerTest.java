package refactor.rental.p1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import refactoring.rental.p1.Customer;
import refactoring.rental.p1.Movie;
import refactoring.rental.p1.Rental;
import refactoring.rental.p1.RentalType;

public class CustomerTest {

    @Test
    public void testStatement() {
        Customer customer = new Customer("Tom");
        Movie movie1 = new Movie("movie1", RentalType.NEW_RELEASE);
        Movie movie2 = new Movie("movie2", RentalType.REGULAR);
        Movie movie3 = new Movie("movie3", RentalType.CHILDREN);
        customer.addRental(new Rental(movie1, 3));
        customer.addRental(new Rental(movie2, 4));
        customer.addRental(new Rental(movie3, 5));
        String result = customer.statement();

        String expectString = "Rental Record for Tom\n" +
                "\tmovie1\t9.00\n" +
                "\tmovie2\t5.00\n" +
                "\tmovie3\t4.50\n" +
                "Amount owned is 18.50\n" +
                "You earned 4 frequent renter points";

        assertEquals(expectString, result);
    }

}
