package zerobase.wifi.model;


import zerobase.wifi.dto.PosHistoryDto;
import zerobase.wifi.dto.WifiInfoDto;
import zerobase.wifi.service.WifiInfoService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WifiInfoModel {

    /*
    변수 넣고
    여기에 게터, 세터 다 넣기
     */
    // pubdao 넣기
    String url = "jdbc:mariadb://localhost:3306/mission1";
    String id = "user1";
    String pw = "user1234";
    public void insert(WifiInfoDto wifiInfoDto){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection conn=null;
        PreparedStatement stat=null;

        try{
            conn= DriverManager.getConnection(url, id, pw);

            String sql="INSERT INTO WIFI VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            stat=conn.prepareStatement(sql);
            stat.setString(1, String.valueOf(wifiInfoDto.getMgrNo()));
            stat.setString(2, wifiInfoDto.getRegion());
            stat.setString(3, wifiInfoDto.getMainNm());
            stat.setString(4, wifiInfoDto.getAddress());
            stat.setString(5, wifiInfoDto.getAddressDetail());
            stat.setString(6, wifiInfoDto.getInstallFloor());
            stat.setString(7, wifiInfoDto.getInstallTy());
            stat.setString(8, wifiInfoDto.getInstallMby());
            stat.setString(9, wifiInfoDto.getServiceSe());
            stat.setString(10, wifiInfoDto.getNetworkTy());
            stat.setString(11, String.valueOf(wifiInfoDto.getInstallYear()));
            stat.setString(12, wifiInfoDto.getIsOutdoor());
            stat.setString(13, wifiInfoDto.getConnectEnv());
            stat.setString(14, String.valueOf(wifiInfoDto.getLongitude()));
            stat.setString(15, String.valueOf(wifiInfoDto.getLatitude()));
            stat.setString(16, String.valueOf(wifiInfoDto.getWorkDate()));

            int affected = stat.executeUpdate();
            if(affected<0){
                System.out.println("저장실패");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
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
    }
    public List<WifiInfoDto> selectList(String lnt, String lat){
        List<WifiInfoDto> wifiList = new ArrayList<>();
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection conn=null;
        PreparedStatement stat=null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url, id, pw);

            String sql="SELECT *," +
                    " round(6371*acos(cos(radians( ? ))*cos(radians(LAT))*cos(radians(LNT)-radians( ? ))+sin(radians( ? ))*sin(radians(LAT))), 4)" +
                    " AS DISTANCE" +
                    " FROM WIFI" +
                    " ORDER BY DISTANCE" +
                    " LIMIT 20;";

            stat=conn.prepareStatement(sql);
            stat.setString(1,String.valueOf(lnt));
            stat.setString(2,String.valueOf(lat));
            stat.setString(3,String.valueOf(lnt));

            rs=stat.executeQuery();
            while(rs.next()){
                String dist = rs.getString("DISTANCE");
                String mgrNo = rs.getString("MGR_NO");
                String wrdofc = rs.getString("WRDOFC");
                String mainNm = rs.getString("MAIN_NM");
                String addre1 = rs.getString("ADRES1");
                String addre2 = rs.getString("ADRES2");
                String instlFloor = rs.getString("INSTL_FLOOR");
                String instlTy = rs.getString("INSTL_TY");
                String instlMby = rs.getString("INSTL_MBY");
                String svcSe = rs.getString("SVC_SE");
                String cmcwr = rs.getString("CMCWR");
                String cnstcYear = rs.getString("CNSTC_YEAR");
                String inOutDoor = rs.getString("INOUT_DOOR");
                String remars3 = rs.getString("REMARS3");
                String lntt = rs.getString("LNT");
                String latt = rs.getString("LAT");
                Date workDttm = rs.getDate("WORK_DTTM" );

                WifiInfoDto wifiInfoDto=new WifiInfoDto();
                wifiInfoDto.setDist(dist);
                wifiInfoDto.setMgrNo(Integer.parseInt(mgrNo));
                wifiInfoDto.setRegion(wrdofc);
                wifiInfoDto.setMainNm(mainNm);
                wifiInfoDto.setAddress(addre1);
                wifiInfoDto.setAddressDetail(addre2);
                wifiInfoDto.setInstallFloor(instlFloor);
                wifiInfoDto.setInstallTy(instlTy);
                wifiInfoDto.setInstallMby(instlMby);
                wifiInfoDto.setServiceSe(svcSe);
                wifiInfoDto.setNetworkTy(cmcwr);
                wifiInfoDto.setInstallYear(Integer.parseInt(cnstcYear));
                wifiInfoDto.setIsOutdoor(inOutDoor);
                wifiInfoDto.setConnectEnv(remars3);
                wifiInfoDto.setLongitude(Double.parseDouble(lntt));
                wifiInfoDto.setLatitude(Double.parseDouble(latt));
                wifiInfoDto.setWorkDate(workDttm);

                wifiList.add(wifiInfoDto);

            }
        }catch (Exception e){
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

        public void deleteAll(){
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            Connection conn=null;
            PreparedStatement stat=null;
            String sql= "DELETE FROM WIFI";

            try{
                conn=DriverManager.getConnection(url, id, pw);
                stat=conn.prepareStatement(sql);
                int affected= stat.executeUpdate();

                if(affected<0){
                    System.out.println("리셋 실패");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
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


    }

}


