package pl.otros.logview.formatter.record;

import pl.otros.logview.api.pluginable.MessageFormatter;

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
        //TODO format message

        return message;
    }
}
