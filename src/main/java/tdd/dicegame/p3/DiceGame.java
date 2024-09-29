package tdd.dicegame.p3;

public class DiceGame {
    public boolean play(RandomGen rand) {
        int roll1 = rand.nextInt(6) + 1;
        int roll2 = rand.nextInt(6) + 1;
        System.out.println("Player rolled " + roll1 + " and " + roll2);
        int sum = roll1 + roll2;
        if (sum > 6)
            return true;
        else
            return false;
    }
}
