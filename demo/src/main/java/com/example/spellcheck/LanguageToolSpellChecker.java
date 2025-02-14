package com.example.spellcheck;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageToolSpellChecker implements SpellChecker {
    private static final Logger logger = LoggerFactory.getLogger(LanguageToolSpellChecker.class);
    private final JLanguageTool langTool;

    public LanguageToolSpellChecker() {
        this.langTool = new JLanguageTool(new AmericanEnglish());
    }

    @Override
    public boolean hasError(String text) {
        try {
            return !langTool.check(text).isEmpty();
        } catch (IOException e) {
            logger.error("拼写检查失败", e);
            return false;
        }
    }

    @Override
    public boolean hasError(Element element) {
        if (hasError(element.ownText())) {
            return true;
        }
        return element.textNodes().stream()
                .map(TextNode::text)
                .anyMatch(this::hasError);
    }

    @Override
    public List<SpellError> check(String text) {
        try {
            List<RuleMatch> matches = langTool.check(text);
            return matches.stream()
                    .map(match -> new SpellError(
                            text.substring(match.getFromPos(), match.getToPos()),
                            match.getFromPos(),
                            match.getToPos(),
                            match.getSuggestedReplacements()
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("拼写检查失败", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<SpellError> check(Element element) {
        List<SpellError> errors = new ArrayList<>();
        errors.addAll(check(element.ownText()));
        element.textNodes().forEach(node -> errors.addAll(check(node.text())));
        return errors;
    }

    @Override
    public List<String> getSuggestions(String word) {
        try {
            List<RuleMatch> matches = langTool.check(word);
            if (!matches.isEmpty()) {
                return matches.get(0).getSuggestedReplacements();
            }
        } catch (IOException e) {
            logger.error("获取建议失败", e);
        }
        return Collections.emptyList();
    }
}
