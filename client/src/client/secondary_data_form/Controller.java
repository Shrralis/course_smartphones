package client.secondary_data_form;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Owner;

/**
 * Created by shrralis on 3/2/17.
 */
public class Controller<T extends Owner> {
    private client.model_data_form.Controller.OnOkButtonClickListener onOkButtonClickListener = null;
    private T objectToAdd = null;

    @FXML TextField name;
    @FXML
    protected void onMouseCancelClick(MouseEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @FXML
    protected void onMouseOkClick() {
        if (onOkButtonClickListener != null) {
            onOkButtonClickListener.onButtonOkClick();
        }
    }

    public void setOnOkButtonClickListener(client.model_data_form.Controller.OnOkButtonClickListener listener) {
        onOkButtonClickListener = listener;
    }

    public T getObjectToAdd() {
        return objectToAdd;
    }

    public boolean isFieldsValidForAdd(Class<T> clazz) {
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
        setupObjectToAdd(clazz);
        return true;
    }
    @SuppressWarnings("unchecked")
    private void setupObjectToAdd(Class<T> clazz) {
        if (objectToAdd == null) {
            try {
                objectToAdd = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException ignored) {
                ignored.printStackTrace();
            }
        }

        objectToAdd.name = name.getText().trim();
    }
}
