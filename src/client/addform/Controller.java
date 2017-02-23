package client.addform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

/**
 * Created by shrralis on 2/23/17.
 */
public class Controller {
    @FXML private ComboBox<Manufacturer> manufacturer;
    @FXML private TextField model;
    @FXML private ComboBox<Standard> standard;
    @FXML private ComboBox<OS> os;
    @FXML private TextField os_version;
    @FXML private ComboBox<EnclosureType> enclosure_type;
    @FXML private ComboBox<EnclosureMaterial> enclosure_material;
    @FXML private TextField sim_amount;
    @FXML private ComboBox<SimCardType> sim_type;
    @FXML private TextField thickness;
    @FXML private TextField weight;
    @FXML private ComboBox<String> color;
    @FXML private ComboBox<ScreenType> screen_type;
    @FXML
    protected void onMouseClickedOK(MouseEvent event) {
        Manufacturer manufacturer = this.manufacturer.getSelectionModel().getSelectedItem();

        if (manufacturer == null) {
            System.err.println("nothing");
        } else {
            onMouseClickedCancel(event);
        }
    }
    @FXML
    protected void onMouseClickedCancel(MouseEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @FXML
    public void initialize() {
        setManufacturers();
    }

    protected final void setManufacturers() {
        ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
        manufacturers.add(new Manufacturer());

        manufacturer.setItems(manufacturers);
        /*manufacturer.setCellFactory(param -> new ListCell<Manufacturer>() {
            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setText(item.getName());
                    setGraphic(null);
                } else {
                    setText("невідомо");
                }
            }
        });*/
    }
}
