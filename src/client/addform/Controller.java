package client.addform;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Manufacturer;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by shrralis on 2/23/17.
 */
public class Controller {
    @FXML
    ComboBox<Manufacturer> manufacturer;
    @FXML
    protected void onMouseClickedOK(MouseEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    protected void onMouseClickedCancel(MouseEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    public void initialize() {

    }
}
