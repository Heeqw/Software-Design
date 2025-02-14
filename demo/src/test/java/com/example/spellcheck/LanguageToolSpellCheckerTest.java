package com.example.spellcheck;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LanguageToolSpellCheckerTest {
    
    private LanguageToolSpellChecker spellChecker;
    
    @BeforeEach
    void setUp() {
        spellChecker = new LanguageToolSpellChecker();
    }
    
    @Test
    void testHasErrorWithCorrectText() {
        assertFalse(spellChecker.hasError("This is a correct sentence."));
    }
    
    @Test
    void testHasErrorWithIncorrectText() {
        assertTrue(spellChecker.hasError("Thiss iz a incorrect sentense."));
    }
    
    @Test
    void testHasErrorWithElement() {
        Element element = mock(Element.class);
        when(element.ownText()).thenReturn("Thiss iz incorrect.");
        
        assertTrue(spellChecker.hasError(element));
    }
    
    @Test
    void testCheckText() {
        List<SpellError> errors = spellChecker.check("Thiss iz incorrect.");
        
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream()
            .anyMatch(error -> error.getWord().equals("Thiss")));
    }
    
    @Test
    void testCheckElement() {
        Element element = mock(Element.class);
        TextNode textNode = new TextNode("Thiss iz incorrect.");
        when(element.ownText()).thenReturn("");
        when(element.textNodes()).thenReturn(List.of(textNode));
        
        List<SpellError> errors = spellChecker.check(element);
        
        assertFalse(errors.isEmpty());
    }
    
    @Test
    void testGetSuggestions() {
        List<String> suggestions = spellChecker.getSuggestions("Thiss");
        
        assertFalse(suggestions.isEmpty());
        assertTrue(suggestions.contains("This"));
    }
    
    @Test
    void testGetSuggestionsForCorrectWord() {
        List<String> suggestions = spellChecker.getSuggestions("This");
        assertTrue(suggestions.isEmpty());
    }
    
    @Test
    void testGrammarErrors() {
        List<SpellError> errors = spellChecker.check("I has a pen.");
        assertFalse(errors.isEmpty());
    }
    
    @Test
    void testMultipleErrors() {
        List<SpellError> errors = spellChecker.check("Thiss iz verry incorrekt.");
        assertTrue(errors.size() > 1);
    }
    
    @Test
    void testEmptyText() {
        assertFalse(spellChecker.hasError(""));
        assertTrue(spellChecker.check("").isEmpty());
    }
} 