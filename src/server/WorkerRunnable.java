package server;

import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

/**
 * Created by shrralis on 2/19/17.
 */
@SuppressWarnings("unchecked")
public class WorkerRunnable implements Runnable {
    private Socket clientSocket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private Connection connection = null;
    private String sIP = null;

    public WorkerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
        sIP = clientSocket.getInetAddress().getHostAddress();
    }

    public void run() {
        try {
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ServerResult result;

            do {
                result = processResult(processQuery((ServerQuery) inputStream.readObject()));

                if (result == null) {
                    System.out.println("Disconnecting (" + sIP + ")...");
                } else {
                    sendAnswer(result);
                }
            } while (result != null);
            inputStream.close();
            outputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ServerResult processQuery(ServerQuery query) {
        if (query == null) {
            System.out.println("No query from the server!");
            return ServerResult.create(1, "No query");
        } else {
            String method = query.getMethodName();

            if (method.equalsIgnoreCase("disconnect")) {
                return ServerResult.create(0, "disconnect");
            }

            if (!openDBConnection()) {
                System.err.println("Cannot open database connection!");
                return ServerResult.create(2, "Database connection error");
            }

            if (method.equalsIgnoreCase("get")) {
                return get(query);
            }
        }
        return null;
    }

    private ServerResult processResult(ServerResult result) throws IOException {
        if (result == null) {
//            EMPTY RESULT
        } else if (result.getResult() != 0) {
//            ERROR
        } else {
            if (result.getMessage().equalsIgnoreCase("disconnect")) {
                return null;
            }
        }
        return result;
    }

    private boolean openDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/smartphones",
                    "root",
                    "zolotorig91"
            );

            return true;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendAnswer(ServerResult result) {
        try {
            outputStream.writeObject(result);
        } catch (IOException ignored) {}
    }

    private ServerResult get(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(manufacturer)|(standard)|(os)|((enclosure_)((type)|(material)))" +
                        "|(((sim_card)|(screen)|(battery))(_type)|(memory_card))|(processor)|(store)|(model)$")) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + table + "`;");

                    if (table.equalsIgnoreCase("manufacturer")) {
                        result = ServerResult.create(new List(resultSet, Manufacturer.class));
                    }

                    if (table.equalsIgnoreCase("model")) {
                        result = ServerResult.create(new List(resultSet, SmartphoneModel.class));
                    }
                } else {
                    System.out.println("Unknown table (" + table + ") for get()");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
