package server;

import model.Owner;
import model.ServerQuery;
import model.ServerResult;

import java.io.*;
import java.net.Socket;
import java.sql.*;

/**
 * Created by shrralis on 2/19/17.
 */
public class WorkerRunnable implements Runnable {
    private Socket clientSocket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private Connection connection = null;
    private String sServer = null;

    public WorkerRunnable(Socket clientSocket, String server) {
        this.clientSocket = clientSocket;
        this.sServer   = server;
    }

    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {}

        try {
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            while (processResult(processQuery((ServerQuery) inputStream.readObject()), clientSocket)) {}
            inputStream.close();
            outputStream.close();
//            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
//            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

//            processResult(processQuery((ServerQuery) input.readObject()), clientSocket);
//            output.close();
//            input.close();
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
                System.out.println("Disconnecting...");
                return ServerResult.create(0, "disconnect");
            }

            if (!openDBConnection()) {
                System.err.println("Cannot open database connection!");
                return ServerResult.create(2, "Database connection error");
            }

            if (method.equalsIgnoreCase("get")) {
                get(query.getTableName());
            }
        }
        return null;
    }

    private boolean processResult(ServerResult result, Socket clientSocket) throws IOException {
//        ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        if (result == null) {
//            outputStream.writeObject(ServerResult.create(666, "Unexpected error!"));
        } else if (result.getResult() != 0) {

        } else {
            if (result.getMessage().equalsIgnoreCase("disconnect")) {
//                outputStream.close();
                return false;
            }
        }
//        outputStream.close();
        return true;
    }

    private boolean openDBConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ced",
                    "root",
                    "zolotorig91"
            );

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ServerResult get(String table) {
        try {

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement();

                if (table.equalsIgnoreCase("model")) {

                } else if (table.matches("^(manufacturer)|(standard)|(os)|((enclosure_)((type)|(material)))|(((sim_card)|(screen)|(battery))(_type)|(memory_card))||(processor)|(store)$")) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + table + "`;");
                } else {
                    System.out.println("Unknown table (" + table + ") for get()");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
