package tdd.dicegame.p1;

public class DiceGame {

    public static void main(String[] args) {
        // 生成两个1到6之间的随机数，加起来大于6输出赢，否则输
        int num1 = (int) (Math.random() * 6) + 1;
        int num2 = (int) (Math.random() * 6) + 1;
        int sum = num1 + num2;
        if (sum > 6) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose!");
        }
    }
}
