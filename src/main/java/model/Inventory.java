package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class - Used to manage exiting Parts and Products
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to inventory
     *
     * @param newPart The part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product to inventory
     *
     * @param newProduct The product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Retrieves part based on id
     *
     * @param partId A parts assigned identification number
     */
    public Part lookupPart(int partId) {
        return null;
    }

    /**
     * Retrieves product based on id
     *
     * @param productId A products assigned identification number
     */
    public Product lookupProduct(int productId) {
        return null;
    }

    /**
     * Retrieves part based on name
     *
     * @param partName A parts assigned name
     */
    public ObservableList lookupPart(String partName) {
        return null;
    }

    /**
     * Retrieves product based on name
     *
     * @param productName A products assigned name
     */
    public ObservableList lookupProduct(String productName) {
        return null;
    }

    /**
     * Updates part based on index
     *
     * @param index        Location of selected part
     * @param selectedPart Desired selection
     */
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);

    }

    /**
     * Updates product based on index
     *
     * @param index      Location of selected product
     * @param newProduct Desired selection
     */
    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Removes selected part from allParts
     *
     * @param selectedPart Part to be removed
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes selected product from allProducts
     *
     * @param selectedProduct Product to be removed
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all parts from inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves all products from inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
