/*
 *  UCF COP3330 Summer 2021 Assignment 3 Solution
 *  Copyright 2021 David Santamaria
 */
package ex42;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

class IncorrectFormatException extends Exception {
    public IncorrectFormatException(String errorMessage) {
        super(errorMessage);
    }
}

// our token enum will differentiate between
enum TokenType {
    UNKNOWN, // error state, should not be reached with sanitized data
    FNAME, // a person's first name
    LNAME, // their last name
    SALARY, // their salary
    EOF // and the end of the file (parser will need this in order to know when to write to file)
}

// our token class will have
class Token {
    TokenType type; // a token enum to tell what kind of token it is
    String lexeme; // a lexeme representing the string that was matched by the lexer
    // a constructor so that it's not super annoying to make instances of a token object
    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public static String typeToString(TokenType t) {
        switch (t) {
            case FNAME:
                return "First Name";
            case LNAME:
                return "Last Name";
            case SALARY:
                return "Salary";
            case EOF:
                return "EOF";
            default:
            case UNKNOWN:
                return "Unknown";
        }
    }

    public String toString() {
        return "{<" + typeToString(type) + ">" + " `" + lexeme + "`}";
    }
}

// our data class
class Data {
    // wrapper around our data - names
    ArrayList<S3> names;
    // there's nothing really here to test
    public Data() {
        names = new ArrayList<S3>();
    }
}

// our ordered triple S3 class
class S3 {
    String first, second, third;
    public S3(String a, String b, String c) {
        first = a;
        second = b;
        third = c;
    }
}

// our lexer class will have
class Lexer {
    // a scanner to read in stuff from the file
    static Scanner fin;
    // a buffer scanner to read the current line
    static Scanner in;

    // our current character and line in the file
    int line, ch;

