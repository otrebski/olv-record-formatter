package pl.otros.logview.formatter.record.parser;

import org.testng.annotations.Test;
import java.util.List;
import static org.testng.Assert.assertEquals;

public class SplitTopLevelTest {

    @Test
    public void shouldSplitOnTopLevelCommas() {
        //given
        String input = "a=1,b=Record[x=2,y=3],c=4";

        List<String> expected = List.of(
                "a=1",
                "b=Record[x=2,y=3]",
                "c=4"
        );
        //when
        List<String> actual = Parser.splitTopLevel(input);

        //then
        assertEquals(actual, expected);
    }
}
