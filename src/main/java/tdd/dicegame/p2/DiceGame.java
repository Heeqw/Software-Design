package tdd.dicegame.p2;

public class DiceGame {
    public boolean isWin(int r1, int r2) {
        return (r1 + r2) > 6;
    }

    public boolean play() {
        // 生成两个1到6之间的随机数，加起来大于6输出赢，否则输
        int num1 = (int) (Math.random() * 6) + 1;
        int num2 = (int) (Math.random() * 6) + 1;
        return isWin(num1, num2);
    }
}
