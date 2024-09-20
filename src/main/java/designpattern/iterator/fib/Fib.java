package designpattern.iterator.fib;

import java.util.Iterator;

public class Fib {

    public static void main(String[] args) {
        Iterator<Integer> iterator = new FibIterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next > 100)
                break;
            System.out.println(next);
        }
    }

}

// 斐波那级数的迭代器
class FibIterator implements Iterator<Integer> {

    private int a = 0;
    private int b = 1;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        int result = a;
        a = b;
        b = a + result;
        return result;
    }
}
