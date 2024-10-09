package tdd.dicegame.livedemo;

public class ConsoleGame {
    public static void main(String[] args) {
        DiceGame game = new DiceGame();
        RandomGen random = new RandomGen() {
            public int nextInt(int bound) {
                return (int) (Math.random() * bound) + 1;
            }
        };
        boolean result = game.play(random);
        if (result) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose!");
        }

    }
}
