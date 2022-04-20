package model;

/**
 * @author Richard Milliorn
 * WGU C482
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Method used to add part to parts array
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds newProduct to allProduct list
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up part by ID number provided.
     *
     * @param Id Id number of part being looked up
     * @return returns part(s) with matching ID
     */
    public static Part lookupPart(int Id) {
        for(int i = 0; i < allParts.size(); i++) {
            Part searchPart = allParts.get(i);

            if (searchPart.getId() == Id) {
                return searchPart;
            }
        }
        return null;
    }

    /**
     * Looks up part using name provided.
     * RUNTIME ERROR!!!!
     * having return in if statement stays stuck in the search and tables will not repopulate after clearing search
     * FUTURE ENHANCEMENT
     * Make it where search returns value ignoring case. I couldnt get it to populate again with empty string if i tried Ignore case
     * @param partialName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partialName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        for ( Part searchPart : allParts) {
            if (searchPart.getName().contains(partialName)) {
                namedPart.add(searchPart);
            }
        }
        return namedPart;
    }

    /**
     *Looks up product with name provided.
     * RUNTIME ERROR!!!!
     * having return in if statement stays stuck in the search and tables will not repopulate after clearing search
     * FUTURE ENHANCEMENT
     * Make it where search returns value ignoring case. I couldnt get it to populate again with empty string if i tried Ignore case
     * @param partialName
     * @return
     */

    /**
     * Looks up product by ID number provided.
     * @param Id product Id being looked up
     * @return returns product(s) with matching ID
     * <p>
     * copied and pasted after doing part. changed names for product.
     */
    public static Product lookupProduct(int Id) {
        for(int i = 0; i < allProducts.size(); i++) {
            Product searchProduct = allProducts.get(i);
            if (searchProduct.getId() == Id)
                return searchProduct;
        }
        return null;
    }

    /**
     * looks up product using the provided name
     *  FUTURE ENHANCEMENT
     *      * Make it where search returns value ignoring case. I couldn't get it to populate again with empty string if i tried Ignore case
     * @param partialName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String partialName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        for (Product searchProduct : allProducts) {
            if (searchProduct.getName().contains(partialName)) {
                namedProduct.add(searchProduct);
            }
        }
        return namedProduct;
    }

    /**
     * changes the part at the assigned index location.
     * @param index locates the assigned part.
     * @param newPart part to replace existing index location
     */
    public static void updatePart(int index, Part newPart) {
        allParts.set(index,newPart);
    }

    /** updates index location with new product
     *
     * LOGICAL ERROR
     *
     * kept trying to run new as in C++ class PA
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** copied and pasted from product and modified
     *
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
               allParts.remove(selectedPart);
                return true;
            }
            else {
                return false;
            }
    }

    /**
     * copied and pasted from product and modified
     *
     * RUNTIME ERROR
     * could not get items to delete from my product screen because i had allParts and not allProducts
     * forgot to change all parts to all products. be careful with Copy & Paste
     *
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the list of all parts currently created
     * forgot to return a list and had it just returning a part. would not compile until i added the observable list<Part>
     *
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** returns current list of products.
     *
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
