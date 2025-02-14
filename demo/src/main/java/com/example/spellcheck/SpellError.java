package com.example.spellcheck;
import java.util.List;

public class SpellError {
    private final String word;
    private final int startPosition;
    private final int endPosition;
    private final List<String> suggestions;

    public SpellError(String word, int startPosition, int endPosition, List<String> suggestions) {
        this.word = word;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.suggestions = suggestions;
    }

    public String getWord() {
        return word;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    @Override
    public String toString() {
        return String.format("'%s' at positions %d-%d. Suggestions: %s",
            word, startPosition, endPosition, 
            String.join(", ", suggestions));
    }
}
