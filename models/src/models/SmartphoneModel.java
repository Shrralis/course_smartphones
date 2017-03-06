package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public int sim_card_amount = 1;

    public SimCardType sim_card_type = null;

    public double thickness;

    public double weight;

    public String color = "чорний";

    public ScreenType screen_type = null;

    public double screen_diagonal;

    public String screen_resolution = null;

    public BatteryType battery_type = null;

    public int battery_capacity = 1500;

    public int ram = 2048;

    public int internal_storage = 8192;

    public MemoryCardType memory_card_type = null;

    public Processor processor = null;

    public String wifi = null;

    public boolean nfc = false;

    public String bluetooth = null;

    public String camera = null;

    public String frontal_camera = null;

    @SuppressWarnings("unused")
    public SmartphoneModel() {}

    public SmartphoneModel(ResultSet from, Connection connection) {
        parse(from, connection);
    }
    @Override
    public Owner parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            manufacturer = new Manufacturer(get("SELECT * FROM `manufacturer` WHERE `id` = "
                    + from.getInt("manufacturer"), connection));
            standard = new Standard(get("SELECT * FROM `standard` WHERE `id` = "
                    + from.getInt("standard"), connection));
            os = new OS(get("SELECT * FROM `os` WHERE `id` = "
                    + from.getInt("os"), connection));
            os_version = from.getString("os_version");
            enclosure_type = new EnclosureType(get("SELECT * FROM `enclosureType` WHERE `id` = "
                    + from.getInt("enclosure_type"), connection));
            enclosure_material = new EnclosureMaterial(get("SELECT * FROM `enclosureMaterial` WHERE `id` = "
                    + from.getInt("enclosure_material"), connection));
            sim_card_amount = from.getInt("sim_card_amount");
            sim_card_type = new SimCardType(get("SELECT * FROM `simCardType` WHERE `id` = "
                    + from.getInt("sim_card_type"), connection));
            thickness = from.getDouble("thickness");
            weight = from.getDouble("weight");
            color = from.getString("color");
            screen_type = new ScreenType(get("SELECT * FROM `screenType` WHERE `id` = "
                    + from.getInt("screen_type"), connection));
            screen_diagonal = from.getDouble("screen_diagonal");
            screen_resolution = from.getString("screen_resolution");
            battery_type = new BatteryType(get("SELECT * FROM `batteryType` WHERE `id` = "
                    + from.getInt("battery_type"), connection));
            battery_capacity = from.getInt("battery_capacity");
            ram = from.getInt("ram");
            internal_storage = from.getInt("internal_storage");
            memory_card_type = new MemoryCardType(get("SELECT * FROM `memoryCardType` WHERE `id` = "
                    + from.getInt("memory_card_type"), connection));
            processor = new Processor(get("SELECT * FROM `processor` WHERE `id` = "
                    + from.getInt("processor"), connection));
            wifi = from.getString("wifi");
            nfc = from.getInt("nfc") == 1;
            bluetooth = from.getString("bluetooth");
            camera = from.getString("camera");
            frontal_camera = from.getString("frontal_camera");
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        return result;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Standard getStandard() {
        return standard;
    }

    public OS getOS() {
        return os;
    }

    public String getOs_version() {
        return os_version;
    }

    public EnclosureType getEnclosureType() {
        return enclosure_type;
    }

    public EnclosureMaterial getEnclosureMaterial() {
        return enclosure_material;
    }

    public int getSim_card_amount() {
        return sim_card_amount;
    }

    public SimCardType getSimCardType() {
        return sim_card_type;
    }

    public double getThickness() {
        return thickness;
    }

    public double getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }

    public ScreenType getScreenType() {
        return screen_type;
    }

    public double getScreen_diagonal() {
        return screen_diagonal;
    }

    public String getScreen_resolution() {
        return screen_resolution;
    }

    public BatteryType getBatteryType() {
        return battery_type;
    }

    public int getBattery_capacity() {
        return battery_capacity;
    }

    public int getRam() {
        return ram;
    }

    public int getInternal_storage() {
        return internal_storage;
    }

    public MemoryCardType getMemoryCardType() {
        return memory_card_type;
    }

    public Processor getProcessor() {
        return processor;
    }

    public String getWifi() {
        return wifi;
    }

    public boolean isNfc() {
        return nfc;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public String getCamera() {
        return camera;
    }

    public String getFrontal_camera() {
        return frontal_camera;
    }
}
