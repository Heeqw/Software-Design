package designpattern.composite.livedemo;

import java.util.ArrayList;

public class CharGenMainADST {

    public static void main(String[] args) {

        ArrayList<CharGen> rules = new ArrayList<>();
        rules.add(RangedCharGen.UpperCase);
        rules.add(RangedCharGen.LowerCase);
        rules.add(RangedCharGen.Digit);
        rules.add(new EnumCharGen('_', '.'));

        CompositeCharGen gen = new CompositeCharGen(rules);
        System.out.println(gen.nextChar());

    }
}

interface CharGen {
    char nextChar();

    default String nextString(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(nextChar());
        }
        return sb.toString();
    }
}

class RangedCharGen implements CharGen {
    public static RangedCharGen UpperCase = new RangedCharGen('A', 'Z');
    public static RangedCharGen LowerCase = new RangedCharGen('a', 'z');
    public static RangedCharGen Digit = new RangedCharGen('0', '9');

    private char from;
    private char to;

    public RangedCharGen(char from, char to) {
        this.from = from;
    }

    public char nextChar() {
        return (char) (Math.random() * (to - from + 1) + from);
    }
}

class EnumCharGen implements CharGen {
    private char[] chars;

    public EnumCharGen(char... chars) {
        this.chars = chars;
    }

    public char nextChar() {
        return chars[(int) (Math.random() * chars.length)];
    }
}

class CompositeCharGen implements CharGen {
    private ArrayList<CharGen> rules;

    public CompositeCharGen(ArrayList<CharGen> rules) {
        this.rules = rules;
    }

    public char nextChar() {
        return rules.get((int) (Math.random() * rules.size())).nextChar();
    }
}