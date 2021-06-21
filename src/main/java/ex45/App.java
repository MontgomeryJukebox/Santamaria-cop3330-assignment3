/*
 *  UCF COP3330 Summer 2021 Assignment 3 Solution
 *  Copyright 2021 David Santamaria
 */
package ex45;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class FileLoader {
    public static String loadFile(String filename) {
        String ret = "";
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                ret += in.nextLine() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return ret;
    }
}

class FileWriter {
    public static void writeFile(String contents, String filename) {
        try {
            PrintWriter pw = new PrintWriter(new File(filename));
            pw.println(contents);
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

class Sed {
    public static String replace(String str, String substr, String value) {
        return str.replaceAll(substr, value);
    }
}

public class App {
    public static void main(String[] args) {
        String str = FileLoader.loadFile("exercise45_input.txt");
        str = Sed.replace(str, "utilize", "use");
        FileWriter.writeFile(str, "exercise45_output.txt");
    }
}
