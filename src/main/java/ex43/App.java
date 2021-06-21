/*
 *  UCF COP3330 Summer 2021 Assignment 3 Solution
 *  Copyright 2021 David Santamaria
 */
package ex43;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// we will need some sort of config handler
class MakeConfig {
    // that will store all the info that was prompted from the user
    String siteName;
    String author;
    boolean js, css;

    public MakeConfig() {
        // do nothing
    }

    // for good code style, we can give it some constructors and populate-methods
    public MakeConfig(String siteName, String author, boolean js, boolean css) {
        this.siteName = siteName;
        this.author = author;
        this.js = js;
        this.css = css;
    }

    public void makeConfig(String siteName, String author, boolean js, boolean css) {
        this.siteName = siteName;
        this.author = author;
        this.js = js;
        this.css = css;
    }
}

// Since everything that creates/edits files/directories in the program needs to have a path,
// we can make use of inheritance to make it just a bit cleaner
class FileWorker {
    // should default work from the working directory (without having to keep this in mind, and easy overriding)
    String rootDirectoryPath = "./website/";
    public FileWorker() {
        // do nothing
    }
    // for cleanliness we can give it a constructor that takes the relative path so we don't have to manually set() it
    public FileWorker(String rootPath) {
        rootDirectoryPath += rootPath;
    }
}

// we'll need something to create files
class FileCreator extends FileWorker {
    public FileCreator() {
        // do nothing
    }
    public FileCreator(String filepath) {
        super(filepath);
    }

    // and a file generating method
    public void createFile(String filename) {
        // that will take in a filename/relative path name, and
        Path filepath = Paths.get(rootDirectoryPath + "/" + filename);
        try {
            // create the file
            Files.createFile(filepath);
            // (while also printing out "Created this file over here"
            System.out.println("Created " + filepath);
        } catch (IOException e) {
            e.printStackTrace();
        } // we'll have to throw it around a try-catch too
    }
}

// the directory creator should mostly be the same
class DirCreator extends FileWorker {
    public DirCreator() {
        // do nothing
    }
    public DirCreator(String dirpath) {
        super(dirpath);
    }

    // obviously instead of files we'll just make directories
    public void createDirs(MakeConfig config) {
        // same process though, we'll determine the absolute path from our FileWorker's rootdir path
        Path websitePath =  Paths.get(rootDirectoryPath);
        Path jsPath = Paths.get(rootDirectoryPath + "/js/");
        Path cssPath = Paths.get(rootDirectoryPath + "/css/");
        try {
            // the we'll try making the website directory
            Files.createDirectories(websitePath);
            System.out.println("Created " + websitePath);
            // if the config says we need a js folder,
            if (config.js) {
                // then make one
                Files.createDirectories(jsPath);
                System.out.println("Created " + jsPath);
            }
            // and the same for the css folder
            if (config.css) {
                Files.createDirectories(cssPath);
                System.out.println("Created " + cssPath);
            }
            // we'll definitely have to that into a try catch to catch Exceptions
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

// to actually write the html we'll need some HTMLWriter like class
class HTMLWriter extends FileWorker {
    public HTMLWriter() {
        // do nothing
    }

    // it will hold the template html file (although ideally we would probably put this in a file)
    static String data = "<!DOCTYPE HTML>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <meta name=\"author\" content=\"AUTHOR_NAME\">\n" +
            "    </head>\n" +
            "<body>\n" +
            "    <title> TITLE </title>\n" +
            "    Hello, World!\n" +
            "</body>\n" +
            "</html>";

    public HTMLWriter(String rootDirectoryPath) {
        super(rootDirectoryPath);
    }

    // and then when we try to write,
    public void writeConfigToHTML(MakeConfig config, String filepath) {
        try {
            // we just make a printwriter with the file's path (index.html, that is)
            PrintWriter pw = new PrintWriter(new File(rootDirectoryPath + "/" + filepath));
            // fill out the template data as is determined by the config,
            data = data.replace("AUTHOR_NAME", config.author);
            data = data.replace("TITLE", config.siteName);
            // and then write it
            pw.println(data);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// for our driver code
public class App {
    static Scanner in; // we'll like to have a scanner to read input
    // maybe a "[Y/n]" determiner so we don't duplicate the "press y for yes and n for no" code
    public static boolean getYesNo() {
        return Character.toLowerCase(in.nextLine().charAt(0)) == 'y';
    }

    public static void main(String[] args) {
        in = new Scanner(System.in);
        MakeConfig config = new MakeConfig();

        // then we'll just grab the user's input
        System.out.print("Site name: ");
        String sName = in.nextLine();
        System.out.print("Author: ");
        String author = in.nextLine();
        System.out.print("Do you want a folder for JavaScript? ");
        boolean js = getYesNo();
        System.out.print("Do you want a folder for CSS? ");
        boolean css = getYesNo();

        // create the instances we need
        FileCreator fcreate = new FileCreator(sName);
        DirCreator dcreate = new DirCreator(sName);
        HTMLWriter writer = new HTMLWriter(sName);

        // and actually craeate the directories/files
        config.makeConfig(sName, author, js, css);
        dcreate.createDirs(config);
        fcreate.createFile("index.html");
        writer.writeConfigToHTML(config, "index.html");
        // oh, and an in.close() for good measure :)
        in.close();
    }
}
