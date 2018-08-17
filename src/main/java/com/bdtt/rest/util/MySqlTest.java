package com.bdtt.rest.util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tangqiliang on 2017/11/22.
 * 用来操作测试环境数据库，insert, delete等，线上环境因为数据库是只读权限，所以该脚本不能用；注，如果是查询，用GetMySql那个类
 */

public class MySqlTest {
    private static String sql_add = PropertiesHandle.readValue("sql_add");
    private static String sql_user = PropertiesHandle.readValue("sql_user");
    private static String sql_pwd = PropertiesHandle.readValue("sql_pwd");

    public static void insert_row_into_Local_ad_switch(String region_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String insertSql = "insert into local_ad_switch values ('100',"+ region_id + ", '1', '1', '1')";
        System.out.println(insertSql);
        stmt.execute(insertSql);
    }
    
    public static void delete_row_from_Local_ad_switch(String region_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String deleteSql = "delete from local_ad_switch where region_id_new = " + region_id;
        System.out.println(deleteSql);
        stmt.execute(deleteSql);
    }
    
    /**
     * 根据region_ID从数据库查找站点名称
     * @param region_id
     */
    public static String select_name_from_local_region (String region_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
    	Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "select spell from local_region where new_region_id = " + region_id;
        ResultSet resultSet= stmt.executeQuery(selectSql);
        String name="";
        while (resultSet.next()) {
			name =resultSet.getString("spell");
		}
        return name;	
	}

    //删除用户
    public static void delete_user(String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String deleteSql = "delete from local_user where uid = " + uid;
        System.out.println(deleteSql);
        stmt.execute(deleteSql);
    }
    
    //更新用户的注册时间
    public static void update_user_create_time(String uid, String createtime) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String updateSql = "update local_user set create_time = '" + createtime + "' where uid = " + uid;//不加单引号会报错
        System.out.println(updateSql);
        stmt.execute(updateSql);
    }
    
    //更新用户金币和零钱值
    public static void update_user_gold_money(String uid, String gold, String money) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String updateSql = "update local_user_money set gold = " + gold + ", money = " + money + ", total_gold = " + gold + ", total_money = " + money + " where uid = " + uid;
        System.out.println(updateSql);
        stmt.execute(updateSql);
    }
    
    //删除用户一元提现记录
    public static void delete_user_yytx (String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String deleteSql = "delete from local_trade_withdraw where type =2 and uid = " + uid;
        System.out.println(deleteSql);
        stmt.execute(deleteSql);
    }
    
    //删除用户徒弟
    public static void delete_user_subordination (String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String deleteSql = "delete from local_money_user_subordination where parent_id = " + uid;
        System.out.println(deleteSql);
        stmt.execute(deleteSql);
    }
    
    //删除徒弟唤醒记录, uid为徒弟的uid
    public static void delete_user_wakeup_record (String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String deleteSql = "delete from bdtt.local_money_wakeup_record where uid = " + uid;
        System.out.println(deleteSql);
        stmt.execute(deleteSql);
    }
    
    //更新用户的活跃时间
    public static void update_user_active_time(String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "select * from bdtt.local_money_wakeup where uid ="+ uid;
        //System.out.println(selectSql);
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String returnuid = null;
        while (selectRes.next()) { //循环输出结果集
        	returnuid = selectRes.getString("uid");
        }
        
        if (returnuid == null) {
            String insertSql = "insert into bdtt.local_money_wakeup values ('0',"+ uid + ", '0', '1', '0', '2018-01-23 17:30:24', '2018-01-23 17:31:44')";
            //INSERT INTO `bdtt`.`local_money_wakeup`(`id`, `uid`, `wakeup`, `status`, `is_delete`, `create_time`, `update_time`) VALUES (86, 2056081, 0, 0, 1, '2018-01-23 17:30:24', '2018-01-23 17:31:44');
            System.out.println(insertSql);
            stmt.execute(insertSql);
        } else {
            String updateSql = "update bdtt.local_money_wakeup set wakeup = 0 where uid = " + uid;
            System.out.println(updateSql);
            stmt.execute(updateSql);
        }
        
    }
    
    //删除用户领取赚钱阅读任务记录
    public static void delete_user_activity_receiver (String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String deleteSql = "delete from bdtt.local_money_activity_receivers where user_id = " + uid;
        System.out.println(deleteSql);
        stmt.execute(deleteSql);
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    	
    	//String region_id=PropertiesHandle.readValue("region_id");
    	//System.out.println(region_id);
    	//MySqlTest.delete_row_from_Local_ad_switch(region_id);
    	//MySqlTest.insert_row_into_Local_ad_switch(region_id);    
    	//MySqlTest.select_name_from_local_region(region_id);
    	//MySqlTest.update_user_create_time(uid, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	String uid = GetMySql.getUserId("13000000001");
    	System.out.println(uid);
    	MySqlTest.update_user_active_time(uid);
    }
}