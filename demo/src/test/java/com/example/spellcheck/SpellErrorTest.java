package com.example.spellcheck;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SpellErrorTest {
    
    @Test
    void testSpellError() {
        List<String> suggestions = Arrays.asList("correct", "collect");
        SpellError error = new SpellError("incorrekt", 10, 19, suggestions);
        
        assertEquals("incorrekt", error.getWord());
        assertEquals(10, error.getStartPosition());
        assertEquals(19, error.getEndPosition());
        assertEquals(suggestions, error.getSuggestions());
    }
    
    @Test
    void testToString() {
        List<String> suggestions = Arrays.asList("hello", "hallo");
        SpellError error = new SpellError("helo", 0, 4, suggestions);
        
        String expected = "'helo' at positions 0-4. Suggestions: hello, hallo";
        assertEquals(expected, error.toString());
    }
} 