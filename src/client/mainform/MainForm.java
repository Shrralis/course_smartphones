package client.mainform;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.ServerQuery;
import model.ServerResult;
import model.SmartphoneModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by shrralis on 2/19/17.
 */
public class MainForm extends Application {
    private Socket clientSocket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            clientSocket = new Socket("localhost", 6777);
        } catch (IOException ignored) {
            System.err.println("No connection with the server!");
        }

        if (clientSocket != null) {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_main.fxml"));
        Parent root = loader.load();

        ((Controller) loader.getController()).setOutputStream(outputStream);
        ((Controller) loader.getController()).setInputStream(inputStream);
        ((Controller) loader.getController()).getAll();
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
                    }
                } catch (IOException ignored) {}
            }
        });
    }
}