    // a constructor that takes in a filepath string for its scanner
    public Lexer(String filepath) {
        try {
            fin = new Scanner(new File(filepath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }
    }

    // a nextToken() method to return the next token read from the file
    public Token nextToken() {
        // we'll have to check first if we have a token
        if (in != null && !in.hasNext() && !hasNextToken()) {
            return new Token(TokenType.EOF, "");
        }
        if (in == null || !in.hasNext()) {
            String line = fin.nextLine();
            line = line.replaceAll(",", " ,");
//            System.out.println("\t\tDEBUG: setting in to `" + line + "`");
            in = new Scanner(line);
        }

        Pattern salary = Pattern.compile(",[0-9]*");
        Pattern lname = Pattern.compile(",[a-zA-Z]*");
        Pattern fname = Pattern.compile("[a-zA-Z]*");
        // then check against some regexes to find out where we're at in the current line
        if (in.hasNext(salary)) { /* it's not cheating if we're using regexes right? they're supposed to
        recognize regular languages, right? o.0 damnit, i'm not computationally independent yet! */
            // if we reach a newline, we must set line++ and ch to 0
            line++;
            ch = 0;
            return new Token(TokenType.SALARY, in.next(salary).replaceAll(",", ""));
        } else if (in.hasNext(lname)) {
            // otherwise we'll just increment ch by amount of characters read
            String lexeme = in.next(lname);
            ch += lexeme.length();
            return new Token(TokenType.LNAME, lexeme.replaceAll(",", ""));
        } else if (in.hasNext(fname)) {
            String lexeme = in.next(fname);
            ch += lexeme.length();
            return new Token(TokenType.FNAME, lexeme.replaceAll(",", ""));
        } else {
            return new Token(TokenType.UNKNOWN, in.next());
        }
    }

    // a hasNextToken() method to return whether or not the file has a token to return
    public boolean hasNextToken() {
        return fin.hasNext();
    }
}

// our parser class will have:
class Parser {
    // a lexer to gobble tokens from the file
    Lexer lex;
    // a writer to write the data to console and to file
    Writer writer;
    // a reference to the data to be written (formatted :) )
    Data data;

    String buffer;

    // a constructor to initialize that lexer
    public Parser(Data data, String inputFilePath, String outputFilePath) {
        lex = new Lexer(inputFilePath);
        writer = new Writer(outputFilePath);
    }

    // a parse() method that will parse the file, populate our data, and tell the writer to print to output
    public void parse(Data data) throws IncorrectFormatException {
        boolean eof = false;
        // while we're still running,
        while (!eof) {
            // grab the first three tokens
            Token a, b, c;
            a = lex.nextToken();
//            System.out.println("\t\tDEBUG: A = " + a);
            // if the first is EOF then we know we're done
            if (a.type == TokenType.EOF) {
                // then just print the data to console and to file
                Writer.writeToConsole(data);
                Writer.writeToFile(data);
                eof = true;
                continue;
            }
            // else, check to make sure it's a first name that was returned
            if (a.type != TokenType.EOF && a.type != TokenType.FNAME) {
                throw new IncorrectFormatException("Expected first name or EOF at line: " + lex.line + " and character: " +
                        lex.ch + ", but found " + Token.typeToString(a.type) + ": " + a.lexeme);
            }
            // grab the next token
            b = lex.nextToken();
//            System.out.println("\t\tDEBUG: B = " + b);
            // which should be a last name
            if (b.type != TokenType.LNAME) {
                throw new IncorrectFormatException("Expected last name at line: " + lex.line + " and character: " + lex.ch
                        + ", but found " + Token.typeToString(b.type) + ": " + b.lexeme);
            }
            // and then grab the last
            c = lex.nextToken();
//            System.out.println("\t\tDEBUG: C = " + c);
            // which should be a salary
            if (c.type != TokenType.SALARY) {
                throw new IncorrectFormatException("Expected Salary at line: " + lex.line + " and character: " + lex.ch
                        + ", but found: " + Token.typeToString(c.type) + ": " + c.lexeme);
            }

            // if the line exists, then we add the three tokens as an entry to our data
            S3 entry = new S3(a.lexeme, b.lexeme, c.lexeme);
            data.names.add(entry);
        }
    }
}

// our writer class will
class Writer {
    // wrap around a printstream object
    static PrintStream print;

    // have a constructor that will take the output file's path
    public Writer(String filepath) {
        try {
            // which will make the printstream
            print = new PrintStream(new File(filepath));
        } catch (IOException e) {
            // and catch any io errors
            e.printStackTrace();
        }
    }

    /*
    These are all print methods, so
    I won't include any tests for them
    literally all the statements besides
    print = new PrintStream(new File(filepath));
    and
    e.printStackTrace();
    are a print, variable definition, or a flush. There is
    no reason to test these.
     */

    // write to the console,
    public static void writeToConsole(Data data) {
        writeToPrintStream(data, System.out);
    }
    // or
    // write to a file
    public static void writeToFile(Data data) {
        writeToPrintStream(data, print);
    }

    // have an inner generic function - write to any printstream (this will let us combine write to console and file int
    // o one inner method with multiple interfaces)
    private static void writeToPrintStream(Data data, PrintStream out) {
        // print the header
        out.printf("%-8s %-8s %-8s \n", "First", "Last", "Salary");
        out.println("------------------------");

        // print out the data
        for (S3 s3: data.names) {
            out.printf("%-8s %-8s %-8s\n", s3.first, s3.second, s3.third);
        }

        // flush the printstream
        out.flush();
    }
}

// driver code will have
public class App {
    // obligatory /* This is our main method */ comment
    // we will also throw an exception from main if the input is incorrectly formatted
    public static void main(String[] args) throws IncorrectFormatException, FileNotFoundException {
        // Create our data and parser
        Data data = new Data();
        Parser parser = new Parser(data, "exercise42_input.txt", "exercise42_output.txt");
        parser.parse(data); // and parse :)
    } // an obligatory /* end of main method */ comment
}


















// and an obligatory /* End of file */ comment