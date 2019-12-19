package com.lcg.lessoncheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {

    private Connection connection=null;     //打开数据库对象
    private Statement statement=null;   //操作整合sql语句的对象
    private ResultSet resultSet=null;       //查询结果的集合

    //DBService对象
    private static DBService dbService=null;

    /**
     * 构造方法私有化
     */
    private DBService(){

    }

    /**
     * 获取MySQL数据库单例类对象
     * */
    public static DBService getDbService(){
        if(dbService==null){
            dbService=new DBService();
        }
        return dbService;
    }

    /**
     * 获取密码
     * */
    public String getPassword(String account){
        //存放结果
        String password = "";
        //sql语句
        String sql="select password from user where account='%s'";
        sql = String.format(sql, account);
        try{
            connection = DBOpenHelper.getConnection();
            statement = connection.createStatement();
            if(statement != null && !connection.isClosed()){
                if(statement != null){
                    resultSet = statement.executeQuery(sql);
                    if(resultSet != null){
                        resultSet.next();
                        password = resultSet.getString("password");
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(connection, statement, resultSet);

        return password;
    }

    /*
    * 获取用户类型
    * */
    public String getAccountType(String account){
        String type = "";
        //sql语句
        String sql = "select identification from user where account='%s'";
        sql = String.format(sql, account);
        try{
            connection = DBOpenHelper.getConnection();
            statement = connection.createStatement();
            if(statement != null && !connection.isClosed()){
                if(statement != null){
                    resultSet = statement.executeQuery(sql);
                    if(resultSet != null){
                        resultSet.next();
                        type = resultSet.getString("identification");
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(connection, statement, resultSet);

        return type;
    }

    /*
     * 获取用户学校
     * */
    public String getSchool(String account){
        String type = "";
        //sql语句
        String sql = "select school from user where account='%s'";
        sql = String.format(sql, account);
        try{
            connection = DBOpenHelper.getConnection();
            statement = connection.createStatement();
            if(statement != null && !connection.isClosed()){
                if(statement != null){
                    resultSet = statement.executeQuery(sql);
                    if(resultSet != null){
                        resultSet.next();
                        type = resultSet.getString("school");
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(connection, statement, resultSet);

        return type;
    }

    /*
     * 获取用户名字
     * */
    public String getName(String account){
        String type = "";
        //sql语句
        String sql = "select name from user where account='%s'";
        sql = String.format(sql, account);
        try{
            connection = DBOpenHelper.getConnection();
            statement = connection.createStatement();
            if(statement != null && !connection.isClosed()){
                if(statement != null){
                    resultSet = statement.executeQuery(sql);
                    if(resultSet != null){
                        resultSet.next();
                        type = resultSet.getString("name");
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(connection, statement, resultSet);

        return type;
    }

    /*
     * 获取用户学号
     * */
    public String getId(String account){
        String type = "";
        //sql语句
        String sql = "select id from user where account='%s'";
        sql = String.format(sql, account);
        try{
            connection = DBOpenHelper.getConnection();
            statement = connection.createStatement();
            if(statement != null && !connection.isClosed()){
                if(statement != null){
                    resultSet = statement.executeQuery(sql);
                    if(resultSet != null){
                        resultSet.next();
                        type = resultSet.getString("id");
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(connection, statement, resultSet);

        return type;
    }

    /**
     * 注册
     * */
    public void sigin(String account, String password, String id, String name, String school, String identi){

        //sql语句
        String sql="INSERT INTO lessoncheck.`user`(Account, password, id, name, school, identification) VALUES('%s', '%s', '%s', '%s', '%s', '%s')";
        sql = String.format(sql, account, password, id, name, school, identi);
        try{
            connection = DBOpenHelper.getConnection();
            statement = connection.createStatement();
            if(statement != null && !connection.isClosed()){
                if(statement != null){
                    boolean re = statement.execute(sql);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
