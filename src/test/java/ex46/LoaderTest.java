package ex46;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {

    @Test
    void loadFile() {
        String test = Loader.loadFile("exercise41_input.txt");
        assert(test.equals("Ling, Mai\n" +
                "Johnson, Jim\n" +
                "Zarnecki, Sabrina\n" +
                "Jones, Chris\n" +
                "Jones, Aaron\n" +
                "Swift, Geoffrey\n" +
                "Xiong, Fong"));
    }
}