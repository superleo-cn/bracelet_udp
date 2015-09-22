package com.qt.domain;

import com.qt.sql.BraceletDataSource;
import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;

public class EmergencyData {

    private static final Logger LOGGER = Logger.getLogger(EmergencyData.class);

    private byte[] startCode;

    private String length;

    private byte command;

    private String braceletId;

    private String status;

    private float gyroscope;

    private float acceleration;

    private float time;

    private float position;

    private float latitude;

    private float longitude;

    private byte[] endCode;

    public static void save(EmergencyData emergency) {

        Sql2o sql2o = new Sql2o(BraceletDataSource.getDataSoruce());

        String sql = "INSERT INTO tb_emergency_data(bracelet_id, status, gyroscope, acceleration, time, position, latitude, longitude, create_date) "
                + "VALUES (:bracelet_id, :status, :gyroscope, :acceleration, :time, :position, :latitude, :longitude, :create_date)";

        try (Connection con = sql2o.open()) {
            LOGGER.info("==========[Save EmergencyData start] -> [EmergencyData Id : " + emergency.getBraceletId() + "] ==========");
            con.createQuery(sql).addParameter("bracelet_id", emergency.getBraceletId())
                    .addParameter("status", emergency.getStatus())
                    .addParameter("gyroscope", emergency.getGyroscope())
                    .addParameter("acceleration", emergency.getAcceleration())
                    .addParameter("time", emergency.getTime())
                    .addParameter("position", emergency.getPosition())
                    .addParameter("latitude", emergency.getLatitude())
                    .addParameter("longitude", emergency.getLongitude())
                    .addParameter("create_date", new Date()).executeUpdate();
            LOGGER.info("==========[Save EmergencyData end] -> [EmergencyData Id : " + emergency.getBraceletId() + "] ==========");
        } catch (Exception e) {
            LOGGER.error("==========[Save EmergencyData exception] -> [EmergencyData Id : " + emergency.getBraceletId() + "] ==========", e);
        }
    }

    public byte[] getStartCode() {
        return startCode;
    }

    public void setStartCode(byte[] startCode) {
        this.startCode = startCode;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public String getBraceletId() {
        return braceletId;
    }

    public void setBraceletId(String braceletId) {
        this.braceletId = braceletId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(float gyroscope) {
        this.gyroscope = gyroscope;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public byte[] getEndCode() {
        return endCode;
    }

    public void setEndCode(byte[] endCode) {
        this.endCode = endCode;
    }
}
