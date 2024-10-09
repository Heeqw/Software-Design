package refactor.rental.p2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import refactoring.rental.p2.Movie;
import refactoring.rental.p2.Rental;
import refactoring.rental.p2.RentalType;

public class RentalTest {

    @Test
    public void testGetAmount() {
        Movie movie1 = new Movie("movie1", RentalType.NEW_RELEASE);
        Movie movie2 = new Movie("movie2", RentalType.REGULAR);
        Movie movie3 = new Movie("movie3", RentalType.CHILDREN);
        Rental r1 = new Rental(movie1, 3);
        Rental r2 = new Rental(movie2, 4);
        Rental r3 = new Rental(movie3, 5);

        assertEquals(9.0, r1.getAmount(), 0.01);
        assertEquals(5.0, r2.getAmount(), 0.01);
        assertEquals(4.5, r3.getAmount(), 0.01);

    }

    @Test
    public void testGetFrequentRenterPoints() {
        Movie movie1 = new Movie("movie1", RentalType.NEW_RELEASE);
        Movie movie2 = new Movie("movie2", RentalType.REGULAR);
        Movie movie3 = new Movie("movie3", RentalType.CHILDREN);
        Rental r1 = new Rental(movie1, 3);
        Rental r2 = new Rental(movie2, 4);
        Rental r3 = new Rental(movie3, 5);

        assertEquals(2, r1.getFrequentRenterPoints());
        assertEquals(1, r2.getFrequentRenterPoints());
        assertEquals(1, r3.getFrequentRenterPoints());
    }

}
