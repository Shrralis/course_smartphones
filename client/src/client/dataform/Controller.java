package client.dataform;

import client.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

/**
 * Created by shrralis on 2/23/17.
 */
public class Controller {
    public enum FormType {
        Add,
        Edit,
        Search
    }

    public interface OnOKButtonClickListener {
        void onButtonOKClicked();
    }

    private FormType formType = FormType.Add;
    private OnOKButtonClickListener onOKButtonClickListener;
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
    protected void onMouseClickedOK(MouseEvent event) {
        if (onOKButtonClickListener != null) {
            onOKButtonClickListener.onButtonOKClicked();
        }
    }
    @FXML
    protected void onMouseClickedCancel(MouseEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @FXML
    protected void onCheckBoxSelected(ActionEvent event) {
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

    public void setModelToProcess(SmartphoneModel modelToProcess) {
        this.modelToProcess = modelToProcess;
    }

    public SmartphoneModel getModelToProcess() {
        return modelToProcess;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;

        setSmth(Manufacturer.class);
        setSmth(Standard.class);
        setSmth(OS.class);
        setSmth(EnclosureType.class);
        setSmth(EnclosureMaterial.class);
        setSmth(SimCardType.class);
        setColors();
        setSmth(ScreenType.class);
        setSmth(BatteryType.class);
        setSmth(MemoryCardType.class);
        setSmth(Processor.class);
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
    @SuppressWarnings("unchecked")
    private <T extends Owner> void setSmth(Class<T> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        ServerQuery query = ServerQuery.create(tableName, "get", null, null);
        ObservableList<T> list = FXCollections.observableArrayList();

        try {
            if (outputStream != null) {
                outputStream.writeObject(query);

                if (inputStream != null) {
                    ServerResult result = (ServerResult) inputStream.readObject();

                    if (result != null && result.getResult() == 0) {
                        for (T obj :
                                (List<T>) result.getObjects()) {
                            list.add(obj);
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

            for (Field field :
                    this.getClass().getDeclaredFields()) {
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

                    ((ComboBox) field.get(this)).getItems().add(objForCreating);
                    ((ComboBox) field.get(this)).valueProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null && ((T) newValue).getName() != null) {
                            if (((T) newValue).getName().equalsIgnoreCase("створити...")) {
                                System.out.println("Here you should add window for adding new secondary data to db.");

                                ((T) newValue).id++;
                                ((T) newValue).name = "нове ім'я";

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
        this.formType = formType;

        if (formType == FormType.Search) {
            setAllFieldsDisabled();

            onOKButtonClickListener = () -> {
                if (isFieldsValidForSearch()) {

                }
            };
        } else if (formType == FormType.Add) {
            final OnOKButtonClickListener currentListener = onOKButtonClickListener;
            onOKButtonClickListener = () -> {
                if (isFieldsValidForAdd()) {
                    setupModel();

                    if (currentListener != null) {
                        currentListener.onButtonOKClicked();
                    }
                }
            };
        } else {
            onOKButtonClickListener = () -> {

            };
        }
    }

    public void setOnOKButtonClickListener(OnOKButtonClickListener listener) {
        onOKButtonClickListener = listener;
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
        onCheckBoxSelected(new ActionEvent(field, null));
    }

    private boolean isFieldsValidForSearch() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Помилка");
        alert.setHeaderText("Уважніше!");

        if (iIsAnythingSelectedForSearch == 0) {
            System.err.println("Ви не обрали жодного поля для пошуку!");
            alert.setContentText("Ви не обрали жодного поля для пошуку!");
            alert.showAndWait();
            return false;
        }

        if (bManufacturer.isSelected() && manufacturer.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по виробнику, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по виробнику, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bModel.isSelected() && model.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви бажаєте провести пошук по назві моделі, але не ввели жодного символу!");
            alert.setContentText("Ви бажаєте провести пошук по назві моделі, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bStandard.isSelected() && standard.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по стандарту зв'язку, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по стандарту зв'язку, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bOs.isSelected() && os.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по операційній системі, але не обрали жодну з них!");
            alert.setContentText("Ви бажаєте провести пошук по операційній системі, але не обрали жодну з них!");
            alert.showAndWait();
            return false;
        }

        if (bOsVersion.isSelected() && osVersion.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви бажаєте провести пошук по версії ОС, але не ввели жодного символу!");
            alert.setContentText("Ви бажаєте провести пошук по версії ОС, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bEnclosureType.isSelected() && enclosureType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по типу корпусу, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по типу корпусу, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bEnclosureMaterial.isSelected() && enclosureMaterial.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по матеріалу корпусу, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по матеріалу корпусу, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bSimAmount.isSelected() && !simAmount.getText().trim().matches("[0-9]+")) {
            System.err.println("Невірний формат кількості SIM-карток або пусте поле!\nПоле повинно містити лише цифри!");
            alert.setContentText("Невірний формат кількості SIM-карток або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bSimType.isSelected() && simCardType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по типу SIM-картки, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по типу SIM-картки, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bThickness.isSelected() && !thickness.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            System.err.println("Невірний формат товщини або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.setContentText("Невірний формат товщини або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.showAndWait();
            return false;
        }

        if (bWeight.isSelected() && !weight.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            System.err.println("Невірний формат ваги або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.setContentText("Невірний формат ваги або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.showAndWait();
            return false;
        }

        if (bColor.isSelected() && color.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по кольору, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по кольору, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bScreenType.isSelected() && screenType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по типу екрану, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по типу екрану, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bScreenDiagonal.isSelected() && !screenDiagonal.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            System.err.println("Невірний формат діагоналі або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.setContentText("Невірний формат діагоналі або пусте поле!\nПоле повинно містити ціле або дробове число (приклад: 0.3)!");
            alert.showAndWait();
            return false;
        }

        if (bScreenResolution.isSelected() && screenResolution.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви бажаєте провести пошук по роздільній здатності екрану моделі, але не ввели жодного символу!");
            alert.setContentText("Ви бажаєте провести пошук по роздільній здатності екрану моделі, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bBatteryType.isSelected() && batteryType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по типу акамулятора, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по типу акамулятора, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bBatteryCapacity.isSelected() && !batteryCapacity.getText().trim().matches("[0-9]+")) {
            System.err.println("Невірний формат ємності акумулятора або пусте поле!\nПоле повинно містити лише цифри!");
            alert.setContentText("Невірний формат ємності акумулятора або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bRam.isSelected() && !ram.getText().trim().matches("[0-9]+")) {
            System.err.println("Невірний формат кількості оперативної пам'яті або пусте поле!\nПоле повинно містити лише цифри!");
            alert.setContentText("Невірний формат кількості оперативної пам'яті або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bInternalStorage.isSelected() && !internalStorage.getText().trim().matches("[0-9]+")) {
            System.err.println("Невірний формат внутрішньої пам'яті або пусте поле!\nПоле повинно містити лише цифри!");
            alert.setContentText("Невірний формат внутрішньої пам'яті або пусте поле!\nПоле повинно містити лише цифри!");
            alert.showAndWait();
            return false;
        }

        if (bMemoryCardType.isSelected() && memoryCardType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по типу карти пам'яті, але не обрали жодного з них!");
            alert.setContentText("Ви бажаєте провести пошук по типу карти пам'яті, але не обрали жодного з них!");
            alert.showAndWait();
            return false;
        }

        if (bProcessor.isSelected() && processor.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви бажаєте провести пошук по назві процесора, але не обрали жодну з них!");
            alert.setContentText("Ви бажаєте провести пошук по назві процесора, але не обрали жодну з них!");
            alert.showAndWait();
            return false;
        }

        if (bWifi.isSelected() && wifi.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви бажаєте провести пошук по Wi-Fi, але не ввели жодного символу!");
            alert.setContentText("Ви бажаєте провести пошук по Wi-Fi, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bBluetooth.isSelected() && bluetooth.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви бажаєте провести пошук по Bluetooth, але не ввели жодного символу!");
            alert.setContentText("Ви бажаєте провести пошук по Bluetooth, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bCamera.isSelected() && camera.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви бажаєте провести пошук по основній камері, але не ввели жодного символу!");
            alert.setContentText("Ви бажаєте провести пошук по основній камері, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }

        if (bFrontalCamera.isSelected() && frontalCamera.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви бажаєте провести пошук по передній камері, але не ввели жодного символу!");
            alert.setContentText("Ви бажаєте провести пошук по передній камері, але не ввели жодного символу!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean isFieldsValidForAdd() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Помилка");
        alert.setHeaderText("З'єднання з сервером неможливе");

        if (manufacturer.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали виробника!");
            alert.setContentText("Ви не обрали виробника!");
            alert.showAndWait();
            return false;
        }

        if (model.getText().trim().equalsIgnoreCase("")) {
            System.err.println("Ви не ввели назву моделі!");
            alert.setContentText("Ви не ввели назву моделі!");
            alert.showAndWait();
            return false;
        }

        if (standard.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали стандарт зв'язку!");
            alert.setContentText("Ви не обрали стандарт зв'язку!");
            alert.showAndWait();
            return false;
        }

        if (os.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали операційну систему смартфона!");
            alert.setContentText("Ви не обрали операційну систему смартфона!");
            alert.showAndWait();
            return false;
        }

        if (enclosureType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали тип корпусу!");
            alert.setContentText("Ви не обрали тип корпусу!");
            alert.showAndWait();
            return false;
        }

        if (enclosureMaterial.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали матеріал корпусу!");
            alert.setContentText("Ви не обрали матеріал корпусу!");
            alert.showAndWait();
            return false;
        }

        if (simCardType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали тип SIM-картки смартфону!");
            alert.setContentText("Ви не обрали тип SIM-картки смартфону!");
            alert.showAndWait();
            return false;
        }

        if (!thickness.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            System.err.println("Ви не ввели товщину або формат невірний! (Формат: 1.09)");
            alert.setContentText("Ви не ввели товщину або формат невірний! (Формат: 1.09)");
            alert.showAndWait();
            return false;
        }

        if (!weight.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            System.err.println("Ви не ввели вагу або формат невірний! (Формат: 1.09)");
            alert.setContentText("Ви не ввели вагу або формат невірний! (Формат: 1.09)");
            alert.showAndWait();
            return false;
        }

        if (screenType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали тип матриці екрану!");
            alert.setContentText("Ви не обрали тип матриці екрану!");
            alert.showAndWait();
            return false;
        }

        if (!screenDiagonal.getText().trim().matches("[0-9]+(\\.[0-9]+)?")) {
            System.err.println("Ви не ввели діагональ екрану або формат невірний! (Формат: 1.09)");
            alert.setContentText("Ви не ввели діагональ екрану або формат невірний! (Формат: 1.09)");
            alert.showAndWait();
            return false;
        }

        if (batteryType.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали тип акумуляторної батареї!");
            alert.setContentText("Ви не обрали тип акумуляторної батареї!");
            alert.showAndWait();
            return false;
        }

        if (processor.getSelectionModel().getSelectedItem() == null) {
            System.err.println("Ви не обрали процесор!");
            alert.setContentText("Ви не обрали процесор!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void setupModel() {
        modelToProcess.manufacturer = manufacturer.getValue();
        modelToProcess.name = model.getText().trim();
        modelToProcess.standard = standard.getValue();
        modelToProcess.os = os.getValue();
        modelToProcess.os_version = osVersion.getText().trim();
        modelToProcess.enclosure_type = enclosureType.getValue();
        modelToProcess.enclosure_material = enclosureMaterial.getValue();
        modelToProcess.sim_card_amount = Integer.parseInt(simAmount.getText().trim());
        modelToProcess.sim_card_type = simCardType.getValue();
        modelToProcess.thickness = Double.parseDouble(thickness.getText().trim());
        modelToProcess.weight = Double.parseDouble(weight.getText().trim());
        modelToProcess.color = color.getValue().trim();
        modelToProcess.screen_type = screenType.getValue();
        modelToProcess.screen_diagonal = Double.parseDouble(screenDiagonal.getText().trim());
        modelToProcess.screen_resolution = screenResolution.getText().trim();
        modelToProcess.battery_type = batteryType.getValue();
        modelToProcess.battery_capacity = Integer.parseInt(batteryCapacity.getText().trim());
        modelToProcess.ram = Integer.parseInt(ram.getText().trim());
        modelToProcess.internal_storage = Integer.parseInt(internalStorage.getText().trim());
        modelToProcess.memory_card_type = memoryCardType.getValue();
        modelToProcess.processor = processor.getValue();
        modelToProcess.wifi = wifi.getText().trim();
        modelToProcess.nfc = nfc.isSelected();
        modelToProcess.bluetooth = bluetooth.getText().trim();
        modelToProcess.camera = camera.getText().trim();
        modelToProcess.frontal_camera = frontalCamera.getText().trim();
    }
}
