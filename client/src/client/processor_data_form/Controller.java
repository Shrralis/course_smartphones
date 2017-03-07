package client.processor_data_form;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Processor;

/**
 * Created by shrralis on 3/2/17.
 */
public class Controller {
    private client.data_form.Controller.OnOkButtonClickListener onOkButtonClickListener = null;
    private Processor processor = null;

    @FXML TextField name;
    @FXML TextField cores;
    @FXML TextField frequency;
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

    public void setOnOkButtonClickListener(client.data_form.Controller.OnOkButtonClickListener listener) {
        onOkButtonClickListener = listener;
    }

    public Processor getProcessor() {
        return processor;
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

        if (!cores.getText().trim().matches("[0-9]+")) {
            alert.setContentText("Ви не ввели кількість ядер або введене не ціле число!");
            alert.showAndWait();
            return false;
        }

        if (!frequency.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Ви не ввели частоту або формат невірний! (формат: 1.09)");
            alert.showAndWait();
            return false;
        }
        setupProcessor();
        return true;
    }

    private void setupProcessor() {
        if (processor == null) {
            processor = new Processor();
        }

        processor.name = name.getText().trim();
        processor.cores = Integer.parseInt(cores.getText().trim());
        processor.frequency = Double.parseDouble(frequency.getText().trim());
    }
}
