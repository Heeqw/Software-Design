package tdd.dicegame.p2;

public class Console {
    public static void main(String[] args) {
        boolean isWin = new DiceGame().play();
        if (isWin) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose!");
        }
    }
}
