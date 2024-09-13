package designpattern.composite.p1;

public class CharGen {
    public static char genRandomChar(char from, char to){
        return (char) (Math.random() * (to - from + 1) + from);
    }

    static char getRandomLowerCaseLetter(){
        return genRandomChar('a', 'z');
    }

    static char getRandomUpperCaseLetter(){
        return genRandomChar('A', 'Z');
    }

    static char getRandomDigitCharacter(){
        return genRandomChar('0', '9');
    }

    static char getRandomUnicodeCharacter(){
        return genRandomChar('\u0000', '\uFFFF');
    }

    /**
     * 获取在指定范围内的随机字符
     * 该方法首先从给定的字符范围数组中随机选择一个范围，
     * 然后在这个范围内生成一个随机字符并返回
     * 
     * @param ranges 字符范围数组，每个内部数组包含两个字符，表示字符范围的起始和结束
     * @return 在选定范围内生成的随机字符
     */
    public static char getRandomRangedCharacter(char[][] ranges){
        int index = (int)(Math.random() * ranges.length);
        return genRandomChar(ranges[index][0], ranges[index][1]);
    }

    public static char getEnumeratedCharacter(char... chars){
        int index = (int)(Math.random() * chars.length);
        return chars[index];
    }

}
