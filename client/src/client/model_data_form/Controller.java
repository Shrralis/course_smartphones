package client.model_data_form;

import client.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by shrralis on 2/23/17.
 */
public class Controller {
    public enum FormType {
        Add,
        Edit,
        Search
    }

    public interface OnOkButtonClickListener {
        void onButtonOkClick();
    }

    private OnOkButtonClickListener onOkButtonClickListener = null;
    private int iIsAnythingSelectedForSearch = 0;
    private SmartphoneModel modelToProcess = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    @FXML private ComboBox<Manufacturer> manufacturer;
    @FXML private TextField model;
    @FXML private ComboBox<Standard> standard;
    @FXML private ComboBox<OS> os;
    @FXML private TextField osVersion;
    @FXML private ComboBox<EnclosureType> enclosureType;
    @FXML private ComboBox<EnclosureMaterial> enclosureMaterial;
    @FXML private TextField simAmount;
    @FXML private ComboBox<SimCardType> simCardType;
    @FXML private TextField thickness;
    @FXML private TextField weight;
    @FXML private ComboBox<String> color;
    @FXML private ComboBox<ScreenType> screenType;
    @FXML private TextField screenDiagonal;
    @FXML private TextField screenResolution;
    @FXML private ComboBox<BatteryType> batteryType;
    @FXML private TextField batteryCapacity;
    @FXML private TextField ram;
    @FXML private TextField internalStorage;
    @FXML private ComboBox<MemoryCardType> memoryCardType;
    @FXML private ComboBox<Processor> processor;
    @FXML private TextField wifi;
    @FXML private CheckBox nfc;
    @FXML private TextField bluetooth;
    @FXML private TextField camera;
    @FXML private TextField frontalCamera;
    @FXML private CheckBox bManufacturer;
    @FXML private CheckBox bModel;
    @FXML private CheckBox bStandard;
    @FXML private CheckBox bOs;
    @FXML private CheckBox bOsVersion;
    @FXML private CheckBox bEnclosureType;
    @FXML private CheckBox bEnclosureMaterial;
    @FXML private CheckBox bSimAmount;
    @FXML private CheckBox bSimType;
    @FXML private CheckBox bThickness;
    @FXML private CheckBox bWeight;
    @FXML private CheckBox bColor;
    @FXML private CheckBox bScreenType;
    @FXML private CheckBox bScreenDiagonal;
    @FXML private CheckBox bScreenResolution;
    @FXML private CheckBox bBatteryType;
    @FXML private CheckBox bBatteryCapacity;
    @FXML private CheckBox bRam;
    @FXML private CheckBox bInternalStorage;
    @FXML private CheckBox bMemoryCardType;
    @FXML private CheckBox bProcessor;
    @FXML private CheckBox bWifi;
    @FXML private CheckBox bNfc;
    @FXML private CheckBox bBluetooth;
    @FXML private CheckBox bCamera;
    @FXML private CheckBox bFrontalCamera;
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
    @FXML
    protected void onHelpButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Довідка");
        alert.setHeaderText("Додаткова інформація");
        alert.setContentText("Для видалення вторичних даних (тих, що у випадаючих списках), " +
                "оберіть те, що бажаєте видалити, затисніть клавішу Shift й натисніть на випадаючому списку.");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(model.getScene().getWindow());
        alert.showAndWait();
    }
    @FXML
    protected void onCheckBoxSelect(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();

        if (checkBox.isSelected()) {
            iIsAnythingSelectedForSearch++;
        } else {
            if (iIsAnythingSelectedForSearch > 0) {
                iIsAnythingSelectedForSearch--;
            }
        }

        if (checkBox == bManufacturer) {
            manufacturer.setDisable(!checkBox.isSelected());
        } else if (checkBox == bModel) {
            model.setDisable(!checkBox.isSelected());
        } else if (checkBox == bStandard) {
            standard.setDisable(!checkBox.isSelected());
        } else if (checkBox == bOs) {
            os.setDisable(!checkBox.isSelected());
        } else if (checkBox == bOsVersion) {
            osVersion.setDisable(!checkBox.isSelected());
        } else if (checkBox == bEnclosureType) {
            enclosureType.setDisable(!checkBox.isSelected());
        } else if (checkBox == bEnclosureMaterial) {
            enclosureMaterial.setDisable(!checkBox.isSelected());
        } else if (checkBox == bSimAmount) {
            simAmount.setDisable(!checkBox.isSelected());
        } else if (checkBox == bSimType) {
            simCardType.setDisable(!checkBox.isSelected());
        } else if (checkBox == bThickness) {
            thickness.setDisable(!checkBox.isSelected());
        } else if (checkBox == bWeight) {
            weight.setDisable(!checkBox.isSelected());
        } else if (checkBox == bColor) {
            color.setDisable(!checkBox.isSelected());
        } else if (checkBox == bScreenType) {
            screenType.setDisable(!checkBox.isSelected());
        } else if (checkBox == bScreenDiagonal) {
            screenDiagonal.setDisable(!checkBox.isSelected());
        } else if (checkBox == bScreenResolution) {
            screenResolution.setDisable(!checkBox.isSelected());
        } else if (checkBox == bBatteryType) {
            batteryType.setDisable(!checkBox.isSelected());
        } else if (checkBox == bBatteryCapacity) {
            batteryCapacity.setDisable(!checkBox.isSelected());
        } else if (checkBox == bRam) {
            ram.setDisable(!checkBox.isSelected());
        } else if (checkBox == bInternalStorage) {
            internalStorage.setDisable(!checkBox.isSelected());
        } else if (checkBox == bMemoryCardType) {
            memoryCardType.setDisable(!checkBox.isSelected());
        } else if (checkBox == bProcessor) {
            processor.setDisable(!checkBox.isSelected());
        } else if (checkBox == bWifi) {
            wifi.setDisable(!checkBox.isSelected());
        } else if (checkBox == bNfc) {
            nfc.setDisable(!checkBox.isSelected());
        } else if (checkBox == bBluetooth) {
            bluetooth.setDisable(!checkBox.isSelected());
        } else if (checkBox == bCamera) {
            camera.setDisable(!checkBox.isSelected());
        } else if (checkBox == bFrontalCamera) {
            frontalCamera.setDisable(!checkBox.isSelected());
        }
    }
    @SuppressWarnings("unchecked")
    @FXML
    public void initialize() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.getType().getSimpleName().equals("ComboBox") && !field.getName().equals("color")) {
                try {
                    ComboBox comboBox = (ComboBox) field.get(this);

                    comboBox.setOnMouseClicked(event -> {
                        if (event.isShiftDown()) {
                            Owner value = (Owner) comboBox.getSelectionModel().getSelectedItem();

                            if (value != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                                alert.setTitle("Видалення");
                                alert.setHeaderText("Видалення даних");
                                alert.setContentText("Ви дійсно хочете видалити " + value.getName() + "?");
                                alert.initModality(Modality.WINDOW_MODAL);
                                alert.initOwner(comboBox.getScene().getWindow());

                                Optional<ButtonType> answer = alert.showAndWait();

                                if (answer.get() == ButtonType.OK) {
                                    try {
                                        outputStream.writeObject(ServerQuery.create(value.getClass().getSimpleName(),
                                                "delete", value, null));

                                        ServerResult result = (ServerResult) inputStream.readObject();

                                        if (result.getResult() == 0) {
                                            comboBox.getItems().remove(value);
                                            comboBox.getSelectionModel().clearSelection();
                                        }
                                    } catch (IOException | ClassNotFoundException ignored) {}
                                }
                            }
                        }
                    });
                } catch (IllegalAccessException ignored) {}
            }
        }
    }

    public void setModelToProcess(SmartphoneModel modelToProcess) {
        this.modelToProcess = modelToProcess;
    }

    public SmartphoneModel getModelToProcess() {
        return modelToProcess;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;

        setSomething(Manufacturer.class);
        setSomething(Standard.class);
        setSomething(OS.class);
        setSomething(EnclosureType.class);
        setSomething(EnclosureMaterial.class);
        setSomething(SimCardType.class);
        setColors();
        setSomething(ScreenType.class);
        setSomething(BatteryType.class);
        setSomething(MemoryCardType.class);
        setSomething(Processor.class);
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
                    addObjForCreate(clazz);
                    return;
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            Alerts.showSomeExceptionAlert(e);
        }
    }
    @SuppressWarnings("unchecked")
    private <T extends Owner> void addObjForCreate(Class<T> clazz) {
        try {
            String fieldName = clazz.getSimpleName().toLowerCase();

            for (Field field :
                    this.getClass().getDeclaredFields()) {
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    T objForCreating = clazz.newInstance();
                    objForCreating.name = "створити...";
                    ComboBox comboBox = ((ComboBox) field.get(this));

                    comboBox.getItems().add(objForCreating);
                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null && ((T) newValue).getName() != null) {
                            if (((T) newValue).getName().equalsIgnoreCase("створити...")) {
                                if (fieldName.matches("(p|P)rocessor")) {
                                    openProcessorAddForm((Processor) newValue);
                                } else if (fieldName.matches("(m|M)anufacturer")) {
                                    openManufacturerAddForm((Manufacturer) newValue);
                                } else {
                                    openSecondaryDataAddForm((T) newValue);
                                }
                                addObjForCreate(clazz);
                            }
                        }
                    });
                    return;
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setColors() {
        ObservableList<String> colors = FXCollections.observableArrayList();

        colors.addAll("білий", "чорний", "жовтий", "зелений", "коричневий", "червоний", "сірий", "голубий", "фіолетовий");
        color.setItems(colors);
    }

    public void setFormType(FormType formType) {
        final OnOkButtonClickListener currentListener = onOkButtonClickListener;

        if (formType == FormType.Search) {
            setAllFieldsDisabled();

            onOkButtonClickListener = () -> {
                if (isFieldsValidForSearch()) {
                    if (currentListener != null) {
                        currentListener.onButtonOkClick();
                    }
                }
            };
        } else {
            onOkButtonClickListener = () -> {
                if (isFieldsValidForAdd()) {
                    setupModel();

                    if (currentListener != null) {
                        currentListener.onButtonOkClick();
                    }
                }
            };

            if (formType == FormType.Edit) {
                setupFormBasedOnModel();
            }
        }
    }

    public void setOnOkButtonClickListener(OnOkButtonClickListener listener) {
        onOkButtonClickListener = listener;
    }

    private void setAllFieldsDisabled() {
        setFieldDisabled(bManufacturer);
        setFieldDisabled(bModel);
        setFieldDisabled(bStandard);
        setFieldDisabled(bOs);
        setFieldDisabled(bOsVersion);
        setFieldDisabled(bEnclosureType);
        setFieldDisabled(bEnclosureMaterial);
        setFieldDisabled(bSimAmount);
        setFieldDisabled(bSimType);
        setFieldDisabled(bThickness);
        setFieldDisabled(bWeight);
        setFieldDisabled(bColor);
        setFieldDisabled(bScreenType);
        setFieldDisabled(bScreenDiagonal);
        setFieldDisabled(bScreenResolution);
        setFieldDisabled(bBatteryType);
        setFieldDisabled(bBatteryCapacity);
        setFieldDisabled(bRam);
        setFieldDisabled(bInternalStorage);
        setFieldDisabled(bMemoryCardType);
        setFieldDisabled(bProcessor);
        setFieldDisabled(bWifi);
        setFieldDisabled(bNfc);
        setFieldDisabled(bBluetooth);
        setFieldDisabled(bCamera);
        setFieldDisabled(bFrontalCamera);
    }

    private void setFieldDisabled(CheckBox field) {
        field.setSelected(false);
        onCheckBoxSelect(new ActionEvent(field, null));
    }

    private boolean isFieldsValidForSearch() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Помилка");
        alert.setHeaderText("Уважніше!");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(wifi.getScene().getWindow());

        if (iIsAnythingSelectedForSearch == 0) {
            alert.setContentText("Ви не обрали жодного поля для пошуку!");
            alert.showAndWait();
            return false;
        }

        if (bManufacturer.isSelected() && manufacturer.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по виробнику, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bModel.isSelected() && model.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви бажаєте провести пошук по назві моделі, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bStandard.isSelected() && standard.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по стандарту зв'язку, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bOs.isSelected() && os.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по операційній системі, але не обрали жодну з них!");
            alert.showAndWait();
            return false;
        }

        if (bOsVersion.isSelected() && osVersion.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви бажаєте провести пошук по версії ОС, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bEnclosureType.isSelected() && enclosureType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по типу корпусу, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bEnclosureMaterial.isSelected() && enclosureMaterial.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по матеріалу корпусу, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bSimAmount.isSelected() && !simAmount.getText().trim().matches("[0-9]+")) {
            alert.setContentText("Невірний формат кількості SIM-карток або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bSimType.isSelected() && simCardType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по типу SIM-картки, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bThickness.isSelected() && !thickness.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Невірний формат товщини або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.showAndWait();
            return false;
        }

        if (bWeight.isSelected() && !weight.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Невірний формат ваги або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.showAndWait();
            return false;
        }

        if (bColor.isSelected() && color.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по кольору, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bScreenType.isSelected() && screenType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по типу екрану, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bScreenDiagonal.isSelected() && !screenDiagonal.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Невірний формат діагоналі або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.showAndWait();
            return false;
        }

        if (bScreenResolution.isSelected() && screenResolution.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви бажаєте провести пошук по роздільній здатності екрану моделі, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bBatteryType.isSelected() && batteryType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по типу акамулятора, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bBatteryCapacity.isSelected() && !batteryCapacity.getText().trim().matches("[0-9]+")) {
            alert.setContentText("Невірний формат ємності акумулятора або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bRam.isSelected() && !ram.getText().trim().matches("[0-9]+")) {
            alert.setContentText("Невірний формат кількості оперативної пам'яті або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bInternalStorage.isSelected() && !internalStorage.getText().trim().matches("[0-9]+")) {
            alert.setContentText("Невірний формат внутрішньої пам'яті або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bMemoryCardType.isSelected() && memoryCardType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по типу карти пам'яті, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bProcessor.isSelected() && processor.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви бажаєте провести пошук по назві процесора, але не обрали жодну з них!");
            alert.showAndWait();
            return false;
        }

        if (bWifi.isSelected() && wifi.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви бажаєте провести пошук по Wi-Fi, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bBluetooth.isSelected() && bluetooth.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви бажаєте провести пошук по Bluetooth, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bCamera.isSelected() && camera.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви бажаєте провести пошук по основній камері, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bFrontalCamera.isSelected() && frontalCamera.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви бажаєте провести пошук по передній камері, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean isFieldsValidForAdd() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Помилка");
        alert.setHeaderText("Помилковий ввід");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(wifi.getScene().getWindow());

        if (manufacturer.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали виробника!");
            alert.showAndWait();
            return false;
        }

        if (model.getText().trim().equalsIgnoreCase("")) {
            alert.setContentText("Ви не ввели назву моделі!");
            alert.showAndWait();
            return false;
        }

        if (standard.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали стандарт зв'язку!");
            alert.showAndWait();
            return false;
        }

        if (os.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали операційну систему смартфона!");
            alert.showAndWait();
            return false;
        }

        if (enclosureType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали тип корпусу!");
            alert.showAndWait();
            return false;
        }

        if (enclosureMaterial.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали матеріал корпусу!");
            alert.showAndWait();
            return false;
        }

        if (simCardType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали тип SIM-картки смартфону!");
            alert.showAndWait();
            return false;
        }

        if (!thickness.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Ви не ввели товщину або формат невірний! (Формат: 1.09)");
            alert.showAndWait();
            return false;
        }

        if (!weight.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Ви не ввели вагу або формат невірний! (Формат: 1.09)");
            alert.showAndWait();
            return false;
        }

        if (screenType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали тип матриці екрану!");
            alert.showAndWait();
            return false;
        }

        if (!screenDiagonal.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            alert.setContentText("Ви не ввели діагональ екрану або формат невірний! (Формат: 1.09)");
            alert.showAndWait();
            return false;
        }

        if (batteryType.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали тип акумуляторної батареї!");
            alert.showAndWait();
            return false;
        }

        if (processor.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Ви не обрали процесор!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void setupModel() {
        final SmartphoneModel oldModel = modelToProcess;
        modelToProcess = new SmartphoneModel();

        if (oldModel != null) {
            modelToProcess.id = oldModel.getId();
        }

        try {
            if (!simAmount.getText().trim().equals("")) {
                modelToProcess.sim_card_amount = Integer.parseInt(simAmount.getText().trim());
            }

            if (!thickness.getText().trim().equals("")) {
                modelToProcess.thickness = Double.parseDouble(thickness.getText().trim());
            }

            if (!weight.getText().trim().equals("")) {
                modelToProcess.weight = Double.parseDouble(weight.getText().trim());
            }

            if (!screenDiagonal.getText().trim().equals("")) {
                modelToProcess.screen_diagonal = Double.parseDouble(screenDiagonal.getText().trim());
            }

            if (!batteryCapacity.getText().trim().equals("")) {
                modelToProcess.battery_capacity = Integer.parseInt(batteryCapacity.getText().trim());
            }

            if (!ram.getText().trim().equals("")) {
                modelToProcess.ram = Integer.parseInt(ram.getText().trim());
            }

            if (!internalStorage.getText().trim().equals("")) {
                modelToProcess.internal_storage = Integer.parseInt(internalStorage.getText().trim());
            }
        } catch (NumberFormatException ignored) {
            ignored.printStackTrace();
        } finally {
            modelToProcess.manufacturer = manufacturer.getValue();
            modelToProcess.name = model.getText().trim();
            modelToProcess.standard = standard.getValue();
            modelToProcess.os = os.getValue();
            modelToProcess.os_version = osVersion.getText().trim();
            modelToProcess.enclosure_type = enclosureType.getValue();
            modelToProcess.enclosure_material = enclosureMaterial.getValue();
            modelToProcess.sim_card_type = simCardType.getValue();
            modelToProcess.color = color.getSelectionModel().getSelectedItem().trim();
            modelToProcess.screen_type = screenType.getValue();
            modelToProcess.screen_resolution = screenResolution.getText().trim();
            modelToProcess.battery_type = batteryType.getValue();
            modelToProcess.memory_card_type = memoryCardType.getValue();
            modelToProcess.processor = processor.getValue();
            modelToProcess.wifi = wifi.getText().trim();
            modelToProcess.nfc = nfc.isSelected();
            modelToProcess.bluetooth = bluetooth.getText().trim();
            modelToProcess.camera = camera.getText().trim();
            modelToProcess.frontal_camera = frontalCamera.getText().trim();
        }
    }

    public HashMap<String, Object> getParamsForSearch() throws IllegalAccessException {
        HashMap<String, Object> paramsForSearch = new HashMap<>();

        for (Field field : getClass().getDeclaredFields()) {
            if (field.getName().matches("^b(\\s|\\S)*$")) {
                if (field.getType() == CheckBox.class) {
                    if (((CheckBox) field.get(this)).isSelected()) {
                        String neededFieldName = field.getName().substring(1).toLowerCase();

                        for (Field field2 : getClass().getDeclaredFields()) {
                            if (field2.getName().equalsIgnoreCase(neededFieldName)) {
                                if (field2.getType() == ComboBox.class && !field2.getName().equalsIgnoreCase("color")
                                        && !field2.getName().equalsIgnoreCase("model")) {
                                    System.out.println(neededFieldName + " = " + ((Owner) ((ComboBox) field2.get(this)).getSelectionModel().getSelectedItem()).getId());
                                    paramsForSearch.put(neededFieldName, ((Owner) ((ComboBox) field2.get(this)).getSelectionModel().getSelectedItem()).getId());
                                } else if (neededFieldName.equalsIgnoreCase("color")) {
                                    System.out.println(neededFieldName + " = " + ((ComboBox) field2.get(this)).getSelectionModel().getSelectedItem());
                                    paramsForSearch.put(neededFieldName, ((ComboBox) field2.get(this)).getSelectionModel().getSelectedItem());
                                } else {
                                    if (neededFieldName.equalsIgnoreCase("model")) {
                                        neededFieldName = "name";
                                    }
                                    System.out.println(neededFieldName + " = " + ((TextField) field2.get(this)).getText());
                                    paramsForSearch.put(neededFieldName, ((TextField) field2.get(this)).getText());
                                }
                            }
                        }
                    }
                }
            }
        }
        return paramsForSearch;
    }

    private void setupFormBasedOnModel() {
        simAmount.setText(modelToProcess.getSim_card_amount() + "");
        thickness.setText(modelToProcess.getThickness() + "");
        weight.setText(modelToProcess.getWeight() + "");
        screenDiagonal.setText(modelToProcess.getScreen_diagonal() + "");
        batteryCapacity.setText(modelToProcess.getBattery_capacity() + "");
        ram.setText(modelToProcess.getRam() + "");
        internalStorage.setText(modelToProcess.getInternal_storage() + "");
        manufacturer.getSelectionModel().select(modelToProcess.getManufacturer());
        model.setText(modelToProcess.getName());
        standard.getSelectionModel().select(modelToProcess.getStandard());
        os.getSelectionModel().select(modelToProcess.getOs());
        osVersion.setText(modelToProcess.getOs_version());
        enclosureType.getSelectionModel().select(modelToProcess.getEnclosure_type());
        enclosureMaterial.getSelectionModel().select(modelToProcess.getEnclosure_material());
        simCardType.getSelectionModel().select(modelToProcess.getSim_card_type());
        color.getSelectionModel().select(modelToProcess.getColor());
        screenType.getSelectionModel().select(modelToProcess.getScreen_type());
        screenResolution.setText(modelToProcess.getScreen_resolution());
        batteryType.getSelectionModel().select(modelToProcess.getBattery_type());
        memoryCardType.getSelectionModel().select(modelToProcess.getMemory_card_type());
        processor.getSelectionModel().select(modelToProcess.getProcessor());
        wifi.setText(modelToProcess.getWifi());
        nfc.setSelected(modelToProcess.isNfc());
        bluetooth.setText(modelToProcess.getBluetooth());
        camera.setText(modelToProcess.getCamera());
        frontalCamera.setText(modelToProcess.getFrontal_camera());
    }

    private void openProcessorAddForm(Processor resultTo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/processor_data_form/form_data.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Stage parentStage = (Stage) wifi.getScene().getWindow();
            client.processor_data_form.Controller c = loader.getController();

            c.setOnOkButtonClickListener(() -> {
                if (c.isFieldsValidForAdd()) {
                    try {
                        outputStream.writeObject(ServerQuery.create("processor", "add", c.getProcessor(), null));

                        ServerResult result = (ServerResult) inputStream.readObject();

                        if (result != null) {
                            if (result.getResult() == 0) {
                                resultTo.id = ((Processor) result.getObjects().get(0)).id;
                                resultTo.name = ((Processor) result.getObjects().get(0)).name;
                                resultTo.cores = ((Processor) result.getObjects().get(0)).cores;
                                resultTo.frequency = ((Processor) result.getObjects().get(0)).frequency;

                                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                            } else {
                                System.err.println("Result is not 0, message: " + result.getMessage());
                            }
                        } else {
                            System.err.println("RESULT IS null");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            stage.setTitle("Додати процесор");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> parentStage.setIconified(false));
            stage.setMinWidth(300);
            stage.setMinHeight(200);
            stage.setMaxWidth(400);
            stage.setMaxHeight(200);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

            if (resultTo.getName().equalsIgnoreCase("створити...")) {
                processor.getItems().remove(resultTo);
                processor.getSelectionModel().clearSelection();
            }
        } catch (IOException ignored) {}
    }

    private void openManufacturerAddForm(Manufacturer resultTo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/manufacturer_data_form/form_data.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Stage parentStage = (Stage) wifi.getScene().getWindow();
            client.manufacturer_data_form.Controller c = loader.getController();

            c.setOnOkButtonClickListener(() -> {
                if (c.isFieldsValidForAdd()) {
                    try {
                        outputStream.writeObject(ServerQuery.create("manufacturer", "add", c.getManufacturer(), null));

                        ServerResult result = (ServerResult) inputStream.readObject();

                        if (result != null) {
                            if (result.getResult() == 0) {
                                resultTo.id = ((Manufacturer) result.getObjects().get(0)).id;
                                resultTo.name = ((Manufacturer) result.getObjects().get(0)).name;
                                resultTo.country = ((Manufacturer) result.getObjects().get(0)).country;

                                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                            } else {
                                System.err.println("Result is not 0, message: " + result.getMessage());
                            }
                        } else {
                            System.err.println("RESULT IS null");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            stage.setTitle("Додати виробника");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> parentStage.setIconified(false));
            stage.setMinWidth(300);
            stage.setMinHeight(144);
            stage.setMaxWidth(400);
            stage.setMaxHeight(144);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

            if (resultTo.getName().equalsIgnoreCase("створити...")) {
                manufacturer.getItems().remove(resultTo);
                manufacturer.getSelectionModel().clearSelection();
            }
        } catch (IOException ignored) {}
    }
    @SuppressWarnings("unchecked")
    private <T extends Owner> void openSecondaryDataAddForm(T resultTo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/secondary_data_form/form_data.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Stage parentStage = (Stage) wifi.getScene().getWindow();
            client.secondary_data_form.Controller c = loader.getController();

            c.setOnOkButtonClickListener(() -> {
                if (c.isFieldsValidForAdd(resultTo.getClass())) {
                    try {
                        outputStream.writeObject(ServerQuery.create(resultTo.getClass().getSimpleName(), "add",
                                c.getObjectToAdd(), null));

                        ServerResult result = (ServerResult) inputStream.readObject();

                        if (result != null) {
                            if (result.getResult() == 0) {
                                resultTo.id = result.getObjects().get(0).id;
                                resultTo.name = result.getObjects().get(0).name;

                                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                            } else {
                                System.err.println("Result is not 0, message: " + result.getMessage());
                            }
                        } else {
                            System.err.println("RESULT IS null");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            stage.setTitle("Додати вторичні дані");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> parentStage.setIconified(false));
            stage.setMinWidth(300);
            stage.setMinHeight(105);
            stage.setMaxWidth(400);
            stage.setMaxHeight(105);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

            try {
                for (Field field : getClass().getDeclaredFields()) {
                    if (field.getName().equalsIgnoreCase(resultTo.getClass().getSimpleName())) {
                        if (resultTo.getName().equalsIgnoreCase("створити...")) {
                            ((ComboBox) field.get(this)).getItems().remove(resultTo);
                            ((ComboBox) field.get(this)).getSelectionModel().clearSelection();
                        } else {
                            System.out.println("ololo");
                        }
                        return;
                    }
                }
            } catch (IllegalAccessException ignored) {}
        } catch (IOException ignored) {}
    }
}
