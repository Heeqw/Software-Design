package com.example.spellcheck;

import com.example.exception.EditorException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultSpellChecker implements SpellChecker {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSpellChecker.class);
    private final Set<String> dictionary;
    private static final String DICTIONARY_FILE = "/dictionary/en_US.dict";
    private static final int MAX_SUGGESTIONS = 5;

    public DefaultSpellChecker() {
        this.dictionary = loadDictionary();
    }

    private Set<String> loadDictionary() {
        try (InputStream is = getClass().getResourceAsStream(DICTIONARY_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            
            return reader.lines()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
                    
        } catch (Exception e) {
            logger.error("Failed to load dictionary", e);
            throw new EditorException("Failed to load spelling dictionary", e);
        }
    }

    @Override
    public boolean hasError(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        
        return Arrays.stream(text.split("\\s+"))
                .map(this::normalizeWord)
                .anyMatch(word -> !dictionary.contains(word));
    }

    @Override
    public boolean hasError(Element element) {
        // 检查元素自身的文本
        if (hasError(element.ownText())) {
            return true;
        }

        // 检查所有文本节点
        return element.textNodes().stream()
                .map(TextNode::text)
                .anyMatch(this::hasError);
    }

    @Override
    public List<SpellError> check(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }

        List<SpellError> errors = new ArrayList<>();
        String[] words = text.split("\\s+");
        int position = 0;

        for (String word : words) {
            String normalizedWord = normalizeWord(word);
            if (!dictionary.contains(normalizedWord)) {
                errors.add(new SpellError(
                    word,
                    position,
                    position + word.length(),
                    getSuggestions(normalizedWord)
                ));
            }
            position += word.length() + 1; // +1 for space
        }

        return errors;
    }

    @Override
    public List<SpellError> check(Element element) {
        List<SpellError> errors = new ArrayList<>();
        
        // 检查元素自身的文本
        errors.addAll(check(element.ownText()));

        // 检查所有文本节点
        element.textNodes().forEach(node -> 
            errors.addAll(check(node.text()))
        );

        return errors;
    }

    @Override
    public List<String> getSuggestions(String word) {
        String normalizedWord = normalizeWord(word);
        if (dictionary.contains(normalizedWord)) {
            return Collections.emptyList();
        }

        return dictionary.stream()
                .filter(dictWord -> getLevenshteinDistance(normalizedWord, dictWord) <= 2)
                .sorted(Comparator.comparingInt(dictWord -> 
                    getLevenshteinDistance(normalizedWord, dictWord)))
                .limit(MAX_SUGGESTIONS)
                .collect(Collectors.toList());
    }

    private String normalizeWord(String word) {
        return word.toLowerCase()
                .replaceAll("[^a-z']", "")
                .trim();
    }

    private int getLevenshteinDistance(String word1, String word2) {
        int[] previousRow = new int[word2.length() + 1];
        int[] currentRow = new int[word2.length() + 1];

        for (int j = 0; j <= word2.length(); j++) {
            previousRow[j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            currentRow[0] = i;
            for (int j = 1; j <= word2.length(); j++) {
                int insertCost = currentRow[j - 1] + 1;
                int deleteCost = previousRow[j] + 1;
                int replaceCost = previousRow[j - 1] + 
                    (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);

                currentRow[j] = Math.min(Math.min(insertCost, deleteCost), replaceCost);
            }
            int[] temp = previousRow;
            previousRow = currentRow;
            currentRow = temp;
        }

        return previousRow[word2.length()];
    }
}
