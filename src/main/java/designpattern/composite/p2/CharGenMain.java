package designpattern.composite.p2;

import java.util.ArrayList;

public class CharGenMain{

    public static void main(String[] args) {
        ArrayList<CharGen> rules = new ArrayList<>();
        rules.add(RangedCharGen.UpperCase);
        rules.add(RangedCharGen.LowerCase);
        rules.add(RangedCharGen.Digit);

        CharGen gen = new CompositeCharGen(rules);

        System.out.println(gen.next(10));
    }

}

interface CharGen{
    char nextChar();

    default String next(int length){

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<length; i++){
            sb.append(nextChar());
        }
        return sb.toString();

    }
}

class RangedCharGen implements CharGen{

    char from;
    char to;

    public RangedCharGen(char from, char to)
    {
        this.from = from;
        this.to = to;
    }

    public char nextChar()
    {
        return designpattern.composite.p1.CharGen.genRandomChar(from, to);
    }

    public static RangedCharGen UpperCase = new RangedCharGen('A', 'Z');
    public static RangedCharGen LowerCase = new RangedCharGen('a', 'z');
    public static RangedCharGen Digit = new RangedCharGen('0', '9');

}

class EnumCharGen implements CharGen{

    private char[] chars;

    public EnumCharGen(char[] chars){
        this.chars = chars;
    }

    public char nextChar(){
        return chars[designpattern.composite.p1.CharGen.getEnumeratedCharacter(chars)];
    }

}

class CompositeCharGen implements CharGen{
    private ArrayList<CharGen> rules = new ArrayList<>();

    public CompositeCharGen(ArrayList<CharGen> rules){
        this.rules = rules;
    }

    public char nextChar(){
        return rules.get( (int)(Math.random() * rules.size()) ).nextChar();
    }

}