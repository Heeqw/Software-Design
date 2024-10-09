package tdd.dicegame.livedemo;

public class DiceGame {

    public boolean play(RandomGen random) {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int sum = dice1 + dice2;
        if (sum > 6)
            return true;
        else
            return false;
    }

}
