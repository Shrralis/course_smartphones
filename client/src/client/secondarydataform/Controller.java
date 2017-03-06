package client.secondarydataform;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Owner;

/**
 * Created by shrralis on 3/2/17.
 */
public class Controller<T extends Owner> {
    private client.dataform.Controller.OnOKButtonClickListener onOKButtonClickListener;
    private T objectToAdd = null;

    @FXML
    protected void onMouseClickedCancel(MouseEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void setOnOKButtonClickListener(client.dataform.Controller.OnOKButtonClickListener listener) {
        onOKButtonClickListener = listener;
    }

    public T getObjectToAdd() {
        return objectToAdd;
    }
}
