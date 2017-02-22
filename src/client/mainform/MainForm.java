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

        if (outputStream == null) {
            System.err.println("LOH");
        }

        /*Button btn = new Button();


        btn.setText("Say 'Hello World'");
        btn.setOnMouseClicked(event -> {
            if (clientSocket != null) {
                try {
                    HashMap<String, Object> params = new HashMap<>();
                    ServerQuery query = ServerQuery.create("model", "get", null, params);
                    HashMap<String, Object> or = new HashMap<String, Object>();
                    or.put("lolka", new Integer(3));
                    or.put("s.lolka1", new Double(3.1));

                    params.put("test", "tost");
                    params.put("arr", or);

                    if (outputStream != null) {
                        outputStream.writeObject(query);
                    }

                    ServerResult result = (ServerResult) inputStream.readObject();

                    if (result != null && result.getMessage().equalsIgnoreCase("success")) {
                        System.out.println("Size: " + result.getObjects().size());

                        if (result.getObjects().size() > 0) {
                            System.out.println(((SmartphoneModel) result.getObjects().get(0)).color);
                        }
                    }
                } catch (IOException | ClassNotFoundException ignored) {}
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
                        outputStream.writeObject(ServerQuery.create(null, "disconnect", null, null));
                        outputStream.close();
                    }
                } catch (IOException ignored) {}
            }
        });*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_main.fxml"));
        Parent root = loader.load();

        ((Controller) loader.getController()).setOutputStream(outputStream);
        ((Controller) loader.getController()).setInputStream(inputStream);
        primaryStage.setTitle("Каталог смартфонів");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1050);
        primaryStage.setMinHeight(560);
        primaryStage.show();
//        new Controller().show(primaryStage, outputStream, inputStream);
    }
}
