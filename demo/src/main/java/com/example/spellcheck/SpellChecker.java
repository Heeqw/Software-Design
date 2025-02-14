package com.example.spellcheck;
import org.jsoup.nodes.Element;
import java.util.List;

public interface SpellChecker {
    /**
     * 检查文本是否包含拼写错误
     */
    boolean hasError(String text);

    /**
     * 检查元素的文本内容是否包含拼写错误
     */
    boolean hasError(Element element);

    /**
     * 对文本进行拼写检查，返回所有错误
     */
    List<SpellError> check(String text);

    /**
     * 对元素的文本内容进行拼写检查，返回所有错误
     */
    List<SpellError> check(Element element);

    /**
     * 获取建议的更正
     */
    List<String> getSuggestions(String word);

}
