package ex42;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        Data data = new Data();
        Parser parser;
        try {
             parser = new Parser(data, "EXERCISE_43_TEST_INPUT.txt", "EXERCISE_43_TEST_OUTPUT.txt");
             parser.parse(data);
        } catch (IncorrectFormatException ife) {
            assert(false);
        }
        S3 s1 = data.names.get(0);
        S3 s2 = data.names.get(1);
        assertAll(() -> s1.first.equals("Hello"),
                () -> s1.second.equals("World"),
                () -> s1.third.equals("123"),
                () -> s2.first.equals("Goodbye"),
                () -> s2.second.equals("World"),
                () -> s2.second.equals("456"));
    }
}