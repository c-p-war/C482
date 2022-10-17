package wardlaw.mainscreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * This class creates the Inventory application.
 *
 * <b>The javadoc files are located in /javadoc.</b>
 *
 * <p><b>
 * FUTURE ENHANCEMENT: Ability to copy a 'template' of another product from the inventory during product creation. This would speed up the creation of complex products that have a large number of associated parts.
 * </b></p>
 */
public class main extends Application {
    /**
     * The start method initializes the fxml file MainScreen.fxml
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/wardlaw/mainscreen/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Wardlaw C482");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method populates mock-data
     *
     * @param args
     */
    public static void main(String[] args) {
        Part tire = new InHouse(1, "Tire", 72.00, 500, 1, 4, 1);
        Inventory.addPart(tire);
        Part handleBars = new InHouse(2, "Handle Bars", 30.00, 300, 1, 2, 2);
        Inventory.addPart(handleBars);
        Part chain = new InHouse(3, "Chain", 22.75, 150, 1, 4, 3);
        Inventory.addPart(chain);
        Part gear = new Outsourced(4, "Gear", 41.50, 10, 1, 6, "Stingray LLC");
        Inventory.addPart(gear);
        Product unicycle = new Product(1, "Unicycle", 500.00, 5, 1, 5);
        Inventory.addProduct(unicycle);
        Product tandem = new Product(2, "Tandem", 1250.00, 3, 1, 4);
        Inventory.addProduct(tandem);
        launch();
    }
}
