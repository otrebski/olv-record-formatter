package pl.otros.logview.formatter.record;

import pl.otros.logview.api.pluginable.MessageFormatter;

import static pl.otros.logview.formatter.record.parser.Parser.parse;
import static pl.otros.logview.formatter.record.printer.PrettyPrinter.print;


public class JavaRecordMessageFormatter implements MessageFormatter {
    @Override
    public boolean formattingNeeded(String message) {
        return message != null
                && message.indexOf('[') >= 0
                && message.indexOf('=') >= 0
                && !message.contains("\n");
    }

    @Override
    public String format(String message) {
        if (message == null) {return null;}
//        if (!formattingNeeded(message)) {return message;}

        var ast = parse(message);
        return print(ast);

    }
}
