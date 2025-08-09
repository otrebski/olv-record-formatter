package pl.otros.logview.formatter.record.parser;

import java.util.List;

public record RecordNode(String name, List<RecordField> fields) implements Node {}
