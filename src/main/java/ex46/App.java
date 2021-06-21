/*
 *  UCF COP3330 Summer 2021 Assignment 3 Solution
 *  Copyright 2021 David Santamaria
 */

package ex46;

import java.io.File;
import java.io.IOException;
import java.util.*;

// we'll need something to display histograms
class Histogram {
    // it'll take in some map of frequencies for each word
    static String getHistogram(HashMap<String, Integer> frequencies) {
        // and it should return a string-ified histogram
        String ret = "";
        // since we have to print in order of highest frequency,
        Set<String> ks = frequencies.keySet();
        // and we're using a hashmap we should grab the keyset of the map
        Object[] keyset = ks.toArray();
        // and sort it,
        Arrays.sort(keyset);
        // and then for each word
        for (Object s : keyset) {
            // we just print it out
            ret += s + ": ";
            // along with the frequency in which it occurs in the text
            for (int i = 0; i < frequencies.get(s); i++) {
                ret += "*";
            }
            ret += "\n";
        }
        return ret;
    }
}

// to actually _get_ those frequencies, we'll need another class
class Frequency {
    // we can take in the text that we want to generate a histogram for
    public static HashMap<String, Integer> getFrequencies(String text) {
        HashMap<String, Integer> ret = new HashMap<String, Integer>();
        // we'll split up that text by the words
        String[] words = text.split(" ");
        // and then just iterate through and keep track of the number of times that word pops in
        for (String s : words) {
            ret.put(s, ret.getOrDefault(s, 0) + 1);
        }
        return ret;
    }
}

// ideally we'll also want some class to handle loading in the file
class Loader {
    // it'll take in the filename
    public static String loadFile(String filename) {
        try {
            // and then just read everything from it
            String ret = "";
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()) {
                ret += in.next() + " ";
            }
            // and return it
            return ret;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }
        return "";
    }
}

// for our driver code
public class App {
    public static void main(String[] args) {
        // we just need to grab the text as a string
        String text = Loader.loadFile("exercise46_input.txt");
        // and then display the histogram generated from that string
        System.out.println(Histogram.getHistogram(Frequency.getFrequencies(text)));
    }
}
