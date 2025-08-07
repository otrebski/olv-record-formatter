package pl.otros.logview.formatter.record.parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Parser() {}

    public static List<String> splitTopLevel(String s) {
        List<String> parts = new ArrayList<>();
        int depth = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '[' -> depth++;
                case ']' -> depth--;
                case ',' -> {
                    if (depth == 0) {
                        parts.add(s.substring(start,i).trim());
                        start = i + 1;
                    }
                }
            }
        }
        parts.add(s.substring(start).trim());
        parts.removeIf(String::isEmpty);
        return parts;
    }
}
