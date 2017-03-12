package client.main_form;

import client.Alerts;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.HashMap;

/**
 * Created by shrralis on 2/21/17.
 */
@SuppressWarnings("unchecked")
public class Controller {
    interface OnRefreshModelsButtonClickListener {
        void onRefreshButtonClicked();
    }
    interface OnRefreshStoresButtonClickListener {
        void onRefreshButtonClicked();
    }

    private OnRefreshModelsButtonClickListener onRefreshModelsButtonClickListener = null;
    private OnRefreshStoresButtonClickListener onRefreshStoresButtonClickListener = null;
    private ObservableList<SmartphoneModel> modelsData = FXCollections.observableArrayList();
    private ObservableList<Store> storesData = FXCollections.observableArrayList();
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    @FXML private TabPane tabs;
    @FXML private TableView<SmartphoneModel> modelTable;
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
    @FXML private TableView<Store> storeTable;
    @FXML private TableColumn<Store, String> price;
    @FXML
    @SuppressWarnings("unchecked")
    protected void onMouseClickAddModel(MouseEvent event) {
        openModelDataForm(client.model_data_form.Controller.FormType.Add, event);
    }
    @FXML protected void onMouseClickEditModel(MouseEvent event) {
        openModelDataForm(client.model_data_form.Controller.FormType.Edit, event, modelTable.getSelectionModel().getSelectedItem());
    }
    @FXML protected void onMouseClickDeleteModel() {
        SmartphoneModel model = modelTable.getSelectionModel().getSelectedItem();

        if (model == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Видалення");
            alert.setHeaderText("Ви не обрали смартфон");
            alert.showAndWait();
            return;
        }

        try {
            outputStream.writeObject(ServerQuery.create("model", "delete", model, null));

            ServerResult result = (ServerResult) inputStream.readObject();

            if (result.getResult() == 0) {
                modelTable.getItems().remove(model);
            }
        } catch (IOException | ClassNotFoundException ignored) {}
    }
    @FXML
    protected void onMouseClickSearchModels(MouseEvent event) {
        openModelDataForm(client.model_data_form.Controller.FormType.Search, event);
    }
    @FXML
    protected void onMouseClickRefreshModels() {
        if (onRefreshModelsButtonClickListener != null) {
            onRefreshModelsButtonClickListener.onRefreshButtonClicked();
        }
        clearModelTable();
        getAllModels();
    }
    @FXML
    @SuppressWarnings("unchecked")
    protected void onMouseClickAddStore(MouseEvent event) {
        openStoreDataForm(client.store_data_form.Controller.FormType.Add, event);
    }
    @FXML protected void onMouseClickEditStore(MouseEvent event) {
        openStoreDataForm(client.store_data_form.Controller.FormType.Edit, event, storeTable.getSelectionModel().getSelectedItem());
    }
    @FXML protected void onMouseClickDeleteStore() {
        Store store = storeTable.getSelectionModel().getSelectedItem();

        if (store == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Видалення");
            alert.setHeaderText("Ви не обрали магазин");
            alert.showAndWait();
            return;
        }

        try {
            outputStream.writeObject(ServerQuery.create("store", "delete", store, null));

            ServerResult result = (ServerResult) inputStream.readObject();

            if (result.getResult() == 0) {
                storeTable.getItems().remove(store);
            }
        } catch (IOException | ClassNotFoundException ignored) {}
    }
    @FXML
    protected void onMouseClickRefreshStores() {
        if (onRefreshStoresButtonClickListener != null) {
            onRefreshStoresButtonClickListener.onRefreshButtonClicked();
        }
        clearStoreTable();
        getAllStores();
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
            if (param.getValue() != null && param.getValue().getOs() != null) {
                return new SimpleStringProperty(param.getValue().getOs().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        enclosure_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getEnclosure_type() != null) {
                return new SimpleStringProperty(param.getValue().getEnclosure_type().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        enclosure_material.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getEnclosure_material() != null) {
                return new SimpleStringProperty(param.getValue().getEnclosure_material().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        sim_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getSim_card_type() != null) {
                return new SimpleStringProperty(param.getValue().getSim_card_type().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        screen_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getScreen_type() != null) {
                return new SimpleStringProperty(param.getValue().getScreen_type().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        battery_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getBattery_type() != null) {
                return new SimpleStringProperty(param.getValue().getBattery_type().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        memory_card_type.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getMemory_card_type() != null) {
                return new SimpleStringProperty(param.getValue().getMemory_card_type().getName());
            } else {
                return new SimpleStringProperty("немає");
            }
        });
        processor.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getProcessor() != null) {
                return new SimpleStringProperty(param.getValue().getProcessor().getName());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        price.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getModelToStore() != null) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getModelToStore().price));
            } else {
                return new SimpleStringProperty("");
            }
        });
        modelTable.setRowFactory(param -> {
            final TableRow<SmartphoneModel> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addToStore = new MenuItem("Додати до магазину");
            final MenuItem findStores = new MenuItem("Знайти магазини");

            addToStore.setOnAction(event -> {
                openModelToStoreDataForm();
            });
            findStores.setOnAction(event -> {
                clearStoreTable();
                getAllStores(row.getItem().getId());
                tabs.getSelectionModel().select(1);
            });
            contextMenu.getItems().add(addToStore);
            contextMenu.getItems().add(findStores);
            // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row ;
        });
    }

    void setOutputStream(final ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    void setInputStream(final ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    void setOnRefreshModelsButtonClickListener(OnRefreshModelsButtonClickListener listener) {
        onRefreshModelsButtonClickListener = listener;
    }

    void setOnRefreshStoresButtonClickListener(OnRefreshStoresButtonClickListener listener) {
        onRefreshStoresButtonClickListener = listener;
    }

    void getAllModels(HashMap<String, Object>... paramsForSearch) {
        try {
            ServerQuery query = ServerQuery.create("model", "get", null, null);

            if (paramsForSearch.length > 0) {
                query.setQueryParameters(paramsForSearch[0]);
            }

            if (outputStream != null) {
                outputStream.writeObject(query);
                System.out.println("Query sent to the server");

                if (inputStream != null) {
                    System.out.println("Waiting for an answer from the server");

                    ServerResult result = (ServerResult) inputStream.readObject();

                    System.out.println("The answer was received");

                    if (result != null && result.getResult() == 0) {
                        System.out.println("The answer is not null and result is 0");
                        System.out.println("The answer has " + result.getObjects().size() + " elements");

                        if (modelsData != null) {
                            modelTable.setItems(modelsData);
                        }

                        for (SmartphoneModel smartphone : (List<SmartphoneModel>) result.getObjects()) {
                            if (modelsData != null) {
                                System.out.println("Adding element... " + smartphone);
                                modelsData.add(smartphone);
                            } else {
                                System.out.println("Data is null, creating with " + smartphone);

                                modelsData = FXCollections.observableArrayList(smartphone);

                                modelTable.setItems(modelsData);
                            }
                        }
                    } else {
                        System.out.println("The answer is null or result is not 0");
                        Alerts.showBadResultAlert();
                    }
                } else {
                    System.out.println("InputStream is null. Can't receive an answer from the server");
                    Alerts.showBadInputStreamAlert();
                }
            } else {
                System.out.println("OutputStream is null. Can't send query to the server");
                Alerts.showBadOutputStreamAlert();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("There is an error (exception) in getAllModels()");
            Alerts.showSomeExceptionAlert(e);
        }
    }

    void getAllStores(int... modelId) {
        try {
            HashMap<String, Object> param = null;

            if (modelId.length > 0) {
                param = new HashMap<>();

                param.put("model", modelId[0]);
            }

            ServerQuery query = ServerQuery.create("store", "get", null, param);

            if (outputStream != null) {
                outputStream.writeObject(query);
                System.out.println("Query sent to the server");

                if (inputStream != null) {
                    System.out.println("Waiting for an answer from the server");

                    ServerResult result = (ServerResult) inputStream.readObject();

                    System.out.println("The answer was received");

                    if (result != null && result.getResult() == 0) {
                        System.out.println("The answer is not null and result is 0");
                        System.out.println("The answer has " + result.getObjects().size() + " elements");

                        if (storeTable != null && storesData != null) {
                            storeTable.setItems(storesData);
                        }

                        for (Owner element : (List<Owner>) result.getObjects()) {
                            Store store = element instanceof ModelToStore ? ((ModelToStore) element).store : (Store) element;

                            if (element instanceof ModelToStore) {
                                store.setModelToStore((ModelToStore) element);
                            }

                            if (storesData != null) {
                                System.out.println("Adding element... " + store);
                                storesData.add(store);
                            } else {
                                System.out.println("Data is null, creating with " + store);

                                storesData = FXCollections.observableArrayList(store);

                                storeTable.setItems(storesData);
                            }
                        }
                    } else {
                        System.out.println("The answer is null or result is not 0");
                        Alerts.showBadResultAlert();
                    }
                } else {
                    System.out.println("InputStream is null. Can't receive an answer from the server");
                    Alerts.showBadInputStreamAlert();
                }
            } else {
                System.out.println("OutputStream is null. Can't send query to the server");
                Alerts.showBadOutputStreamAlert();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("There is an error (exception) in getAllStores()");
            Alerts.showSomeExceptionAlert(e);
        }
    }

    private void clearModelTable() {
        if (modelsData != null) {
            modelsData.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Помилка");
            alert.setHeaderText("Очєрєдна тарабарщина");
            alert.setContentText("Ви і так не поймете... Сумніваєтеся? Гаразд, натє:\n" +
                    "ObservableList == null, ObservableList.clear() cannot be called!");
            alert.showAndWait();
        }
    }

    private void clearStoreTable() {
        if (storesData != null) {
            storesData.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Помилка");
            alert.setHeaderText("Очєрєдна тарабарщина");
            alert.setContentText("Ви і так не поймете... Сумніваєтеся? Гаразд, натє:\n" +
                    "ObservableList == null, ObservableList.clear() cannot be called!");
            alert.showAndWait();
        }
    }

    private void openModelDataForm(final client.model_data_form.Controller.FormType formType, final MouseEvent event,
                                   final SmartphoneModel... model) {
        String title;
        int width = 450;

        try {
            Parent root = null;
            Stage stage = null;
            Stage parentStage = null;
            client.model_data_form.Controller c = null;
            boolean isModelValid;

            try {
                isModelValid = model.length <= 0 ^ model[0] != null;
            } catch (ArrayIndexOutOfBoundsException ignored) {
                isModelValid = true;
            }

            if (isModelValid) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/model_data_form/form_data.fxml"));
                root = loader.load();
                stage = new Stage();
                parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                c = loader.getController();

                c.setOutputStream(outputStream);
                c.setInputStream(inputStream);
            }

            final client.model_data_form.Controller c1 = c;
            final Stage stage1 = stage;

            switch (formType) {
                case Add:
                    title = "Додати модель";

                    c.setOnOkButtonClickListener(() -> {
                        try {
                            outputStream.writeObject(ServerQuery.create("model", "add", c1.getModelToProcess(), null));

                            ServerResult result = (ServerResult) inputStream.readObject();

                            if (result != null) {
                                if (result.getResult() == 0) {
                                    if (modelsData == null) {
                                        modelsData = FXCollections.observableArrayList((SmartphoneModel) result.getObjects().get(0));
                                    } else {
                                        modelsData.add((SmartphoneModel) result.getObjects().get(0));
                                    }
                                    stage1.fireEvent(new WindowEvent(stage1, WindowEvent.WINDOW_CLOSE_REQUEST));
                                } else {
                                    System.err.println("Result is not 0, message: " + result.getMessage());
                                }
                            } else {
                                System.err.println("RESULT IS null");
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case Edit:
                    title = "Редагувати модель";

                    if (model.length <= 0 || model[0] == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);

                        alert.setTitle("Редагування");
                        alert.setHeaderText("Ви не обрали смартфон");
                        alert.showAndWait();
                        return;
                    }
                    c.setModelToProcess(model[0]);
                    c.setOnOkButtonClickListener(() -> {
                        try {
                            HashMap<String, Object> params = new HashMap<>();

                            params.put("id", c1.getModelToProcess().getId());
                            outputStream.writeObject(ServerQuery.create("model", "edit", c1.getModelToProcess(), params));

                            ServerResult result = (ServerResult) inputStream.readObject();

                            if (result != null) {
                                if (result.getResult() == 0) {
                                    modelsData.set(modelTable.getSelectionModel().getSelectedIndex(), (SmartphoneModel) result.getObjects().get(0));
                                    modelTable.refresh();
                                    stage1.fireEvent(new WindowEvent(stage1, WindowEvent.WINDOW_CLOSE_REQUEST));
                                } else {
                                    System.err.println("Result is not 0, message: " + result.getMessage());
                                }
                            } else {
                                System.err.println("RESULT is null");
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case Search:
                    title = "Пошук моделей";
                    width += 50;

                    c.setOnOkButtonClickListener(() -> {
                        try {
                            modelsData.clear();
                            getAllModels(c1.getParamsForSearch());
                            stage1.fireEvent(new WindowEvent(stage1, WindowEvent.WINDOW_CLOSE_REQUEST));
                        } catch (IllegalAccessException ignored) {
                            ignored.printStackTrace();
                        }
                    });
                    break;
                default:
                    title = "Unknown";
                    break;
            }

            final Stage parentStage1 = parentStage;

            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event1 -> parentStage1.setIconified(false));
            stage.setMinWidth(width);
            stage.setMinHeight(width);
            stage.setMaxWidth(width);
            stage.setMaxHeight(1360);
            stage.initStyle(StageStyle.UTILITY);
            c.setFormType(formType);
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

    private void openStoreDataForm(final client.store_data_form.Controller.FormType formType, final MouseEvent event,
                                   final Store... store) {
        String title;
        int width = 335;
        int height = 175;

        try {
            Parent root = null;
            Stage stage = null;
            Stage parentStage = null;
            client.store_data_form.Controller c = null;
            boolean isStoreValid;

            try {
                isStoreValid = store.length <= 0 ^ store[0] != null;
            } catch (ArrayIndexOutOfBoundsException ignored) {
                isStoreValid = true;
            }

            if (isStoreValid) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/store_data_form/form_data.fxml"));
                root = loader.load();
                stage = new Stage();
                parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                c = loader.getController();
            }

            final client.store_data_form.Controller c1 = c;
            final Stage stage1 = stage;

            switch (formType) {
                case Add:
                    title = "Додати магазин";

                    c.setOnOkButtonClickListener(() -> {
                        try {
                            outputStream.writeObject(ServerQuery.create("store", "add", c1.getStoreToProcess(), null));

                            ServerResult result = (ServerResult) inputStream.readObject();

                            if (result != null) {
                                if (result.getResult() == 0) {
                                    if (storesData == null) {
                                        storesData = FXCollections.observableArrayList((Store) result.getObjects().get(0));
                                    } else {
                                        storesData.add((Store) result.getObjects().get(0));
                                    }
                                    stage1.fireEvent(new WindowEvent(stage1, WindowEvent.WINDOW_CLOSE_REQUEST));
                                } else {
                                    System.err.println("Result is not 0, message: " + result.getMessage());
                                }
                            } else {
                                System.err.println("RESULT IS null");
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case Edit:
                    title = "Редагувати магазин";

                    if (store.length <= 0 || store[0] == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);

                        alert.setTitle("Редагування");
                        alert.setHeaderText("Ви не обрали магазин");
                        alert.showAndWait();
                        return;
                    }
                    c.setStoreToProcess(store[0]);
                    c.setOnOkButtonClickListener(() -> {
                        try {
                            HashMap<String, Object> params = new HashMap<>();

                            params.put("id", c1.getStoreToProcess().getId());
                            outputStream.writeObject(ServerQuery.create("store", "edit", c1.getStoreToProcess(), params));

                            ServerResult result = (ServerResult) inputStream.readObject();

                            if (result != null) {
                                if (result.getResult() == 0) {
                                    storesData.set(storeTable.getSelectionModel().getSelectedIndex(), (Store) result.getObjects().get(0));
                                    storeTable.refresh();
                                    stage1.fireEvent(new WindowEvent(stage1, WindowEvent.WINDOW_CLOSE_REQUEST));
                                } else {
                                    System.err.println("Result is not 0, message: " + result.getMessage());
                                }
                            } else {
                                System.err.println("RESULT is null");
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                default:
                    title = "Unknown";
                    break;
            }

            final Stage parentStage1 = parentStage;

            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event1 -> parentStage1.setIconified(false));
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setMaxWidth(width);
            stage.setMaxHeight(height);
            stage.initStyle(StageStyle.UTILITY);
            c.setFormType(formType);
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

    private void openModelToStoreDataForm() {
        String title = "Додати смартфон до магазину";
        int width = 285;
        int height = 150;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/model_to_store_data_form/form_data.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Stage parentStage = (Stage) tabs.getScene().getWindow();
            client.model_to_store_data_form.Controller c = loader.getController();

            c.setOutputStream(outputStream);
            c.setInputStream(inputStream);
            c.setOnOkButtonClickListener(() -> {
                try {
                    ModelToStore modelToStore = c.getModelToStoreToProcess();

                    modelToStore.setModel(modelTable.getSelectionModel().getSelectedItem().getId());
                    outputStream.writeObject(ServerQuery.create("store_has_model", "add", modelToStore, null));

                    ServerResult result = (ServerResult) inputStream.readObject();

                    if (result != null) {
                        if (result.getResult() == 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);

                            alert.setTitle(title);
                            alert.setHeaderText("Смартфон успішно доданий до магазину!");
                            alert.initModality(Modality.WINDOW_MODAL);
                            alert.initOwner(stage.getScene().getWindow());
                            alert.showAndWait();
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
            });
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> parentStage.setIconified(false));
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setMaxWidth(width);
            stage.setMaxHeight(height);
            stage.initStyle(StageStyle.UTILITY);
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
