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

    @Test
    public void printsMapMultiline() {
        //given
        Node n = new MapNode(java.util.List.of(
                new MapEntry("k1", new ValueNode("v1")),
                new MapEntry("k2", new ValueNode("v2"))
        ));
        String nl = System.lineSeparator();

        String expected = "{" + nl +
                          "  k1=v1," + nl +
                          "  k2=v2" + nl +
                           "}";

        //when
        String out = PrettyPrinter.print(n);

        //then
        assertEquals(out, expected);
    }
}
