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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles all elements on the ModifyProduct screen
 */
public class ModifyProductController implements Initializable {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private TextField ModifyProductTxtFieldName;
    @FXML
    private TextField ModifyProductTxtFieldInv;
    @FXML
    private TextField ModifyProductTxtFieldPrice;
    @FXML
    private TextField ModifyProductTxtFieldMax;
    @FXML
    private TextField ModifyProductTxtFieldMin;
    @FXML
    private TextField ModifyProductTxtFieldId;
    public Product selectedProduct;
    @FXML
    private TableView<Part> AssocPartsTable;
    @FXML
    private TableView<Part> AllPartsTable;
    @FXML
    private TableColumn<Object, Object> AllPartsTableColumnPartID;
    @FXML
    private TableColumn<Object, Object> AllPartsTableColumnName;
    @FXML
    private TableColumn<Object, Object> AllPartsTableColumnInventory;
    @FXML
    private TableColumn<Object, Object> AllPartsTableColumnPrice;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnPartID;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnName;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnInventory;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnPrice;
    @FXML
    private TextField ModifyProductTxtFieldSearch;
    /**
     * Returns matching parts from the ModifyProduct part-search feature
     */
    public void modifyProductSearchClicked() {
        String searchInput = ModifyProductTxtFieldSearch.getText();
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchInput) || part.getName().toLowerCase().contains(searchInput)) {
                matchingParts.add(part);
            }
        }
        AllPartsTable.setItems(matchingParts);

        if (matchingParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setContentText("No matches found.\n\nTry again.");
            alert.showAndWait();
        }
    }

    /**
     * Adds a selected part from the AllParts table to the AssocParts table in the ModifyProduct screen
     */
    public void modifyProductAddBtnClicked() {
        Part selectedPart = AllPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Error");
            alert.setContentText("No Part selected \n\nTry again");
            alert.showAndWait();
        } else if (associatedParts.contains(selectedPart)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Relation Error");
            alert.setContentText("This product is already associated with the selected part \n\nTry again");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
            AssocPartsTable.setItems(associatedParts);
        }
    }

    /**
     * Removes a selected part from the AssocParts table in the ModifyProduct screen
     */
    public void modifyProductRemoveBtnClicked() {
        Part selectedPart = AssocPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Remove Error");
            alert.setContentText("No part selected \n\nTry again");
            alert.showAndWait();
        }
        if (associatedParts.contains(selectedPart)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete this Part?");
            alert.setContentText("Ok -> Delete Part \n\nCancel -> Keep Part");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                AssocPartsTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Saves an updated product. Implements field restrictions and handles errors.
     * @param actionEvent Emitted from the ModifyProduct screen when the 'Save' button is clicked
     */
    public void modifyProductSaveBtnClicked(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(ModifyProductTxtFieldId.getText());
            String name = ModifyProductTxtFieldName.getText();
            int inv = Integer.parseInt(ModifyProductTxtFieldInv.getText());
            double price = Double.parseDouble(ModifyProductTxtFieldPrice.getText());
            int max = Integer.parseInt(ModifyProductTxtFieldMax.getText());
            int min = Integer.parseInt(ModifyProductTxtFieldMin.getText());
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be lower than min");
                alert.showAndWait();
                return;
            } else if (inv < min || max < inv) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid inventory count. \n\nInv must be more than min and\n\nInv must be less than max");
                alert.showAndWait();
                return;
            }

            Product updatedProduct = new Product(id, name, price, inv, min, max);
            for (Part part : associatedParts) {
                if (associatedParts.contains(part)) {
                    updatedProduct.addAssociatedPart(part);
                } else if (!associatedParts.contains(part)) {
                    updatedProduct.deleteAssociatedPart(part);
                }
            }

            Inventory.deleteProduct(selectedProduct);
            Inventory.addProduct(updatedProduct);


            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Invalid value for one or more fields.\n\n Try again.");
            alert.showAndWait();
        }
    }

    /**
     * Exits the ModifyProduct screen and returns the user to the MainScreen
     * @param actionEvent Emitted from the ModifyProduct screen when the 'Exit' button is clicked
     */
    public void modifyProductCancelBtnClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Modify Product - Cancel btn clicked");
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the ModifyProduct TextFields, AllPartsTable, and AssocParts tables
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedProduct = MainController.getSelectedProduct();
        ModifyProductTxtFieldId.setText(String.valueOf(selectedProduct.getId()));
        ModifyProductTxtFieldName.setText(String.valueOf(selectedProduct.getName()));
        ModifyProductTxtFieldInv.setText(String.valueOf(selectedProduct.getStock()));
        ModifyProductTxtFieldPrice.setText(String.valueOf(selectedProduct.getPrice()));
        ModifyProductTxtFieldMax.setText(String.valueOf(selectedProduct.getMax()));
        ModifyProductTxtFieldMin.setText(String.valueOf(selectedProduct.getMin()));

        AllPartsTable.setItems(Inventory.getAllParts());
        AllPartsTableColumnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AllPartsTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AllPartsTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AllPartsTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts = selectedProduct.getAllAssociatedParts();

        AssocPartsTable.setItems(associatedParts);
        AssocPartsTableColumnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartsTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartsTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPartsTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
