package ex46;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HistogramTest {

    @Test
    void getHistogram() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("abc", 2);
        String test = Histogram.getHistogram(map);
        System.out.println(test);
        assert(test.equals("abc: **\n"));
    }
}