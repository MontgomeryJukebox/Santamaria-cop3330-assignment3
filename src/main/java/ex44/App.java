/*
 *  UCF COP3330 Summer 2021 Assignment 3 Solution
 *  Copyright 2021 David Santamaria
 */
package ex44;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/* The product class needed a wrapper to work with fromJson() */
class Products {
    Product[] products; // literally just a wrapper around a product array

    public String toString() {
        String ret = "";
        for (int i = 0; i < products.length; i++) {
            ret += products[i].toString() + "\n";
        }
        return ret;
    }
}

// our product class
class Product {
    // will have a name
    String name;
    // a price
    double price;
    // and a quantity
    int quantity;

    // we'll need a constructor to work with fromJson()
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // and a toString() for printing the results
    public String toString() {
        // just return each field with a qualifier
        return "Name: " + name + "\nPrice: " + price + "\nQuantity: " + quantity + "\n";
    }
}

// we'll need some class to wrap around fromJson so that we can load products arbitrarily
class Loader {
    // it'll need some load(json file) method
    public static Products load(String filename) {
        // it'll create a gson object
        Gson gson = new Gson();
        Products products = null;
        try {
            // read from the file,
            Reader reader = Files.newBufferedReader(Paths.get("exercise44_input.json"));
            // and load up some return variable
            products = gson.fromJson(reader, Products.class);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // when everything's done we'll just return it
        return products;
    }
}

// our database class
class Database {
    // will have some inventory of products
    Products inventory;

    // it'll abstract the whole containing an array of products and loading it from the file,
    // you'll just need to give it the filename
    public Database(String filename) {
        inventory = Loader.load(filename);
    }

    // it'll have a search method
    public Product search(String name) {
        // that will go through each product
        for (Product p : inventory.products) {
            // and return if the names are equal
            if (p.name.equals(name)) {
                return p;
            }
        }
        return null;
    }
}

// our driver program
public class App {
    public static void main(String[] args) {
        // will create all the necessary instances
        Scanner in = new Scanner(System.in);
        Database db = new Database("exercise44_input.json");
        Product p;
        String name;
        // and then grab the input
        while (true) {
            System.out.print("What is the product name? ");
            name = in.nextLine();
            // search through the database,
            if ((p = db.search(name)) != null) {
                break;
            }
            // and print out the results
            System.out.println("Sorry, that product was not found in our inventory.");
        }
        System.out.println("{");
        System.out.println(Loader.load("exercise44_input.json"));
        System.out.println("}");
        // and print out the results
        System.out.println(p);
    }
}