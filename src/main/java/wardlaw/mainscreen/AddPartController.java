package wardlaw.mainscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.text.NumberFormat;
import java.util.Objects;

public class AddPartController {
    @FXML
    private Label AddPartLabelDynamic;
    @FXML
    private ToggleGroup addPartToggleGroup;
    @FXML
    private RadioButton AddPartRadioInHouse;
    @FXML
    private RadioButton AddPartRadioOutsourced;

    @FXML
    private TextField AddPartTxtFieldName;
    @FXML
    private TextField AddPartTxtFieldInv;
    @FXML
    private TextField AddPartTxtFieldPrice;
    @FXML
    private TextField AddPartTxtFieldMax;
    @FXML
    private TextField AddPartTxtFieldMin;
    @FXML
    private TextField AddPartTxtFieldDynamic;

    public void addPartRadioInHouseClicked() {
        if (this.addPartToggleGroup.getSelectedToggle().equals(this.AddPartRadioInHouse)) {
            AddPartLabelDynamic.setText("Machine ID");
        }
    }

    public void addPartRadioOutsourcedClicked() {
        if (this.addPartToggleGroup.getSelectedToggle().equals(this.AddPartRadioOutsourced)) {
            AddPartLabelDynamic.setText("Company Name");
        }
    }

    public void addPartSaveBtnClicked(ActionEvent actionEvent) throws IOException {
        try {int id = (int) (Math.random() * 100);
        String name = AddPartTxtFieldName.getText();
        int inv = Integer.parseInt(AddPartTxtFieldInv.getText());
        double price = Double.parseDouble(AddPartTxtFieldPrice.getText());
        int max = Integer.parseInt(AddPartTxtFieldMax.getText());
        int min = Integer.parseInt(AddPartTxtFieldMin.getText());
        if (max < min){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be lower than min");
            alert.showAndWait();
            return;
        } else if (inv < min || max < inv){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid inventory count. \nInv must be more than min\nand\n Inv must be less than max");
            alert.showAndWait();
            return;
        }

        if (this.addPartToggleGroup.getSelectedToggle().equals(this.AddPartRadioOutsourced)) {
            String companyName = AddPartTxtFieldDynamic.getText();
            Part newPart = new Outsourced(id, name, price, inv, min, max, companyName);
            Inventory.addPart(newPart);
        }

        if (this.addPartToggleGroup.getSelectedToggle().equals(this.AddPartRadioInHouse)){
            int machineId = Integer.parseInt(AddPartTxtFieldDynamic.getText());
            Part newPart = new InHouse(id, name, price, inv, min, max, machineId);
            Inventory.addPart(newPart);
        }

        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();} catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Invalid value for one or more fields.\n\n Try again.");
            alert.showAndWait();
        }

    }

    public void addPartCancelBtnClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
