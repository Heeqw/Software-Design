package org.heeqw.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandParser {
    public record ParsedCommand(String name, String[] args) {}

    public static Optional<ParsedCommand> parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Optional.empty();
        }

        List<String> tokens = tokenize(input);
        if (tokens.isEmpty()) {
            return Optional.empty();
        }

        String commandName = tokens.get(0).toLowerCase();
        String[] args = tokens.subList(1, tokens.size()).toArray(new String[0]);

        return Optional.of(new ParsedCommand(commandName, args));
    }

    private static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;
        boolean escaped = false;

        for (char c : input.toCharArray()) {
            if (escaped) {
                currentToken.append(c);
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }

            if (!inQuotes && Character.isWhitespace(c)) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
            } else {
                currentToken.append(c);
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }
}
