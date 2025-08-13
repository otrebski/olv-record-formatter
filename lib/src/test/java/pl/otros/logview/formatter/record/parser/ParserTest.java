package pl.otros.logview.formatter.record.parser;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ParserTest {

    @Test
    public void parsesSimpleRecord() {
        //given
        String input = "SimpleRecord[name=name]";

        //when
        Node n = Parser.parse(input);

        //then
        assertTrue(n instanceof RecordNode);
        var r = (RecordNode) n;
        assertEquals(r.name(), "SimpleRecord");
        assertEquals(r.fields().size(), 1);
        assertEquals(r.fields().getFirst().key(), "name");
        assertTrue(r.fields().getFirst().value() instanceof ValueNode);
    }

    @Test
    public void parsesListInside() {
        //given
        String input = "List[Simple[a=1],Simple[b=2]]";

        //when
        Node n = Parser.parse(input);

        //then
        assertTrue(n instanceof ListNode);
        var l = (ListNode) n;
        assertEquals(l.elements().size(), 2);
        assertTrue(l.elements().getFirst() instanceof RecordNode);
        assertTrue(l.elements().get(1) instanceof RecordNode);
    }

    @Test
    public void mapsJvmArrayToNullValue() {
        //given
        String input = "[Lpl.otros.logview.Generator$SimpleRecord;@685f4c2e]";

        //when
        Node n = Parser.parse(input);

        //then
        assertTrue(n instanceof ValueNode);
        assertEquals(((ValueNode) n).raw(), "null");
    }

    @Test
    public void parsesMapLiteral() {
        //given
        String input = "{k1=v1,k2=v2}";

        //when
        Node n = Parser.parse(input);

        //then
        assertTrue(n instanceof MapNode);
        var m = (MapNode) n;

        assertEquals(m.entries().size(), 2);

        var e1 = m.entries().getFirst();
        assertEquals(e1.key(), "k1");
        assertTrue(e1.value() instanceof ValueNode);
        assertEquals(((ValueNode) e1.value()).raw(), "v1");

        var e2 = m.entries().get(1);
        assertEquals(e2.key(), "k2");
        assertTrue(e2.value() instanceof ValueNode);
        assertEquals(((ValueNode) e2.value()).raw(), "v2");
    }
}
