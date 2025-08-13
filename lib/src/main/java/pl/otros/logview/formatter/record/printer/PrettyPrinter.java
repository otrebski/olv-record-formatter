package pl.otros.logview.formatter.record.printer;

import pl.otros.logview.formatter.record.parser.*;

public final class PrettyPrinter {
    private PrettyPrinter() {}

    private static final String INDENT = "  ";
    private static final String NL = System.lineSeparator();

    public static String print(Node n) {
        return print(n, 0);
    }

    private static String indent(int d) {
        return INDENT.repeat(d);
    }

    private static String print(Node n, int d) {
        if (n instanceof RecordNode(String name, java.util.List<RecordField> fields)) {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append("[").append(NL);
            for (int i = 0; i < fields.size(); i++) {
                var f = fields.get(i);
                sb.append(indent(d + 1))
                        .append(f.key())
                        .append("=")
                        .append(print(f.value(), d + 1).trim());
                if (i < fields.size() - 1) sb.append(",").append(NL);
            }
            sb.append(NL).append(indent(d)).append("]");
            return sb.toString();
        }

        if (n instanceof ListNode(java.util.List<Node> elements)) {
            if (elements.isEmpty()) return "[]";
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(NL);
            for (int i = 0; i < elements.size(); i++) {
                sb.append(indent(d + 1)).append(print(elements.get(i), d + 1).trim());
                if (i < elements.size() - 1) sb.append(",").append(NL);
            }
            sb.append(NL).append(indent(d)).append("]");
            return sb.toString();
        }

        if (n instanceof MapNode(java.util.List<MapEntry> entries)) {
            if (entries.isEmpty()) return "{}";
            StringBuilder sb = new StringBuilder();
            sb.append("{").append(NL);
            for (int i = 0; i < entries.size(); i++) {
                var e = entries.get(i);
                sb.append(indent(d + 1)).append(e.key()).append("=").append(print(e.value(), d + 1).trim());
                if (i < entries.size() - 1) sb.append(",").append(NL);
            }
            sb.append(NL).append(indent(d)).append("}");
            return sb.toString();
        }

        if (n instanceof ValueNode(String raw)) {
            return raw;
        }

        return n.toString();
    }
}
