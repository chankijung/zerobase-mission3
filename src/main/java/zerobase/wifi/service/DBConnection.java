package zerobase.wifi.service;

import zerobase.wifi.model.PosHistoryModel;

import java.sql.*;

public class DBConnection {

    String url = "jdbc:mariadb://localhost:3306";
    String id = "root";
    String pw = "root";
    Connection conn = null;
    PreparedStatement stat = null;

    public void insert(PosHistoryModel posHistoryModel){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("Connection 객체 생성성공");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        } catch (SQLException e) {
            System.out.println("Connection 객체 생성 실패");
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("conn.close() 에러");
            }
        }

        String sql = "INSERT INTO HISTORY VALUES (?, ?, NOW());";

//        try {
//            stat = conn.prepareStatement(sql);
//            stat.setString(1, String.valueOf(posHistoryModel.get));
//            stat.setString(2, String.valueOf(posHistoryModel.getY좌표()));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

}