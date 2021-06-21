package ex41;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class WriterTest {

    @Test
    void writeToFile() {
        Data d = new Data();
        d.names.add("hello, world!");
        Writer w = new Writer("TEST_FILE");
        Writer.writeToFile(d);
        Scanner in = null;
        try {
            in = new Scanner(new File("TEST_FILE"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String ret = "";
        while (in.hasNextLine()) {
            ret += in.nextLine() + "\n";
        }
        assert(ret.equals("Total of 1 names\n" +
                "------------------\n" +
                "hello, world!\n"));
    }
}