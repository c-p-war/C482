package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class - Used to create, retrieve, and remove products
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product constructor
     * @param id    product id
     * @param name  product name
     * @param price product price
     * @param stock # of products in stock
     * @param min   minimum number of products to keep in stock
     * @param max   maximum number of products to keep in stock
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
     * Sets the product id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the product name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the product price
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the product stock
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum product stock level
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum product stock level
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Returns the product id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the product name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the product price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns the product stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Returns the minimum product stock
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Returns the maximum product stock
     */
    public int getMax() {
        return this.max;
    }

    /**
     * Associates a part with a product
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes an associated part from a product
     *
     * @param selectedAssociatedPart
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * @return All parts associates with a product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
