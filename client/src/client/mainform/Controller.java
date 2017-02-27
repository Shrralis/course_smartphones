package client.mainform;

import client.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.List;
import models.ServerQuery;
import models.ServerResult;
import models.SmartphoneModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by shrralis on 2/21/17.
 */
@SuppressWarnings("unchecked")
public class Controller {
    interface OnRefreshButtonClickListener {
        void onRefreshButtonClicked();
    }

    private OnRefreshButtonClickListener onRefreshButtonClickListener = null;
    private ObservableList<SmartphoneModel> data = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    @FXML private TableView<SmartphoneModel> table;
    @FXML private RadioButton by_manufacturer;
    @FXML private RadioButton by_standard;
    @FXML private RadioButton by_os;
    @FXML private RadioButton by_os_version;
    @FXML private RadioButton by_enclosure_type;
    @FXML private RadioButton by_enclosure_material;
    @FXML private RadioButton by_sim_amount;
    @FXML private RadioButton by_sim_type;
    @FXML private RadioButton by_thickness;
    @FXML private RadioButton by_weight;
    @FXML private RadioButton by_color;
    @FXML private RadioButton by_screen_type;
    @FXML private RadioButton by_screen_diagonal;
    @FXML private RadioButton by_screen_resolution;
    @FXML private RadioButton by_battery_type;
    @FXML private RadioButton by_battery_capacity;
    @FXML private RadioButton by_ram;
    @FXML private RadioButton by_internal_storage;
    @FXML private RadioButton by_camera;
    @FXML private TableColumn<SmartphoneModel, String> manufacturer;
    @FXML private TableColumn<SmartphoneModel, String> standard;
    @FXML private TableColumn<SmartphoneModel, String> os;
    @FXML private TableColumn<SmartphoneModel, String> enclosure_type;
    @FXML private TableColumn<SmartphoneModel, String> enclosure_material;
    @FXML private TableColumn<SmartphoneModel, String> sim_type;
    @FXML private TableColumn<SmartphoneModel, String> screen_type;
    @FXML private TableColumn<SmartphoneModel, String> battery_type;
    @FXML private TableColumn<SmartphoneModel, String> memory_card_type;
    @FXML private TableColumn<SmartphoneModel, String> processor;
    @FXML
    @SuppressWarnings("unchecked")
    protected void onMouseClickAdd(MouseEvent event) {
        openDataForm(client.dataform.Controller.FormType.Add, event);
    }
    @FXML protected void onMouseClickEdit(MouseEvent event) {
        System.out.println("You clicked Edit button");
    }
    @FXML protected void onMouseClickDelete(MouseEvent event) {

        table.getSelectionModel().getSelectedItem().getId();
    }
    @FXML
    protected void onMouseClickSearch(MouseEvent event) {
        openDataForm(client.dataform.Controller.FormType.Search, event);
    }
    @FXML
    protected void onMouseClickRefresh(MouseEvent event) {
        if (onRefreshButtonClickListener != null) {
            onRefreshButtonClickListener.onRefreshButtonClicked();
        }
        clearTable();
        getAll();
    }
    @FXML
    public void initialize() {
        manufacturer.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getManufacturer() != null) {
                return new SimpleStringProperty(param.getValue().getManufacturer().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        standard.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getStandard() != null) {
                return new SimpleStringProperty(param.getValue().getStandard().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        os.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getOS() != null) {
                return new SimpleStringProperty(param.getValue().getOS().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        enclosure_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getEnclosureType() != null) {
                return new SimpleStringProperty(param.getValue().getEnclosureType().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        enclosure_material.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getEnclosureMaterial() != null) {
                return new SimpleStringProperty(param.getValue().getEnclosureMaterial().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        sim_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getSimCardType() != null) {
                return new SimpleStringProperty(param.getValue().getSimCardType().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        screen_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getScreenType() != null) {
                return new SimpleStringProperty(param.getValue().getScreenType().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        battery_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getBatteryType() != null) {
                return new SimpleStringProperty(param.getValue().getBatteryType().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        memory_card_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getMemoryCardType() != null) {
                return new SimpleStringProperty(param.getValue().getMemoryCardType().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        processor.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getProcessor() != null) {
                return new SimpleStringProperty(param.getValue().getProcessor().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
    }

    void setOutputStream(final ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    void setInputStream(final ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    void setOnRefreshButtonClickListener(OnRefreshButtonClickListener listener) {
        onRefreshButtonClickListener = listener;
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

    void getAll() {
        try {
            ServerQuery query = ServerQuery.create("model", "get", null, null);

            if (outputStream != null) {
                outputStream.writeObject(query);

                if (inputStream != null) {
                    ServerResult result = (ServerResult) inputStream.readObject();

                    if (result != null && result.getResult() == 0) {
                        for (SmartphoneModel smartphone :
                                (List<SmartphoneModel>) result.getObjects()) {
                            if (data != null) {
                                data.add(smartphone);
                            } else {
                                data = FXCollections.observableArrayList(smartphone);

                                table.setItems(data);
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
        } catch (IOException | ClassNotFoundException e) {
            Alerts.showSomeExceptionAlert(e);
        }
    }

    private void clearTable() {
        if (data != null) {
            data.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Помилка");
            alert.setHeaderText("Очєрєдна тарабарщина");
            alert.setContentText("Ви і так не поймете... Сумніваєтеся? Гаразд, натє:\n" +
                    "ObservableList == null, ObservableList.clear() cannot be called!");
            alert.showAndWait();
        }
    }

    private void openDataForm(final client.dataform.Controller.FormType formType, final MouseEvent event, final SmartphoneModel... model) {
        String title;
        int width = 450;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/dataform/form_data.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            client.dataform.Controller c = loader.getController();

            c.setOutputStream(outputStream);
            c.setInputStream(inputStream);

            switch (formType) {
                case Add:
                    title = "Додати модель";
                    ((client.dataform.Controller) loader.getController()).setOnOKButtonClickListener(() -> {
                        try {
                            outputStream.writeObject(ServerQuery.create("model", "add", c.getModelToProcess(), null));

                            ServerResult result = (ServerResult) inputStream.readObject();

                            if (result != null) {
                                if (result.getResult() == 0) {
                                    data.add(c.getModelToProcess());
                                } else {
                                    System.err.println("Result is not 0, message: " + result.getMessage());
                                }
                            } else {
                                System.err.println("RESULT IS null");
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
//                        onMouseClickRefresh(event);
                    });
                    break;
                case Edit:
                    title = "Редагувати модель";
                    break;
                case Search:
                    title = "Пошук моделей";
                    width += 50;
                    break;
                default:
                    title = "Unknown";
                    break;
            }
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event1 -> parentStage.setIconified(false));
            stage.setMinWidth(width);
            stage.setMinHeight(width);
            stage.setMaxWidth(width);
            stage.setMaxHeight(1360);
            stage.initStyle(StageStyle.UTILITY);
            ((client.dataform.Controller) loader.getController()).setFormType(formType);
            stage.show();
            parentStage.setIconified(true);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Помилка");
            alert.setHeaderText("ЕКСЕПШОН!!1");
            alert.setContentText("Ввід-вивід знову гонить.");
            e.printStackTrace();
            alert.showAndWait();
        }
    }
}
