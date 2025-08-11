package pl.otros.logview.formatter.record.printer;

import org.testng.annotations.Test;
import pl.otros.logview.formatter.record.parser.*;

import static org.testng.Assert.assertEquals;

public class PrettyPrinterTest {
    @Test
    public void printsSimpleRecord() {
        var ast = new RecordNode("SimpleRecord",
                java.util.List.of(new RecordField("name", new ValueNode("name"))));
        String nl = System.lineSeparator();
        String expected = "SimpleRecord[" + nl +
                "  name=name" + nl +
                "]";
        assertEquals(PrettyPrinter.print(ast), expected);
    }
}
