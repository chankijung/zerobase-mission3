package zerobase.wifi.model;


import zerobase.wifi.dto.PosHistoryDto;
import zerobase.wifi.service.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PosHistoryModel {
    String url = "jdbc:mariadb://localhost:3306/mission1";
    String id = "user1";
    String pw = "user1234";

    public void insert(PosHistoryDto posHistoryDto){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement stat=null;

        try{
            conn= DriverManager.getConnection(url, id, pw);
            String sql = "INSERT INTO HISTORY(LNT, LAT, DATE) VALUES (?,?, datetime('now','localtime'))";
            stat= conn.prepareStatement(sql);
            stat.setString(1,String.valueOf(posHistoryDto.getLongitude()));
            stat.setString(2,String.valueOf(posHistoryDto.getLatitude()));
            int affected = stat.executeUpdate();
            if(affected<0){
                System.out.println("저장 실패");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
           try{
               if(stat!= null&& !stat.isClosed()){
                   stat.close();
               }
           }catch (SQLException e){
               e.printStackTrace();
           }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }

    public List<PosHistoryDto> selectAll(){
        List<PosHistoryDto> wifiList= new ArrayList<>();

        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement stat=null;
        ResultSet rs= null;
        try{
            conn=DriverManager.getConnection(url, id, pw);

            String sql="SELECT * FROM HISTORY";
            stat=conn.prepareStatement(sql);

            rs=stat.executeQuery();
            while (rs.next()){
                String histNo =rs.getString("ID");
                //일단 스트링이 아니지만 넣어봄
                String longitude =rs.getString("LNT");
                String latitude = rs.getString("LAT");
                Date date=rs.getDate("DATE");

                PosHistoryDto posHistoryDto=new PosHistoryDto();
                posHistoryDto.setHistNo(Integer.parseInt(histNo));
                posHistoryDto.setLongitude(longitude);
                posHistoryDto.setLongitude(latitude);
                posHistoryDto.setSearchDate(date);

                wifiList.add(posHistoryDto);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (stat != null && !stat.isClosed()) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wifiList;
        }
    }




