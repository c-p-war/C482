package wardlaw.mainscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static Part selectedPart;
    public static Product selectedProduct;
    public static Part getSelectedPart(){return selectedPart;}
    public static Product getSelectedProduct(){return selectedProduct;}

    @FXML
    public Button MainPartsAddBtn;
    @FXML
    public Button MainPartsModifyBtn;
    @FXML
    public Button MainPartsDeleteBtn;
    @FXML
    public TextField MainPartSearchField;
    @FXML
    public Button MainProductDeleteBtn;
    @FXML
    public Button MainProductModifyBtn;
    @FXML
    public Button MainProductAddBtn;
    @FXML
    public TextField MainProductSearchField;
    @FXML
    public Button MainExitBtn;
    @FXML
    private TableView<Part> MainPartsTable;
    @FXML
    private TableColumn<Part, Integer> ColumnPartId;
    @FXML
    private TableColumn<Part, String> ColumnPartName;
    @FXML
    private TableColumn<Part, Integer> ColumnPartInvLevel;
    @FXML
    private TableColumn<Part, Integer> ColumnPartPrice;
    @FXML
    private TableView<Product> MainProductTable;
    @FXML
    private TableColumn<Object, Object> ColumnProductId;
    @FXML
    private TableColumn<Object, Object> ColumnProductName;
    @FXML
    private TableColumn<Object, Object> ColumnProductInvLevel;
    @FXML
    private TableColumn<Object, Object> ColumnProductPrice;

    public void mainPartsSearchClicked() {
        String searchInput = MainPartSearchField.getText();
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (Part part : allParts){
            if(String.valueOf(part.getId()).contains(searchInput) || part.getName().toLowerCase().contains(searchInput)){
                matchingParts.add(part);
            }
        }
        MainPartsTable.setItems(matchingParts);

        if (matchingParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setContentText("No matches found.\n\nTry again.");
            alert.showAndWait();
        }

    }
    public void mainPartsAddBtnClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void mainPartsModifyBtnClicked(ActionEvent actionEvent) throws IOException {
        selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setContentText("No part selected \nTry again");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ModifyPart.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void mainPartsDeleteBtnClicked() {
        selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("No part selected \nTry again");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to delete this part?");
            alert.setContentText("Ok -> Delete Product \n\nCancel -> Keep Product");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK){
                Inventory.deletePart(selectedPart);
            }
        }
    }

    public void mainProductsSearchClicked() {
        String searchInput = MainProductSearchField.getText();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for (Product product : allProducts){
            if(String.valueOf(product.getId()).contains(searchInput) || product.getName().toLowerCase().contains(searchInput)){
                matchingProducts.add(product);
            }
        }
        MainProductTable.setItems(matchingProducts);

        if (matchingProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setContentText("No matches found.\n\nTry again.");
            alert.showAndWait();
        }
    }

    public void mainProductsAddBtnClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void mainProductsModifyBtnClicked(ActionEvent actionEvent) throws IOException {
        selectedProduct = MainProductTable.getSelectionModel().getSelectedItem();
        if  (selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setContentText("No Product selected \nTry again");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ModifyProduct.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    public void mainProductsDeleteBtnClicked() {
        selectedProduct = MainProductTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedProduct.getAllAssociatedParts());
        if (selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("No Product selected \nTry again");
            alert.showAndWait();
        } else if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            // Enhancement, detail which parts are associated
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Action forbidden. \n\n This product has parts associated with it.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to delete this product?");
            alert.setContentText("Ok -> Delete Product \n\nCancel -> Keep Product");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK){
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    public void mainExitBtnClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainPartsTable.setItems(Inventory.getAllParts());
        ColumnPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColumnPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColumnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainProductTable.setItems(Inventory.getAllProducts());
        ColumnProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColumnProductInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColumnProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
