package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public Part lookupPart(int partId){
        return null;
    }

    public Product lookupProduct (int productId){
        return null;
    }

    public ObservableList lookupPart (String partName){
        return null;
    }

    public ObservableList lookupProduct (String productName){
        return null;
    }

    public void updatePart (int index, Part selectedPart){
        allParts.set(index, selectedPart);

    }

    public void updateProduct (int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart){
      if(allParts.contains(selectedPart)){
          allParts.remove(selectedPart);
          return true;
      } else {
          return false;
      }
    }

    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
