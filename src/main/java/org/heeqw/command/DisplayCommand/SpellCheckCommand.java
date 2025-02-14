package org.heeqw.command.DisplayCommand;

import org.heeqw.model.HTMLElement;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellCheckCommand extends DisplayCommand{
    private final JLanguageTool languageTool;

    public SpellCheckCommand(){
        this.languageTool = new JLanguageTool(new AmericanEnglish());
    }

    @Override
    public void execute(){
        if (!editor.isInitialized()){
            throw new IllegalStateException("Editor not initialized");
        }

        Map<String, List<String>> errors = checkSpelling(editor.getRootElement());
        printSpellCheckResults(errors);
    }

    private Map<String, List<String>> checkSpelling(HTMLElement element){
        Map<String, List<String>> errors = new HashMap<>();

        String text = element.getDirectText();
        if (!text.isEmpty()){
            try {
                System.out.println("Checking text: " + text); // 调试信息
                List<RuleMatch> matches = languageTool.check(text);
                if (!matches.isEmpty()) {
                    List<String> suggestions = new ArrayList<>();
                    for (RuleMatch match : matches) {
                        String errorFragment = text.substring(match.getFromPos(), match.getToPos());
                        String suggestionText = String.join(", ", match.getSuggestedReplacements());
                        System.out.println("Detected error: " + errorFragment + " with suggestions: " + suggestionText); // 调试信息
                        suggestions.add(String.format("'%s' (Suggestions: %s)", errorFragment, suggestionText));
                    }
                    errors.put(formatElementPath(element), suggestions);
                }
            }
            catch (Exception e) {
                System.err.println("Error checking text: " + e.getMessage());
            }
        }

        for (HTMLElement child: element.getChildren()){
            errors.putAll(checkSpelling(child));
        }
        return errors;
    }

    private String formatElementPath(HTMLElement element){
        List<String> path = new ArrayList<>();
        HTMLElement current = element;
        while (current != null){
            path.add(0,current.isSpecialTag() ?
                    current.getTagName() :
                    current.getTagName() + "#" + current.getId());
            current = current.getParent();
        }
        return String.join(">", path);
    }

    private void printSpellCheckResults(Map<String, List<String>> errors){
        if (errors.isEmpty()){
            System.out.println("No Spelling errors found.");
            return;
        }

        System.out.println("Spelling errors found:");
        errors.forEach((path,errorList) ->{
            System.out.println("\nIn element: " + path);
            errorList.forEach(error -> System.out.println("  - " + error));
        });
    }
}
