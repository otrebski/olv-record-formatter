package pl.otros.logview;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Generator {
    record SimpleRecord(String name) {
    }

    record SimpleRecord2(String name, boolean b, int number, float number2) {
    }

    record ComplexRecord(
            SimpleRecord simpleRecord,
            SimpleRecord2 SimpleRecord2,
            SimpleRecord[] simpleRecords
    ) {
    }

    record WithList(List<String> list) {
    }

    record WithMap(Map<String, String> map) {
    }

    public static void main(String[] args) {
        SimpleRecord simpleRecord = new SimpleRecord("name");
        System.out.println(simpleRecord);
        SimpleRecord2 simpleRecord2 = new SimpleRecord2("name", false, 3, -3f);
        System.out.println(simpleRecord2);
        System.out.println(new ComplexRecord(
                simpleRecord,
                simpleRecord2,
                null
        ));
        System.out.println(new ComplexRecord(
                simpleRecord,
                simpleRecord2,
                new SimpleRecord[0]
        ));

        System.out.println(new WithList(Collections.emptyList()));
        System.out.println(new WithList(Arrays.asList("s1", "s2", "s3", "s4")));
        Map<String, String> map = Map.of(
                "key1", "value1",
                "key2", "value2",
                "key3", "value3",
                "key4", "value4"
        );
        System.out.println(new WithMap(map));
    }
}
