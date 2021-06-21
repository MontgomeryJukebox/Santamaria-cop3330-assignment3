package ex43;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileCreatorTest {

    @Test
    void createFile() {
        MakeConfig config = new MakeConfig("test", "test", true, true);
        FileCreator fc = new FileCreator("./fileCreatorTest/");

        try {
            Files.createDirectories(Paths.get("./website/fileCreatorTest/"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        fc.createFile("index.html");
        assert(Files.exists(Paths.get("./website/fileCreatorTest/index.html")));
    }
}