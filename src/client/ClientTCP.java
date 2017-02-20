package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.ServerQuery;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by shrralis on 2/19/17.
 */
public class ClientTCP extends Application {
    private Socket clientSocket = null;
    private ObjectOutputStream outputStream = null;

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

        Button btn = new Button();
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        btn.setText("Say 'Hello World'");
        btn.setOnMouseClicked(event -> {
            if (clientSocket != null) {
                try {
                    outputStream.writeObject(ServerQuery.create("test", "get", null));
                } catch (IOException ignored) {}
            }
        });

        StackPane root = new StackPane();

        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            if (clientSocket != null) {
                try {
                    if (outputStream != null) {
                        outputStream.writeObject(ServerQuery.create(null, "disconnect", null));
                        outputStream.close();
                    }
                } catch (IOException ignored) {}
            }
        });
    }
}
