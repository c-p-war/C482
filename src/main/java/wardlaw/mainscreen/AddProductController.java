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

/**
 * Handles all elements on the AddProduct screen
 */
public class AddProductController implements Initializable {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> AssocPartsTable;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnPartID;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnName;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnInventory;
    @FXML
    private TableColumn<Object, Object> AssocPartsTableColumnPrice;
    @FXML
    private TextField AddProductTxtFieldName;
    @FXML
    private TextField AddProductTxtFieldInv;
    @FXML
    private TextField AddProductTxtFieldPrice;
    @FXML
    private TextField AddProductTxtFieldMax;
    @FXML
    private TextField AddProductTxtFieldMin;
    @FXML
    private TextField AddProductTxtFieldSearch;
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

    /**
     * Returns matching parts from the AddProduct part-search feature
     */
    public void addProductSearchClicked() {
        String searchInput = AddProductTxtFieldSearch.getText();
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
     * Adds a selected part from the AllParts table to the AssocParts table in the AddProduct screen
     */
    public void addProductAddBtnClicked() {
        Part selectedPart = AllPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Error");
            alert.setContentText("No part selected \n\nTry again");
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
    public void addProductRemoveBtnClicked() {
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
     * Saves a new product. Implements field restrictions and handles errors.
     * @param actionEvent Emitted from the AddProduct screen when the 'Save' button is clicked
     */
    public void addProductSaveBtnClicked(ActionEvent actionEvent) throws IOException {
        try {
            int id = (int) (Math.random() * 100);
            String name = AddProductTxtFieldName.getText();
            int inv = Integer.parseInt(AddProductTxtFieldInv.getText());
            double price = Double.parseDouble(AddProductTxtFieldPrice.getText());
            int max = Integer.parseInt(AddProductTxtFieldMax.getText());
            int min = Integer.parseInt(AddProductTxtFieldMin.getText());
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be lower than min");
                alert.showAndWait();
                return;
            } else if (inv < min || max < inv) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid inventory count. \n\nInv must be more than min and\n\nInv must be less than max");
                alert.showAndWait();
                return;
            }
            Product newProduct = new Product(id, name, price, inv, min, max);
            for (Part part : associatedParts) {
                if (associatedParts.contains(part)) {
                    newProduct.addAssociatedPart(part);
                }
            }

            Inventory.addProduct(newProduct);

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
     * Exits the AddProduct screen and returns the user to the MainScreen
     * @param actionEvent Emitted from the AddProduct screen when the 'Exit' button is clicked
     */
    public void addProductCancelBtnClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Populates the AddProduct TextFields, AllPartsTable, and AssocParts tables
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllPartsTable.setItems(Inventory.getAllParts());
        AllPartsTableColumnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AllPartsTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AllPartsTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AllPartsTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        AssocPartsTable.setItems(associatedParts);
        AssocPartsTableColumnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartsTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartsTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPartsTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
