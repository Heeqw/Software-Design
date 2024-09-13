package designpattern.composite.livedemo;

import java.util.ArrayList;
public class CharGenMain {
    

    public static void main(String[] args) {
        System.out.println(RangedCharGen.LowerCase.nextChar());
        System.out.println(new EnumCharGen('$','_').nextChar());

        ArrayList<CharGen> rules = new ArrayList<>();
        rules.add(RangedCharGen.LowerCase);
        rules.add(RangedCharGen.UpperCase);
        rules.add(RangedCharGen.Digit);

        CharGen gen = new CompositeCharGen(rules);

        System.out.println(gen.nextChar());
    }
}

interface CharGen{
    char nextChar();

}

// 生成指定范围的字符
class RangedCharGen implements CharGen{

    public static CharGen UpperCase = new RangedCharGen('A', 'Z');
    public static CharGen LowerCase = new RangedCharGen('a', 'z');
    public static CharGen Digit = new RangedCharGen('0', '9');

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

}

// 生成指定字符数组中的字符
class EnumCharGen implements CharGen{

    char[] chars;

    public EnumCharGen(char... chars)
    {
        this.chars = chars;
    }

    public char nextChar()
    {
        return designpattern.composite.p1.CharGen.getEnumeratedCharacter(chars);
    }

}

// 组合多个规则

class CompositeCharGen implements CharGen{

    ArrayList<CharGen> rules;

    public CompositeCharGen(ArrayList<CharGen> rules)
    {
        this.rules = rules;
    }

    public char nextChar()
    {
        return rules.get((int) (Math.random() * rules.size())).nextChar();
    }

}

