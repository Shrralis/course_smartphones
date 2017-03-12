package client.main_form;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.ServerQuery;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by shrralis on 2/19/17.
 */
public class MainForm extends Application {
    private FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/main_form/form_main.fxml"));
    private Socket clientSocket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            loader.load();
            openServerConnection();
            drawForm(primaryStage);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Помилка");
            alert.setHeaderText("ЕКСЕПШОН!!1");
            alert.setContentText("Вітаю, ачівка!! Ви стали свідком (свідицею) г*мнокоду криворукого програміста Славіка." +
                    "\nФорма не прогрузилась.");
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    private void openServerConnection() {
        try {
            if (clientSocket == null || clientSocket.isClosed()) {
                clientSocket = new Socket("shrralis.com", 6777);
            }

            if (outputStream == null) {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            }

            if (inputStream == null) {
                inputStream = new ObjectInputStream(clientSocket.getInputStream());
            }
            ((Controller) loader.getController()).setOutputStream(outputStream);
            ((Controller) loader.getController()).setInputStream(inputStream);
        } catch (IOException ignored) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Помилка");
            alert.setHeaderText("З'єднання з сервером неможливе");
            alert.setContentText("Сервер недоступний!\n" +
                    "Спробуйте пізніше. Для повтору спроби натисніть \"Оновити\" у вікні програми.");
            System.err.println("No connection with the server!\nTry later.");
            alert.showAndWait();
        }
    }

    private void drawForm(Stage primaryStage) {
        Parent root = loader.getRoot();

        ((Controller) loader.getController()).getAllModels();
        ((Controller) loader.getController()).getAllStores();
        ((Controller) loader.getController()).setOnRefreshModelsButtonClickListener(this::openServerConnection);
        ((Controller) loader.getController()).setOnRefreshStoresButtonClickListener(this::openServerConnection);
        primaryStage.setTitle("Каталог смартфонів");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1050);
        primaryStage.setMinHeight(560);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            if (clientSocket != null) {
                try {
                    if (outputStream != null) {
                        outputStream.writeObject(ServerQuery.create(null, "disconnect", null, null));
                        outputStream.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setTitle("Помилка");
                        alert.setHeaderText("Я вже просто без слів...");
                        alert.setContentText("Швидше за все, немає з'єднання з сервером. Але це не факт.");
                        alert.showAndWait();
                    }
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setTitle("Помилка");
                    alert.setHeaderText("ЕКСЕПШОН!!1");
                    alert.setContentText("Щось із вводом-виводом, усе в консолі, якщо ти тру пацик.");
                    e.printStackTrace();
                    alert.showAndWait();
                }
            }
        });
    }
}
