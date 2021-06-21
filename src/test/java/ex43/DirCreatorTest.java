package ex43;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DirCreatorTest {

    @Test
    void createDirs() {
        MakeConfig config = new MakeConfig("hello", "world", true, true);
        DirCreator dc = new DirCreator("./dirtest/");
        dc.createDirs(config);
        assertAll(() -> Files.exists(Paths.get("./dirtest/")),
                () -> Files.exists(Paths.get("./dirtest/js")),
                () -> Files.exists(Paths.get("./dirtest/css")));
    }
}