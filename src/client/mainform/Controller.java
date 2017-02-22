package client.mainform;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by shrralis on 2/21/17.
 */
@SuppressWarnings("unchecked")
public class Controller {
    private ObservableList<SmartphoneModel> data = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    private Stage stage;
    @FXML
    private TableView<SmartphoneModel> table;
    @FXML
    private RadioButton by_manufacturer;
    @FXML
    private RadioButton by_standard;
    @FXML
    private RadioButton by_os;
    @FXML
    private RadioButton by_os_version;
    @FXML
    private RadioButton by_enclosure_type;
    @FXML
    private RadioButton by_enclosure_material;
    @FXML
    private RadioButton by_sim_amount;
    @FXML
    private RadioButton by_sim_type;
    @FXML
    private RadioButton by_thickness;
    @FXML
    private RadioButton by_weight;
    @FXML
    private RadioButton by_color;
    @FXML
    private RadioButton by_screen_type;
    @FXML
    private RadioButton by_screen_diagonal;
    @FXML
    private RadioButton by_screen_resolution;
    @FXML
    private RadioButton by_battery_type;
    @FXML
    private RadioButton by_battery_capacity;
    @FXML
    private RadioButton by_ram;
    @FXML
    private RadioButton by_internal_storage;
    @FXML
    private RadioButton by_camera;
    @FXML
    private TableColumn manufacturer;
    @FXML
    private TableColumn standard;
    @FXML
    private TableColumn os;
    @FXML
    private TableColumn enclosure_type;
    @FXML
    private TableColumn enclosure_material;
    @FXML
    private TableColumn sim_card_type;
    @FXML
    private TableColumn screen_type;
    @FXML
    private TableColumn battery_type;
    @FXML
    private TableColumn memory_card_type;
    @FXML
    private TableColumn processor;
    @FXML
    @SuppressWarnings("unchecked")
    protected void onMouseClickAdd(MouseEvent event) {
        getAll();
    }
    @FXML
    protected void onMouseClickEdit(MouseEvent event) {
        System.out.println("You clicked Edit button");
    }
    @FXML
    protected void onMouseClickDelete(MouseEvent event) {
        System.out.println("You clicked Delete button");
    }
    @FXML
    protected void onMouseClickSearch(MouseEvent event) {
        System.out.println("You clicked Search button and checked search by " + getSearchCheckedType());
    }

    void show(Stage primaryStage, ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException {
//        getAll();
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    private String getSearchCheckedType() {
        if (by_manufacturer.isSelected()) {
            return "manufacturer";
        } else if (by_standard.isSelected()) {
            return "standard";
        } else if (by_os.isSelected()) {
            return "os";
        } else if (by_os_version.isSelected()) {
            return "os_version";
        } else if (by_enclosure_type.isSelected()) {
            return "enclosure_type";
        } else if (by_enclosure_material.isSelected()) {
            return "enclosure_material";
        } else if (by_sim_amount.isSelected()) {
            return "sim_amount";
        } else if (by_sim_type.isSelected()) {
            return "sim_type";
        } else if (by_thickness.isSelected()) {
            return "thickness";
        } else if (by_weight.isSelected()) {
            return "weight";
        } else if (by_color.isSelected()) {
            return "color";
        } else if (by_screen_type.isSelected()) {
            return "screen_type";
        } else if (by_screen_diagonal.isSelected()) {
            return "screen_diagonal";
        } else if (by_screen_resolution.isSelected()) {
            return "screen_resolution";
        } else if (by_battery_type.isSelected()) {
            return "battery_type";
        } else if (by_battery_capacity.isSelected()) {
            return "battery_capacity";
        } else if (by_ram.isSelected()) {
            return "ram";
        } else if (by_internal_storage.isSelected()) {
            return "internal_storage";
        } else if (by_camera.isSelected()) {
            return "camera";
        } else {
            return "none";
        }
    }

    private void getAll() {
        manufacturer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SmartphoneModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SmartphoneModel, String> param) {
                if (param.getValue() != null && param.getValue().manufacturer != null) {
                    return new SimpleStringProperty(param.getValue().manufacturer.name);
                } else {
                    return new SimpleStringProperty("Невідомо");
                }
            }
        });

        try {
            ServerQuery query = ServerQuery.create("model", "get", null, null);

            if (outputStream != null) {
                outputStream.writeObject(query);

                if (inputStream != null) {
                    ServerResult result = (ServerResult) inputStream.readObject();

                    if (result != null && result.getMessage().equalsIgnoreCase("success")) {
                        System.out.println("size: " + result.getObjects().size());
                        for (SmartphoneModel smartphone :
                                (List<SmartphoneModel>) result.getObjects()) {
                            if (data != null) {
                                data.add(smartphone);
                            } else {
                                data = FXCollections.observableArrayList(smartphone);

                                table.setItems(data);
                            }
                            System.out.println(data.get(0).camera);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ignored) {}
    }
}
