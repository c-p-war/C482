package wardlaw.mainscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Handles all elements on the ModifyPart screen
 */
public class ModifyPartController implements Initializable {
    @FXML
    private TextField ModifyPartTxtFieldId;
    @FXML
    private TextField ModifyPartTxtFieldName;
    @FXML
    private TextField ModifyPartTxtFieldInv;
    @FXML
    private TextField ModifyPartTxtFieldPrice;
    @FXML
    private TextField ModifyPartTxtFieldMax;
    @FXML
    private TextField ModifyPartTxtFieldMin;
    @FXML
    private ToggleGroup modifyPartToggleGroup;
    @FXML
    private Toggle ModifyPartRadioInHouse;
    @FXML
    private Toggle ModifyPartRadioOutsourced;
    @FXML
    private Label ModifyPartLabelDynamic;
    public Part selectedPart;
    @FXML
    private TextField ModifyPartTxtFieldDynamic;

    /**
     * Toggles dynamic label to read 'Machine ID'
     */
    public void modifyPartInHouseRadioClicked() {
        if (this.modifyPartToggleGroup.getSelectedToggle().equals(this.ModifyPartRadioInHouse)) {
            ModifyPartLabelDynamic.setText("Machine ID");
        }
    }

    /**
     * Toggles dynamic label to read 'Company Name'
     */
    public void modifyPartOutsourcedRadioClicked() {
        if (this.modifyPartToggleGroup.getSelectedToggle().equals(this.ModifyPartRadioOutsourced)) {
            ModifyPartLabelDynamic.setText("Company Name");
        }
    }

    /**
     * Saves an updated part. Implements field restrictions and handles errors.
     *
     * @param actionEvent Emitted from the ModifyPart screen when the 'Save' button is clicked.
     */
    public void modifyPartSaveBtnClicked(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(ModifyPartTxtFieldId.getText());
            String name = ModifyPartTxtFieldName.getText();
            int inv = Integer.parseInt(ModifyPartTxtFieldInv.getText());
            double price = Double.parseDouble(ModifyPartTxtFieldPrice.getText());
            int max = Integer.parseInt(ModifyPartTxtFieldMax.getText());
            int min = Integer.parseInt(ModifyPartTxtFieldMin.getText());
            int machineId;
            if (this.ModifyPartRadioInHouse.isSelected()) {
                machineId = Integer.parseInt(ModifyPartTxtFieldDynamic.getText());
                InHouse updatedPart = new InHouse(id, name, price, inv, min, max, machineId);
                Inventory.deletePart(selectedPart);
                Inventory.addPart(updatedPart);
            }
            if (this.ModifyPartRadioOutsourced.isSelected()) {
                String companyName = ModifyPartTxtFieldDynamic.getText();
                Outsourced updatedPart = new Outsourced(id, name, price, inv, min, max, companyName);
                Inventory.deletePart(selectedPart);
                Inventory.addPart(updatedPart);
            }

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
     * /**
     * Exits the ModifyPart screen and returns the user to the MainScreen
     *
     * @param actionEvent Emitted from the ModifyPart screen when the 'Cancel' button is clicked
     */
    public void modifyPartCancelBtnClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Modify Part - Cancel btn clicked");
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the ModifyPart TextFields
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedPart = MainController.getSelectedPart();
        ModifyPartTxtFieldId.setText(String.valueOf(selectedPart.getId()));
        ModifyPartTxtFieldName.setText(String.valueOf(selectedPart.getName()));
        ModifyPartTxtFieldInv.setText(String.valueOf(selectedPart.getStock()));
        ModifyPartTxtFieldPrice.setText(String.valueOf(selectedPart.getPrice()));
        ModifyPartTxtFieldMax.setText(String.valueOf(selectedPart.getMax()));
        ModifyPartTxtFieldMin.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            ModifyPartRadioInHouse.setSelected(true);
            ModifyPartLabelDynamic.setText("Machine Id");
            ModifyPartTxtFieldDynamic.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else {
            ModifyPartRadioOutsourced.setSelected(true);
            ModifyPartLabelDynamic.setText("Company Name");
            ModifyPartTxtFieldDynamic.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }
    }
}
