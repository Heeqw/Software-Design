package designpattern.iterator;

import java.util.Iterator;
import java.util.List;

public class Rotated {
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

        Iterator<Integer> r = rotated(
                List.of(1, 2, 3, 4, 5, 6, 7),
                2);
        while (r.hasNext())
            System.out.println(r.next());
    }
}
