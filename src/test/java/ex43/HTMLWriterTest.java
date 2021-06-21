package ex43;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HTMLWriterTest {

    @Test
    void writeConfigToHTML() {
        MakeConfig config = new MakeConfig("site", "author", true, true);
        HTMLWriter writer = new HTMLWriter("htmlWriterTest/");
        try {
            Files.createDirectories(Paths.get("./website/htmlWriterTest/"));
            Files.createFile(Paths.get("./website/htmlWriterTest/index.html"));
            writer.writeConfigToHTML(config, "index.html");
            assert(Files.exists(Paths.get("./website/htmlWriterTest/index.html")));
            Scanner in = new Scanner(new File("./website/htmlWriterTest/index.html"));
            StringBuilder fileContents = new StringBuilder("");
            while (in.hasNext()) {
                fileContents.append(in.nextLine());
            }
            assert(fileContents.toString().contains("content=\"author\""));
            assert(fileContents.toString().contains("<title> site </title>"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}