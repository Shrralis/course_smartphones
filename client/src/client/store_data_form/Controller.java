package client.store_data_form;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Store;

/**
 * Created by shrralis on 3/10/17.
 */
public class Controller {
    public enum FormType {
        Add,
        Edit
    }

    public interface OnOkButtonClickListener {
        void onButtonOkClick();
    }

    private OnOkButtonClickListener onOkButtonClickListener = null;
    private Store storeToProcess = null;

    @FXML private TextField name;
    @FXML private TextField link;

    @FXML
    protected void onMouseOkClick() {
        if (onOkButtonClickListener != null) {
            onOkButtonClickListener.onButtonOkClick();
        }
    }
    @FXML
    protected void onMouseCancelClick(MouseEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void setStoreToProcess(Store storeToProcess) {
        this.storeToProcess = storeToProcess;
    }

    public Store getStoreToProcess() {
        return storeToProcess;
    }

    public void setFormType(FormType formType) {
        final OnOkButtonClickListener currentListener = onOkButtonClickListener;

        onOkButtonClickListener = () -> {
            if (isFieldsValidForAdd()) {
                setupStore();

                if (currentListener != null) {
                    currentListener.onButtonOkClick();
                }
            }
        };

        if (formType == FormType.Edit) {
            setupFormBasedOnStore();
        }
    }

    public void setOnOkButtonClickListener(OnOkButtonClickListener listener) {
        onOkButtonClickListener = listener;
    }

    private boolean isFieldsValidForAdd() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Помилка");
        alert.setHeaderText("Помилковий ввід");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(name.getScene().getWindow());

        if (name.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви не ввели назву магазину!");
            alert.showAndWait();
            return false;
        }

        if (!link.getText().trim().matches("^http(s)?://(\\d|\\D)+.(\\d|\\D)+$")) {
            alert.setContentText("Ви не ввели інтернет-адресу або формат невірний! (Формат: http(s)://rozetka.ua)");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void setupStore() {
        final Store oldStore = storeToProcess;
        storeToProcess = new Store();

        if (oldStore != null) {
            storeToProcess.id = oldStore.getId();
        }

        storeToProcess.name = name.getText().trim();
        storeToProcess.link = link.getText().trim();
    }

    private void setupFormBasedOnStore() {
        name.setText(storeToProcess.getName());
        link.setText(storeToProcess.getLink());
    }
}
