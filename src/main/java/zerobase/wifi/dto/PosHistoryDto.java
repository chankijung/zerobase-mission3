package zerobase.wifi.dto;

import zerobase.wifi.model.PosHistoryModel;

import java.util.Date;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;

public class PosHistoryDto {
    private int histNo;
    private String longitude;
    private String latitude;
    private Date searchDate;

    public int getHistNo() {
        return histNo;
    }

    public void setHistNo(int histNo) {
        this.histNo = histNo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
}