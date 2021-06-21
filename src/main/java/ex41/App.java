/*
 *  UCF COP3330 Summer 2021 Assignment 3 Solution
 *  Copyright 2021 David Santamaria
 */
package ex41;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// our data class
class Data {
    // wrapper around our data - names
    ArrayList<String> names;
    public Data() {
        names = new ArrayList<String>();
    }
}

// our reader class
class Reader {
    static Scanner in;

    public Reader(String filepath) {
        try {
            // just make a scanner around the file we have to read from
            in = new Scanner(new File(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // read from file
    public static void readFromFile(Data data) {
        // put all that we can read from the file into the data
        while (in.hasNextLine()) {
            data.names.add(in.nextLine());
        }
    }
}

// writer class - write to printstream
class Writer {
    static PrintStream print; // the printstream we will print to

    public Writer(String filepath) {
        try {
            // make a printstream
            print = new PrintStream(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // write to the console
    public static void writeToConsole(Data data) {
        writeToPrintStream(data, System.out);
    }
    // or
    // write to a file
    public static void writeToFile(Data data) {
        writeToPrintStream(data, print);
    }

    // inner generic function - write to any printstream
    private static void writeToPrintStream(Data data, PrintStream out) {
        // print the header
        out.printf("Total of %d names\n", data.names.size());
        out.println("------------------");

        // print out the data
        for (String str : data.names) {
            out.println(str);
        }
        // flush the printstream
        out.flush();
    }
}

// sorter class
class Sorter {
    public static void sort(Data data) {
        // sort the data
        Collections.sort(data.names);
    }
}

// driver code
public class App {
    public static void main(String[] args) {
        // Create our data, reader, writer, and sorter instances
        Data data = new Data();
        Reader reader = new Reader("exercise41_input.txt");
        Sorter sorter = new Sorter();
        Writer writer = new Writer("exercise41_output.txt");

        reader.readFromFile(data); // read from file
        sorter.sort(data); // sort the data we just read
        writer.writeToConsole(data); // write this sorted data to console
        writer.writeToFile(data); // also write this data to file
    }
}
