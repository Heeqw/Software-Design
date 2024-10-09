package refactor.rental.p3;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import refactoring.rental.p3.Customer;
import refactoring.rental.p3.Movie;
import refactoring.rental.p3.Rental;
import refactoring.rental.p3.RentalType;
import refactoring.rental.p3.pricestrategy.ChildrenStrategy;
import refactoring.rental.p3.pricestrategy.NewReleaseStrategy;
import refactoring.rental.p3.pricestrategy.RegularStrategy;

public class CustomerTest {

    Customer customer;

    @Before
    public void setUp() {
        customer = new Customer("Tom");
        Movie movie1 = new Movie("movie1", NewReleaseStrategy.getInstance());
        Movie movie2 = new Movie("movie2", RegularStrategy.getInstance());
        Movie movie3 = new Movie("movie3", ChildrenStrategy.getInstance());
        customer.addRental(new Rental(movie1, 3));
        customer.addRental(new Rental(movie2, 4));
        customer.addRental(new Rental(movie3, 5));
    }

    @Test
    public void testStatement() {
        String result = customer.statement();

        String expectString = "Rental Record for Tom\n" +
                "\tmovie1\t9.00\n" +
                "\tmovie2\t5.00\n" +
                "\tmovie3\t4.50\n" +
                "Amount owned is 18.50\n" +
                "You earned 4 frequent renter points";

        assertEquals(expectString, result);
    }

    @Test
    public void testTotalAmount() {
        assertEquals(18.5, customer.getTotalAmount(), 0.01);
    }

    @Test
    public void testTotalFrequentRenterPoints() {
        assertEquals(4, customer.getTotalFrequentRenterPoints());
    }

}
