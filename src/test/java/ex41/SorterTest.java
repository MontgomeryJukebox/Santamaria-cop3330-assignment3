package ex41;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    @Test
    void sort() {
        Data d = new Data();
        d.names.add("hello");
        d.names.add("world");
        Sorter.sort(d);
        assertAll(() -> d.names.get(0).equals("hello"),
                () -> d.names.get(1).equals("world"));
    }
}