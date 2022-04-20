package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Richard Milliorn
 * WGU C82
 *
 * copied and pasted part since its the same with additional observable list and methods
 *
 * while writing inventory model is made me extend Parts on this page
 * i forgot to change allParts to allProducts when copying the code
 *
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *  has to be intitalized to fx collection from webinar
     *
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * creates a new product using the provided parameters.
     * @param id product ID
     * @param name product name
     * @param price product price
     * @param stock product inventory
     * @param min minimum inventory
     * @param max max inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * adds parts to associated parts list.
     * @param part
     *
     * didnt realize the the UML diagram said Part:part couldnt get it to work at first.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** deletes selected part if it is found in the list
     *was told not to have static because it breaks add part by Mark K.
     * @param selectedAssociatedPart  which item we are searching for.
     * @return returns true or false depending if the associated part is found.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * returns list of all associated parts
     *
     * its really neat that this auto populates code for you.
     * i started typing this out and it filled the rest aside from all.
     *
     * @return
     */
    public  ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}