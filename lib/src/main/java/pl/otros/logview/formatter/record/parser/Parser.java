package pl.otros.logview.formatter.record.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class Parser {

    private Parser() {}

    private static final Pattern ARRAY_TO_STRING =
            Pattern.compile("^\\[L[^;]+;@[0-9a-fA-F]+]$");

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

    public static Node parse(String text) {
        text = text.trim();

        if (ARRAY_TO_STRING.matcher(text).matches()) {
            return new ValueNode("null");
        }

        int open = text.indexOf('[');
        if (open >= 0 && text.endsWith("]")) {
            String head = text.substring(0, open).trim();
            String inside = text.substring(open+1, text.length() - 1);
            List<String> parts = splitTopLevel(inside);

            if (head.equals("List") || head.isBlank()) {
                return new ListNode(parts.stream().map(Parser::parse).toList());
            }

            List<RecordField> fields = parts.stream().map(p -> {
                int eq = p.indexOf('=');
                String key = p.substring(0, eq).trim();
                Node val = parse(p.substring(eq + 1));
                return new RecordField(key,val);
            }).toList();

            return new RecordNode(head,fields);
        }

        return new ValueNode(text);
    }
}
