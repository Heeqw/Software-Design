package designpattern.composite.livedemo;

import java.util.ArrayList;
public class CharGenMain {

    public static void main(String[] args) {
    }
}

interface CharGen{
    char nextChar();
}

class RangedCharGen implements CharGen{

    char from;
    char to;

    public RangedCharGen(char from, char to) {
        this.from = from;
        this.to = to;
    }

    public char nextChar() {
        return designpattern.composite.p1.CharGen.genRandomChar(from, to);
    }

    public static RangedCharGen UpperCase = new RangedCharGen('A', 'Z');
    public static RangedCharGen LowerCase = new RangedCharGen('a', 'z');
    public static RangedCharGen Digit = new RangedCharGen('0', '9');
}

class EnumCharGen implements CharGen{

    char[] chars;

    public EnumCharGen(char... chars) {
        this.chars = chars;
    }

    public char nextChar() {
        return designpattern.composite.p1.CharGen.getEnumeratedCharacter(chars);
    }
}


class CompositeCharGen implements CharGen{

    ArrayList<CharGen> rules;

    public CompositeCharGen(ArrayList<CharGen> rules) {
        this.rules = rules;
    }

    public char nextChar() {
        return rules.get((int) (Math.random() * rules.size())).nextChar();
    }
}

