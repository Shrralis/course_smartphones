package model;

import java.sql.*;

/**
 * Created by shrralis on 2/19/17.
 */
public class SmartphoneModel extends Owner {
    public Manufacturer manufacturer = null;

    public Standard standard = null;

    public OS os = null;

    public String os_version = null;

    public EnclosureType enclosure_type = null;

    public EnclosureMaterial enclosure_material = null;

    public int sim_card_amount;

    public SimCardType sim_card_type = null;

    public double thickness;

    public double weight;

    public String color;

    public ScreenType screen_type = null;

    public double screen_diagonal;

    public String screen_resolution = null;

    public BatteryType battery_type = null;

    public int battery_capacity;

    public int ram;

    public int internal_storage;

    public MemoryCardType memory_card_type = null;

    public Processor processor = null;

    public String wifi = null;

    public boolean nfc = false;

    public String bluetooth = null;

    public String camera = null;

    public String frontal_camera = null;

    @SuppressWarnings("unused")
    public SmartphoneModel() {}

    public SmartphoneModel(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public Owner parse(ResultSet from) throws SQLException {
        super.parse(from);

        manufacturer = new Manufacturer(get("SELECT * FROM `manufacturer` WHERE `id` = "
                + from.getInt("manufacturer")));
        standard = new Standard(get("SELECT * FROM `standard` WHERE `id` = "
                + from.getInt("standard")));
        os = new OS(get("SELECT * FROM `os` WHERE `id` = "
                + from.getInt("os")));
        os_version = from.getString("os_version");
        enclosure_type = new EnclosureType(get("SELECT * FROM `enclosure_type` WHERE `id` = "
                + from.getInt("enclosure_type")));
        enclosure_material = new EnclosureMaterial(get("SELECT * FROM `enclosure_material` WHERE `id` = "
                + from.getInt("enclosure_material")));
        sim_card_amount = from.getInt("sim_card_amount");
        sim_card_type = new SimCardType(get("SELECT * FROM `sim_card_type` WHERE `id` = "
                + from.getInt("sim_card_type")));
        thickness = from.getDouble("thickness");
        weight = from.getDouble("weight");
        color = from.getString("color");
        screen_type = new ScreenType(get("SELECT * FROM `screen_type` WHERE `id` = "
                + from.getInt("screen_type")));
        screen_diagonal = from.getDouble("screen_diagonal");
        screen_resolution = from.getString("screen_resolution");
        battery_type = new BatteryType(get("SELECT * FROM `battery_type` WHERE `id` = "
                + from.getInt("battery_type")));
        battery_capacity = from.getInt("battery_capacity");
        ram = from.getInt("ram");
        internal_storage = from.getInt("internal_storage");
        memory_card_type = new MemoryCardType(get("SELECT * FROM `memory_card_type` WHERE `id` = "
                + from.getInt("memory_card_type")));
        processor = new Processor(get("SELECT * FROM `processor` WHERE `id` = "
                + from.getInt("processor")));
        wifi = from.getString("wifi");
        nfc = from.getInt("nfc") == 1;
        bluetooth = from.getString("bluetooth");
        camera = from.getString("camera");
        frontal_camera = from.getString("frontal_camera");

        return this;
    }

    public ResultSet get(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ced",
                "root",
                "zolotorig91"
        );
        Statement statement = connection.createStatement();

        return statement.executeQuery(sql);
    }
}
