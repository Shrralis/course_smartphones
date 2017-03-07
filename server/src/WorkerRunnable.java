import models.*;

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

            System.out.println("Called: " + query.getMethodName() + "(" + query.getTableName().toLowerCase() + ") from - "
                    + clientSocket.getInetAddress().getHostAddress());

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

            if (method.equalsIgnoreCase("add")) {
                return add(query);
            }

            if (method.equalsIgnoreCase("delete")) {
                return delete(query);
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
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/smartphones?useUnicode=true&characterEncoding=UTF-8",
                        "root",
                        "zolotorig91"
                );
            }
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
            String table = query.getTableName().toLowerCase();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(manufacturer)|(standard)|(os)|((enclosure)(((t|T)ype)|((m|M)aterial)))" +
                        "|(((sim(c|C)ard)|(screen)|(battery)|(memory(c|C)ard))((t|T)ype))|(processor)|(store)|(model)$")) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + table.toLowerCase()
                            + query.getMySQLCondition() + "`;");

                    if (table.equalsIgnoreCase("manufacturer")) {
                        result = ServerResult.create(new List(resultSet, Manufacturer.class));
                    }

                    if (table.equalsIgnoreCase("model")) {
                        result = ServerResult.create(new List(resultSet, SmartphoneModel.class, connection));
                    }

                    if (table.equalsIgnoreCase("standard")) {
                        result = ServerResult.create(new List(resultSet, Standard.class));
                    }

                    if (table.equalsIgnoreCase("os")) {
                        result = ServerResult.create(new List(resultSet, OS.class));
                    }

                    if (table.equalsIgnoreCase("enclosureType")) {
                        result = ServerResult.create(new List(resultSet, EnclosureType.class));
                    }

                    if (table.equalsIgnoreCase("enclosureMaterial")) {
                        result = ServerResult.create(new List(resultSet, EnclosureMaterial.class));
                    }

                    if (table.equalsIgnoreCase("simCardType")) {
                        result = ServerResult.create(new List(resultSet, SimCardType.class));
                    }

                    if (table.equalsIgnoreCase("screenType")) {
                        result = ServerResult.create(new List(resultSet, ScreenType.class));
                    }

                    if (table.equalsIgnoreCase("batteryType")) {
                        result = ServerResult.create(new List(resultSet, BatteryType.class));
                    }

                    if (table.equalsIgnoreCase("memoryCardType")) {
                        result = ServerResult.create(new List(resultSet, MemoryCardType.class));
                    }

                    if (table.equalsIgnoreCase("processor")) {
                        result = ServerResult.create(new List(resultSet, Processor.class));
                    }

                    if (table.equalsIgnoreCase("store")) {
                        result = ServerResult.create(new List(resultSet, Store.class));
                    }
                } else {
                    System.out.println("Unknown table (" + table + ") for get()");
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ServerResult add(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(manufacturer)|(standard)|(os)|((enclosure)(((t|T)ype)|((m|M)aterial)))" +
                        "|(((sim(c|C)ard)|(screen)|(battery)|(memory(c|C)ard))((t|T)ype))|(processor)|(store)|(model)$")) {
                    int iResult = statement.executeUpdate(query.getInsertMySQLQuery(), Statement.RETURN_GENERATED_KEYS);

                    if (iResult >= 0) {
                        ResultSet rs = statement.getGeneratedKeys();
                        int id = 0;

                        if (rs.next()) {
                            id = rs.getInt(1);
                        }

                        result = ServerResult.create(
                                new List(statement.executeQuery("SELECT * FROM " + table + " WHERE `id` = " + id + ";"),
                                        query.getObjectToProcess().getClass()));
                    } else {
                        result = ServerResult.create(1, "not added");
                    }
                }
            }
            connection.close();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ServerResult delete(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(manufacturer)|(standard)|(os)|((enclosure)(((t|T)ype)|((m|M)aterial)))" +
                        "|(((sim(c|C)ard)|(screen)|(battery)|(memory(c|C)ard))((t|T)ype))|(processor)|(store)|(model)$")) {
                    int iResult = statement.executeUpdate("DELETE FROM " + table + " WHERE `id` = "
                            + query.getObjectToProcess().getId() + ";");

                    if (iResult >= 0) {
                        result = ServerResult.create(0, "deleted");
                    } else {
                        result = ServerResult.create(1, "not deleted");
                    }
                }
            }
        } catch (SQLException ignored) {}
        return result;
    }
}
