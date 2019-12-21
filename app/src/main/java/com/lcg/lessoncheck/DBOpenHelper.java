package com.lcg.lessoncheck;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DBOpenHelper {

    private static String driver = "com.mysql.jdbc.Driver";//MySQL 驱动
    private static String url = "jdbc:mysql://118.25.88.42:3306/lessoncheck";//MYSQL数据库连接Url
    private static String user = "lesson";//用户名：lesson
    private static String password = ".123456";//密码：.123456

    /**
     * 连接数据库
     * */

    public static Connection getConnection(){
        Connection conn = null;
        try {
//                    System.setProperty("jdbc.driver","com.mysql.jdbc.Driver");
//                    java.sql.DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//                    new com.mysql.jdbc.Driver();
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);//获取连接
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 关闭数据库
     * */
    public static void closeAll(Connection connection, Statement statement){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭数据库
     * */
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
