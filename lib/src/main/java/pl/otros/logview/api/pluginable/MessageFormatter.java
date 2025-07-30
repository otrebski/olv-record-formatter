package pl.otros.logview.api.pluginable;

public interface MessageFormatter {
    boolean formattingNeeded(String message);

    String format(String message);
}
