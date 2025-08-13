package pl.otros.logview.formatter.record.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class Parser {

    private Parser() {}

    private static final Pattern ARRAY_TO_STRING =
            Pattern.compile("^\\[(?:L[^;]+;|[BCDFIJSZ])@[0-9a-fA-F]+$");

    public static List<String> splitTopLevel(String s) {
        List<String> parts = new ArrayList<>();
        int depthSq = 0;
        int depthCurly = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '[' -> depthSq++;
                case ']' -> depthSq--;
                case '{' -> depthCurly++;
                case '}' -> depthCurly--;
                case ',' -> {
                    if (depthSq == 0 && depthCurly == 0) {
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

        if (text.startsWith("{") && text.endsWith("}")) {
            String inside = text.substring(1, text.length() -1);
            List<MapEntry> entries = splitTopLevel(inside).stream().map(p -> {
                int eq = p.indexOf('=');
                if (eq < 0) throw new IllegalArgumentException("invalid key: " + p);
                String key = p.substring(0, eq).trim();
                Node val = parse(p.substring(eq+1));
                return new MapEntry(key, val);
            })
                    .toList();
            return new MapNode(entries);
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
