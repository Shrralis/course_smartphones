package client.manufacturer_data_form;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Manufacturer;

/**
 * Created by shrralis on 3/2/17.
 */
public class Controller {
    private client.model_data_form.Controller.OnOkButtonClickListener onOkButtonClickListener = null;
    private Manufacturer manufacturer = null;

    @FXML TextField name;
    @FXML TextField country;
    @FXML
    protected void onMouseCancelClick(MouseEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @FXML
    protected void onMouseOkClick(MouseEvent event) {
        if (onOkButtonClickListener != null) {
            onOkButtonClickListener.onButtonOkClick();
        }
    }

    public void setOnOkButtonClickListener(client.model_data_form.Controller.OnOkButtonClickListener listener) {
        onOkButtonClickListener = listener;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public boolean isFieldsValidForAdd() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Помилка");
        alert.setHeaderText("Помилковий ввід");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(name.getScene().getWindow());

        if (name.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви не ввели назву!");
            alert.showAndWait();
            return false;
        }

        if (country.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви не ввели країну!");
            alert.showAndWait();
            return false;
        }
        setupManufacturer();
        return true;
    }

    private void setupManufacturer() {
        if (manufacturer == null) {
            manufacturer = new Manufacturer();
        }

        manufacturer.name = name.getText().trim();
        manufacturer.country = country.getText().trim();
    }
}
