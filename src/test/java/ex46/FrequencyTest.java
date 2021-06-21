package ex46;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FrequencyTest {

    @Test
    void getFrequencies() {
        String test = "abc";
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("abc", 1);
        assert(map.equals(Frequency.getFrequencies(test)));
    }
}