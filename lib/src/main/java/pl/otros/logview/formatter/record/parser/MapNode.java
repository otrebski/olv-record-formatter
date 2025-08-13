package pl.otros.logview.formatter.record.parser;

import java.util.List;

public record MapNode(List<MapEntry> entries) implements Node {
}
