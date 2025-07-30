package pl.otros.logview.formatter.record;

import com.google.common.io.Resources;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class JavaRecordMessageFormatterTest {

    @org.testng.annotations.Test
    public void testFormattingNeeded() {
    }

    @org.testng.annotations.Test(dataProvider = "format")
    public void testFormat(String input, String output) throws IOException {
        //given
        var message = Resources.toString(Resources.getResource("testdata/" + input), StandardCharsets.UTF_8);
        var expected = Resources.toString(Resources.getResource("testdata/" + output), StandardCharsets.UTF_8);

        //when
        String format = new JavaRecordMessageFormatter().format(message);

        //then
        assertEquals(format, expected);
    }

    @DataProvider(name = "format")
    public Object[][] formatDataProvider() throws IOException, URISyntaxException {
        var dir  = Paths.get(ClassLoader.getSystemResource("testdata").toURI());
        try (Stream<Path> files = Files.list(dir)) {
            var inputs = files
                    .map(path -> path.getFileName().toString())
                    .peek(System.out::println)
                    .filter(name -> !name.endsWith("-out.txt"))
                    .map(name -> new Object[]{name,name.replace(".txt", "-out.txt")})
                    .toList();
            return inputs.toArray(new Object[][]{});
        }
    }
}