package tdd.livedemo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tdd.calc.Calc;

public class CalcTest {

    @Test
    public void testAdd() {
        Calc calc = new Calc();
        int result = calc.add(2, 3);
        assertEquals(5, result);
    }

}
