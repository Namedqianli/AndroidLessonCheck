package com.lcg.lessoncheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBService {

    private Connection connection=null;     //打开数据库对象
    private PreparedStatement preparedStatement=null;   //操作整合sql语句的对象
    private ResultSet resultSet=null;       //查询结果的集合

    //DBService对象
    public static DBService dbService=null;

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
}
