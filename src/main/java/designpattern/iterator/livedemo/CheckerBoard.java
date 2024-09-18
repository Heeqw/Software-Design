package designpattern.iterator.livedemo;

import java.util.Iterator;

enum ChessType {
    black, white, empty
}

class Cell {
    private ChessType chessType;
    private int x;
    private int y;

    public Cell(int x, int y, ChessType chessType) {
        this.x = x;
        this.y = y;
        this.chessType = chessType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ChessType getChessType() {
        return chessType;
    }

    @Override
    public String toString() {

        return "Cell{" +
                "chessType=" + chessType +
                ", x=" + x +
                ", y=" + y +
                '}';

    }
}

public class CheckerBoard implements Iterable<Cell> {

    private ChessType[][] chessBoard;
    private int max_x;
    private int max_y;

    public CheckerBoard(int max_x, int max_y) {
        this.max_x = max_x;
        this.max_y = max_y;
        chessBoard = new ChessType[max_x][max_y];
    }

    public void putChess(int x, int y, ChessType chessType) {
        chessBoard[x][y] = chessType;
    }

    public ChessType getChess(int x, int y) {

        ChessType t = chessBoard[x][y];
        if (t == null)
            return ChessType.empty;
        else
            return t;
    }

    public int getMax_x() {
        return max_x;
    }

    public int getMax_y() {
        return max_y;
    }

    @Override
    public Iterator<Cell> iterator() {
        // 实现Iterator接口，用于遍历棋盘上的每个棋子
        return new Iterator<Cell>() {

            // 初始化棋盘坐标的x轴位置
            private int x = 0;
            // 初始化棋盘坐标的y轴位置
            private int y = 0;

            @Override
            public boolean hasNext() {
                // 判断棋盘上是否有下一个棋子
                return x < getMax_x() && y < getMax_y();
            }

            @Override
            public Cell next() {
                // 获取当前坐标上的棋子，并移动到下一个位置
                Cell cell = new Cell(x, y, getChess(x, y));
                x++; // 在x轴上移动到下一个位置
                if (x >= getMax_x()) {
                    // 如果x轴达到最大值，则重置x轴并移动y轴到下一个位置
                    x = 0;
                    y++;
                }
                return cell;
            }
        };
    }

}

class Test {

    public static void main(String[] args) {
        CheckerBoard checkerBoard = new CheckerBoard(4, 4);
        checkerBoard.putChess(3, 3, ChessType.white);
        checkerBoard.putChess(2, 2, ChessType.black);

        System.out.println("===>通过内部结构遍历：");
        for (int y = 0; y < checkerBoard.getMax_y(); y++)
            for (int x = 0; x < checkerBoard.getMax_x(); x++)
                System.out.print(checkerBoard.getChess(x, y) + " ");

        System.out.println("===>通过迭代器遍历：");
        Iterator<Cell> cellIterator = checkerBoard.iterator();
        while (cellIterator.hasNext())
            System.out.println(cellIterator.next());

        System.out.println("===>通过foreach遍历：");
        for (Cell cell : checkerBoard) {
            System.out.println(cell);
        }

    }

}