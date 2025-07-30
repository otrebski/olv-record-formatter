package pl.otros.logview.formatter.record;

import com.google.common.io.Resources;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.testng.Assert.*;

public class JavaRecordMessageFormatterTest {

    @org.testng.annotations.Test
    public void testFormattingNeeded() {
    }

    @org.testng.annotations.Test(dataProvider = "format")
    public void testFormat(String name, String input, String output) throws IOException {
        //given
        var message = Resources.toString(Resources.getResource("testdata/" + input), StandardCharsets.UTF_8);
        var expected = Resources.toString(Resources.getResource("testdata/" + output), StandardCharsets.UTF_8);

        //when
        String format = new JavaRecordMessageFormatter().format(message);

        //then
        assertEquals(format, expected);
    }

    @DataProvider(name = "format")
    public Object[][] formatDataProvider() {
        return new Object[][]{
                {"Name", "example-1-input.txt", "example-1-output.txt"},
        };
    }
}