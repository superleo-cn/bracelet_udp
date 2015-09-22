package com.qt.domain;

import java.util.Date;

import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.qt.sql.BraceletDataSource;

public class HealthData {

    private static final Logger LOGGER = Logger.getLogger(HealthData.class);

    private byte[] startCode;

    private String length;

    private byte command;

    private String motionState;

    private int pulseState;

    private float temperature;

    private String warning;

    private int sbp;

    private int dbp;

    private float latitude;

    private float longitude;

    private String braceletId;

    private byte[] endCode;

    public static void save(HealthData healthData) {

        Sql2o sql2o = new Sql2o(BraceletDataSource.getDataSoruce());

        String sql = "INSERT INTO tb_health_data(bracelet_id, motion_state, pulse_state, temperature, warning, sbp, dbp, latitude, longitude, create_date) "
                + "VALUES (:bracelet_id, :motion_state, :pulse_state, :temperature, :warning, :sbp, :dbp, :latitude, :longitude, :create_date)";

        try (Connection con = sql2o.open()) {
            LOGGER.info("==========[Save healthData start] -> [HealthData Id : " + healthData.getBraceletId() + "] ==========");
            con.createQuery(sql).addParameter("bracelet_id", healthData.getBraceletId())
                    .addParameter("motion_state", healthData.getMotionState())
                    .addParameter("pulse_state", healthData.getPulseState())
                    .addParameter("temperature", healthData.getTemperature())
                    .addParameter("warning", healthData.getWarning())
                    .addParameter("sbp", healthData.getSbp()).addParameter("dbp", healthData.getDbp())
                    .addParameter("latitude", healthData.getLatitude())
                    .addParameter("longitude", healthData.getLongitude())
                    .addParameter("create_date", new Date()).executeUpdate();
            LOGGER.info("==========[Save healthData end] -> [HealthData Id : " + healthData.getBraceletId() + "] ==========");
        } catch (Exception e) {
            LOGGER.error("==========[Save healthData exception] -> [HealthData Id : " + healthData.getBraceletId() + "] ==========", e);
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

    public String getMotionState() {
        return motionState;
    }

    public void setMotionState(String motionState) {
        this.motionState = motionState;
    }

    public int getPulseState() {
        return pulseState;
    }

    public void setPulseState(int pulseState) {
        this.pulseState = pulseState;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public byte[] getEndCode() {
        return endCode;
    }

    public void setEndCode(byte[] endCode) {
        this.endCode = endCode;
    }

    public String getBraceletId() {
        return braceletId;
    }

    public void setBraceletId(String braceletId) {
        this.braceletId = braceletId;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

}
