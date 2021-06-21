package ex43;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MakeConfigTest {

    @Test
    void makeConfig() {
        MakeConfig config = new MakeConfig();
        config.makeConfig("test", "test2", true, true);
        assertAll(() -> config.siteName.equals("test"),
                () -> config.author.equals("test2"));
        assert(config.css);
        assert(config.js);
    }
}