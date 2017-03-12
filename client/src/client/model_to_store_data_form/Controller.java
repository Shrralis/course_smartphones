package client.model_to_store_data_form;

import client.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

/**
 * Created by shrralis on 3/10/17.
 */
public class Controller {
    public interface OnOkButtonClickListener {
        void onButtonOkClick();
    }

    private OnOkButtonClickListener onOkButtonClickListener = null;
    private ModelToStore modelToStoreToProcess = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    @FXML private ComboBox<Store> store;
    @FXML private TextField price;

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

    public ModelToStore getModelToStoreToProcess() {
        return modelToStoreToProcess;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;

        setSomething(Store.class);
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
    @SuppressWarnings("unchecked")
    private <T extends Owner> void setSomething(Class<T> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        ServerQuery query = ServerQuery.create(tableName, "get", null, null);
        ObservableList<T> list = FXCollections.observableArrayList();

        try {
            if (outputStream != null) {
                outputStream.writeObject(query);

                if (inputStream != null) {
                    ServerResult result = (ServerResult) inputStream.readObject();

                    if (result != null && result.getResult() == 0) {
                        if (result.getObjects() != null) {
                            for (T obj : (List<T>) result.getObjects()) {
                                list.add(obj);
                            }
                        }
                    } else {
                        Alerts.showBadResultAlert();
                    }
                } else {
                    Alerts.showBadInputStreamAlert();
                }
            } else {
                Alerts.showBadOutputStreamAlert();
            }

            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.getName().equalsIgnoreCase(tableName)) {
                    ((ComboBox) field.get(this)).setItems(list);
                    return;
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            Alerts.showSomeExceptionAlert(e);
        }
    }

    public void setOnOkButtonClickListener(OnOkButtonClickListener listener) {
        onOkButtonClickListener = () -> {
            if (isFieldsValidForAdd()) {
                setupModelToStore();

                if (listener != null) {
                    listener.onButtonOkClick();
                }
            }
        };
    }

    private boolean isFieldsValidForAdd() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Помилка");
        alert.setHeaderText("Помилковий ввід");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(store.getScene().getWindow());

        if (store.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали магазин!");
            alert.showAndWait();
            return false;
        }

        if (!price.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Ви не ввели ціну або формат невірний! (Формат: 1.09)");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void setupModelToStore() {
        modelToStoreToProcess = new ModelToStore();

        try {
            modelToStoreToProcess.setPrice(Double.parseDouble(price.getText().trim()));
        } catch (NumberFormatException ignored) {
            ignored.printStackTrace();
        } finally {
            modelToStoreToProcess.setStore(store.getSelectionModel().getSelectedItem());
        }
    }
}
