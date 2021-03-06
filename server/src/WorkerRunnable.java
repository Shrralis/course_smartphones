import models.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by shrralis on 2/19/17.
 */
@SuppressWarnings("unchecked")
public class WorkerRunnable implements Runnable {
    private Socket clientSocket = null;
    private ObjectOutputStream outputStream = null;
    private Connection connection = null;
    private String sIP = null;

    WorkerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
        sIP = clientSocket.getInetAddress().getHostAddress();
    }

    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
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
            String table = query.getTableName();
            table = table != null ? table.toLowerCase() : "";

            System.out.println("Called: " + method + "(" + table + ") from - "
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

            if (method.equalsIgnoreCase("edit")) {
                return edit(query);
            }

            if (method.equalsIgnoreCase("exportToExcel")) {
                return exportToExcel(query);
            }
        }
        return null;
    }

    private ServerResult processResult(ServerResult result) throws IOException {
        if (result == null) {
            result = ServerResult.create(-1, "some error");
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
                        "development",
                        "tempPassword_byShrralis"
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
                    if (table.equalsIgnoreCase("store") && query.hasQueryParams()) {
                        ResultSet stores = statement.executeQuery("SELECT * FROM `store_has_model`" +
                                query.getMySQLCondition() + ";");
                        result = ServerResult.create(new List(stores, ModelToStore.class, connection));

                        connection.close();
                        return result;
                    }

                    ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + table + "`" +
                            query.getMySQLCondition() + ";");

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

            if (connection != null) {
                connection.close();
            }
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
                        "|(((sim(c|C)ard)|(screen)|(battery)|(memory(c|C)ard))((t|T)ype))|(processor)|(store)|(model)|(store_has_model)$")) {
                    int iResult = statement.executeUpdate(query.getInsertMysqlQuery(), Statement.RETURN_GENERATED_KEYS);

                    if (iResult >= 0) {
                        ResultSet rs = statement.getGeneratedKeys();
                        int id = 0;

                        if (rs.next()) {
                            id = rs.getInt(1);
                        }

                        if (table.equalsIgnoreCase("store_has_model")) {
                            result = ServerResult.create(0, "successfully added");
                        } else {
                            result = ServerResult.create(
                                    new List(statement.executeQuery("SELECT * FROM `" + table + "` WHERE `id` = " + id + ";"),
                                            query.getObjectToProcess().getClass(), connection)
                            );
                        }
                    } else {
                        result = ServerResult.create(1, "not added");
                    }
                }
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException | IllegalAccessException ignored) {
            ignored.printStackTrace();
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
                    int iResult = statement.executeUpdate("DELETE FROM `" + table + "` WHERE `id` = "
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

    private ServerResult edit(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(manufacturer)|(standard)|(os)|((enclosure)(((t|T)ype)|((m|M)aterial)))" +
                        "|(((sim(c|C)ard)|(screen)|(battery)|(memory(c|C)ard))((t|T)ype))|(processor)|(store)|(model)$")) {
                    int iResult = statement.executeUpdate(query.getUpdateMysqlQuery(), Statement.RETURN_GENERATED_KEYS);

                    if (iResult >= 0) {
                        int id = query.getObjectToProcess().getId();

                        result = ServerResult.create(
                                new List(statement.executeQuery("SELECT * FROM `" + table + "` WHERE `id` = " + id + ";"),
                                        query.getObjectToProcess().getClass(), connection)
                        );
                        System.out.println("Size: " + result.getObjects().size());
                    } else {
                        result = ServerResult.create(1, "not updated");
                    }
                }
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException | IllegalAccessException ignored) {}
        return result;
    }

    private ServerResult exportToExcel(ServerQuery query) {
        ServerResult result = null;
        XSSFWorkbook workbook = null;

        try {
            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement.executeQuery("SHOW TABLES;");
                workbook = new XSSFWorkbook();

                resultSet.beforeFirst();

                while (resultSet.next()) {
                    String tableName = resultSet.getString(1);
                    XSSFSheet sheet = workbook.createSheet(tableName);
                    ResultSet rs = statement2.executeQuery("SELECT * FROM `" + tableName + "`;");
                    XSSFRow header = sheet.createRow(0);
                    int columnCount = rs.getMetaData().getColumnCount(),
                            rows = 1;

                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rs.getMetaData().getColumnName(i);

                        header.createCell(i - 1).setCellValue(columnName);
                    }
                    rs.beforeFirst();

                    while (rs.next()) {
                        XSSFRow row = sheet.createRow(rows++);

                        for (int i = 1; i <= columnCount; i++) {
                            row.createCell(i - 1).setCellValue(rs.getString(i));
                        }
                    }
                }
            }

            File file = new File("exported_" + Calendar.getInstance().getTimeInMillis() + ".xlsx");

            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            FileInputStream fis = new FileInputStream(file);

            workbook.write(fos);
            fos.close();
            workbook.close();

            byte[] bytes = new byte[16 * 1024];
            int count;

            while ((count = fis.read(bytes)) > 0) {
                outputStream.write(bytes, 0, count);
            }
            fis.close();
            file.delete();

            result = ServerResult.create(0, "success");
        } catch (IOException | SQLException ignored) {}
        return result;
    }
}
