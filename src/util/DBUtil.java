package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;

public class DBUtil {
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    private static ComboPooledDataSource ds = new ComboPooledDataSource();
    public static Connection getConn(){
        //每一个需要连接的线程  都先判断自己随身携带的ThreadLocal中是否已经有Connection
        Connection conn = tl.get();
        try {
            if(conn == null){
                //tl中没有连接  创建新连接  放到ThreadLocal容器中  并返回
                conn = ds.getConnection();
                tl.set(conn);//把新的连接放到ThreadLocal容器中
                return conn;
            }else{
                return conn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    //关闭rs
    public static void closeRs(ResultSet rs){
        if (rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //关闭psta
    public static void closePsta(PreparedStatement psta){
        if (psta!=null) {
            try {
                psta.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //关闭conn
    public static void closeConn(Connection conn){
        try {
            if (conn!=null && conn.isClosed()) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            tl.set(null);
        }
    }
}
