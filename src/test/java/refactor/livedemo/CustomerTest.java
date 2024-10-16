package refactor.livedemo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import refactoring.livedemo.Customer;
import refactoring.livedemo.Movie;
import refactoring.livedemo.Rental;

public class CustomerTest {

    @Test
    public void testStatement() {
        Customer customer = new Customer("Tom");
        Movie movie1 = new Movie("movie1", Movie.NEW_RELEASE);
        Movie movie2 = new Movie("movie2", Movie.REGULAR);
        Movie movie3 = new Movie("movie3", Movie.CHILDREN);
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
