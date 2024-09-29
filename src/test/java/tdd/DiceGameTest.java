package tdd;

import org.junit.Test;

import tdd.dicegame.p3.DiceGame;
import tdd.dicegame.p3.RandomGen;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

class MockRandomGen implements RandomGen {
    private ArrayList<Integer> sequence = new ArrayList<Integer>();

    public MockRandomGen(int... sequence) {
        for (int i : sequence) {
            this.sequence.add(i);
        }
    }

    public int nextInt(int n) {
        if (sequence.isEmpty()) {
            throw new RuntimeException("No more numbers in sequence");
        }
        int result = sequence.remove(0);
        return result;
    }
}

public class DiceGameTest {

    @Test
    public void testRollDice() {
        DiceGame game = new DiceGame();
        boolean isWin = game.play(new MockRandomGen(1, 2));
        assertFalse(isWin);
    }

}
