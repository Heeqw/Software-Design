package designpattern.iterator.livedemo;

import java.util.Iterator;
import java.util.List;

enum ChessType {
    black, white, blank
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
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

enum Direction {
    up(new Point(0, -1)),
    down(new Point(0, 1)),
    left(new Point(-1, 0)),
    right(new Point(1, 0)),
    upLeft(new Point(-1, -1)),
    upRight(new Point(1, -1)),
    downRight(new Point(1, 1)),
    downLeft(new Point(-1, 1));

    private Point p;

    Direction(Point p) {
        this.p = p;
    }

    public Point getP() {
        return p;
    }

}

public class CheckBoard {

    private ChessType[][] chessBoard;
    private int max_x;
    private int max_y;

    public CheckBoard(int max_x, int max_y) {
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
            return ChessType.blank;
        else
            return t;
    }

    public int getMax_x() {
        return max_x;
    }

    public int getMax_y() {
        return max_y;
    }

    /**
     * 返回一个迭代器，用于遍历棋盘上的Cell
     * 迭代顺序由给定的方向和起始点决定
     * 
     * @param dir   迭代方向，确定每次迭代移动的方位
     * @param start 迭代起始点，迭代从此点开始
     * @return 返回一个迭代器，用于按指定方向和起始点遍历棋盘上的Cell
     */
    public Iterator<Cell> iterator(Direction dir, Point start) {
        // 实现Iterator接口，用于遍历棋盘上给定的方向和起始点的线上的棋子
        return new Iterator<Cell>() {
            // 当前迭代的细胞的位置，初始值为start
            private Point p = start;

            /**
             * 检查迭代是否还有下一个元素
             * 
             * @return 如果有下一个元素，返回true；否则返回false
             */
            @Override
            public boolean hasNext() {
                // 检查当前位置p是否在棋盘的有效范围内
                return p.getX() < getMax_x() && p.getY() < getMax_y() && p.getX() >= 0 && p.getY() >= 0;
            }

            /**
             * 返回迭代的下一个元素，并移动到下一个元素的位置
             * 
             * @return 返回下一个Cell
             */
            @Override
            public Cell next() {
                // 根据当前位置p创建一个细胞对象
                Cell cell = new Cell(p.getX(), p.getY(), getChess(p.getX(), p.getY()));
                // 根据给定的方向更新当前位置p
                p = new Point(p.getX() + dir.getP().getX(), p.getY() + dir.getP().getY());
                return cell;
            }
        };
    }

}

class Test {

    public static <A> Iterator<A> rotated(List<A> list, int offset) {
        return new Iterator<A>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public A next() {
                A result = list.get((index + offset) % list.size());
                index++;
                return result;
            }
        };
    }

    public static void main(String[] args) {
        CheckBoard checkerBoard = new CheckBoard(4, 4);
        checkerBoard.putChess(3, 3, ChessType.white);
        checkerBoard.putChess(2, 2, ChessType.black);

        System.out.println("===>通过内部结构遍历：");
        for (int y = 0; y < checkerBoard.getMax_y(); y++)
            for (int x = 0; x < checkerBoard.getMax_x(); x++)
                System.out.print(checkerBoard.getChess(x, y) + " ");

        // System.out.println("===>通过迭代器遍历：");
        // Iterator<Cell> cellIterator = checkerBoard.iterator();
        // while (cellIterator.hasNext())
        // System.out.println(cellIterator.next());

        // System.out.println("===>通过foreach遍历：");
        // for (Cell cell : checkerBoard) {
        // System.out.println(cell);
        // }

        System.out.println("===>通过迭代器遍历，从(2,2)开始，向下移动：");
        Iterator<Cell> i = checkerBoard.iterator(Direction.down, new Point(2, 2));
        while (i.hasNext())
            System.out.println(i.next());

    }

}