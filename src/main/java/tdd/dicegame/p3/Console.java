package tdd.dicegame.p3;

public class Console {
    public static void main(String[] args) {
        DiceGame game = new DiceGame();
        RandomGen rand = new RandomGen() {
            public int nextInt(int n) {
                return (int) (Math.random() * n);
            }
        };
        boolean isWin = game.play(rand);
        if (isWin) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose!");
        }
    }
}
