package pl.otros.logview.formatter.record.parser;

public sealed interface Node
        permits RecordNode, ListNode, ValueNode, MapNode {}
