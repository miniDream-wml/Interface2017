package com.bdtt.rest.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangqiliang on 2017/8/2.
 * 此类中的内容为实时获取数据库中字段
 */

public class GetMySql {
    private static String sql_add = PropertiesHandle.readValue("sql_add");
    private static String sql_user = PropertiesHandle.readValue("sql_user");
    private static String sql_pwd = PropertiesHandle.readValue("sql_pwd");

    //从数据库获取用户uid
    public static String getUserId(String username) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        //String selectSql = "select * from bdtt.local_user where user_name='13666680348'";
        String selectSql = "select * from bdtt.local_user where user_name="+"'"+username+"'";
        //System.out.println(selectSql);
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String uid = null;
        while (selectRes.next()) { //循环输出结果集
        	uid = selectRes.getString("uid");
        }
        return uid;
    }
    
    //从数据库获取精品卡出现的频率
    public static String getSpecialCardRatio(String news_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        //String selectSql = "select * from bdtt.local_user where user_name='13666680348'";
        String selectSql = "select * from bdtt.qualified_card_news_click_info where news_id="+ news_id;
        //System.out.println(selectSql);
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String ratio = null;
        while (selectRes.next()) { //循环输出结果集
        	ratio = selectRes.getString("ratio");
        }
        return ratio;
    }
    
    public static String getTopicName(String news_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        //String selectSql = "select * from bdtt.local_user where user_name='13666680348'";
        String selectSql = "select * from bdtt.local_news where id="+ news_id;
        //System.out.println(selectSql);
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String ratio = null;
        while (selectRes.next()) { //循环输出结果集
        	ratio = selectRes.getString("keywords");
        }
        return ratio;
    }
    
    //根据region_id获取该region的本地圈频道
    public static String getChannelName(String region_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        //String selectSql = "select * from bdtt.local_user where user_name='13666680348'";
        String selectSql = "select *from local_circle_channel where region_id=" + region_id + " and sort != '0' order by sort desc";
        System.out.println(selectSql);
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String channelname = null;
        while (selectRes.next()) { //循环输出结果集
        	channelname = selectRes.getString("name");
        }
        return channelname;
    }
    
    //根据region_id获取region的name
    public  static String getRegionName (String region_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		Connection con = null; //定义一个MYSQL链接对象
	    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
	    Statement stmt; //创建声明
	    stmt = con.createStatement();
	    String selectSql = "select spell from local_region where new_region_id = " + region_id;
	    ResultSet resultSet= stmt.executeQuery(selectSql);
	    String regionname="";
	    while (resultSet.next()) {//循环输出结果集	
	    	regionname =resultSet.getString("spell");
		}
	    return regionname;	
    }
    
    //从数据库查询一篇未删除的专题ID
    public  static String getTopicID ()throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		Connection con = null; //定义一个MYSQL链接对象
	    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
	    Statement stmt; //创建声明
	    stmt = con.createStatement();
	    String selectSql = "select * from local_news where news_category = 2 and is_delete = 0 limit 1";
	    ResultSet resultSet= stmt.executeQuery(selectSql);
	    String topicID="";
	    while (resultSet.next()) {//循环输出结果集	
	    	topicID =resultSet.getString("id");
		}
	    return topicID;
	
    }
    //从数据库查询一篇已经删除的专题ID
    public  static String getdeleteTopicID ()throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		Connection con = null; //定义一个MYSQL链接对象
	    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
	    Statement stmt; //创建声明
	    stmt = con.createStatement();
	    String selectSql = "select * from local_news where news_category = 2 and is_delete = 1 limit 1";
	    ResultSet resultSet= stmt.executeQuery(selectSql);
	    String deltopicid="";
	    while (resultSet.next()) {//循环输出结果集	
	    	deltopicid =resultSet.getString("id");
		}
	    return deltopicid;
	
    }
    //从数据库查询app最新版本
    public  static String getCurVersion (String type)throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		Connection con = null; //定义一个MYSQL链接对象
	    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
	    Statement stmt; //创建声明
	    stmt = con.createStatement();
	    String selectSql = "select version from local_update where type ="+type+" order by version desc limit 1";
	    System.out.println(selectSql);
	    ResultSet resultSet= stmt.executeQuery(selectSql);
	    String curversion="";
	    while (resultSet.next()) {//循环输出结果集	
	    	curversion =resultSet.getString("version");
		}
	    return curversion;
    }
    
    //获取用户的金币数
    public  static String getGoldNum (String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		Connection con = null; //定义一个MYSQL链接对象
	    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
	    Statement stmt; //创建声明
	    stmt = con.createStatement();
	    String selectSql = "select * from local_user_money where uid = " + uid;
	    ResultSet resultSet= stmt.executeQuery(selectSql);
	    String goldnum="";
	    while (resultSet.next()) {//循环输出结果集	
	    	goldnum =resultSet.getString("gold");
		}
	    return goldnum;	
    }
    
    //获取用户的零钱数
    public  static String getMoneyNum (String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		Connection con = null; //定义一个MYSQL链接对象
	    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
	    Statement stmt; //创建声明
	    stmt = con.createStatement();
	    String selectSql = "select * from local_user_money where uid = " + uid;
	    ResultSet resultSet= stmt.executeQuery(selectSql);
	    String moneynum="";
	    while (resultSet.next()) {//循环输出结果集	
	    	moneynum =resultSet.getString("money");
		}
	    return moneynum;
    }
    
    //获取用户的总零钱数
    public  static String getTotalMoneyNum (String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		Connection con = null; //定义一个MYSQL链接对象
	    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
	    Statement stmt; //创建声明
	    stmt = con.createStatement();
	    String selectSql = "select * from local_user_money where uid = " + uid;
	    ResultSet resultSet= stmt.executeQuery(selectSql);
	    String totalmoneynum="";
	    while (resultSet.next()) {//循环输出结果集	
	    	totalmoneynum =resultSet.getString("total_money");
		}
	    return totalmoneynum;
    }
    
    //从数据库获取赚钱阅读活动id
    public static String getTaskActId(String actname) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "select * from local_money_config_setting where name ="+"'"+actname+"'";
        //System.out.println(selectSql);
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String actid = null;
        while (selectRes.next()) { //循环输出结果集
        	actid = selectRes.getString("id");
        }
        return actid;
    }
    
    //从数据库获取用户已经接受的赚钱阅读活动task_id列表
    public static List getReceivedTaskIdList(String uid) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		List <String> task_id_list = new ArrayList<String>();
		Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "select * from bdtt.local_money_activity_receivers where user_id = " + uid;
        //System.out.println(selectSql);
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String task_id = null;
        while (selectRes.next()) { //循环输出结果集
        	task_id = selectRes.getString("task_id");
        	//System.out.println(id);
        	task_id_list.add(task_id);
        }
		System.out.println(task_id_list);
		return task_id_list;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    	
    	//String region_id=PropertiesHandle.readValue("region_id");
    	//System.out.println(region_id);
    	//System.out.println(GetMySql.getChannelName(region_id));
    	//System.out.println(GetMySql.getRegionName(region_id));
    	//System.out.println(GetMySql.getTaskActId("10800元"));
    	System.out.println(GetMySql.getReceivedTaskIdList("3000332"));

    }
}