package com.bdtt.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * ssh到oam-dev-1，运行/home/tangqiliang/Scripts/operateredis.sh脚本去操作redis
 * @author Ryan Tang
 *
 */
public class RedisOperate {
		
	/**
	 * @param host 远程主机ip地址
	 * @param port 连接端口，null 时为默认端口
	 * @param user 用户名
	 * @param password 密码
	 * @return
	 * @throws JSchException 
	 */
	public static Session connect(String host, String user, String password, Integer port) throws JSchException{
		Session session = null;
		try {
			JSch jsch = new JSch();
			if(port != null){
				session = jsch.getSession(user, host, port.intValue());
			}else{
				session = jsch.getSession(user, host);
			}
			session.setPassword(password);
			//设置第一次登陆的时候提示，可选值:(ask | yes | no)
			session.setConfig("StrictHostKeyChecking", "no");
			//30秒连接超时
			session.connect(30000);
		} catch (JSchException e) {
			e.printStackTrace();
			System.out.println("SSH 获取连接发生错误");
			throw e;
		}
		return session;
	}
	
	/**
	 * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
	 * @param host	主机名
	 * @param user	用户名
	 * @param psw	密码
	 * @param port	端口
	 * @param command	命令
	 * @return
	 */
	public static String exec(String host, String user, String password, Integer port, String command){
		String result="";
		Session session =null;
		ChannelExec openChannel =null;
		try {
			JSch jsch=new JSch();
			if(port != null){
				session = jsch.getSession(user, host, port.intValue());
			}else{
				session = jsch.getSession(user, host);
			}
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(password);
			session.connect();
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			//int exitStatus = openChannel.getExitStatus();
			//System.out.println(exitStatus);
			openChannel.connect();  
            InputStream in = openChannel.getInputStream();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
            String buf = null;
            while ((buf = reader.readLine()) != null) {
            	result+= new String(buf.getBytes("gbk"),"UTF-8");  
            }  
		} catch (JSchException | IOException e) {
			result+=e.getMessage();
		}finally{
			if(openChannel!=null&&!openChannel.isClosed()){
				openChannel.disconnect();
			}
			if(session!=null&&session.isConnected()){
				session.disconnect();
			}
		}
		return result;
	}
	
	public static void main (String[] args) throws JSchException {
		//String customerId = PropertiesHandle.readValue("customerId");
		//String token = RedisSSHTest.exec("218.244.132.47", "root", "Vf3SKIl2bWrD7m8Kt9mG", null, "ssh root@10.47.95.66 \"/home/redis.sh\"");
		//RedisOperate.connect("101.37.228.23", "root", "q!xXMHjBmXTq8QMW", null);
		String udid = PropertiesHandle.readValue("udid");
		//String executecommand = "/home/tangqiliang/Scripts/operateredis.sh 'keys alert:life_service:" + udid + "'";
		String executecommand = "/home/tangqiliang/Scripts/operateredis.sh 'del alert:life_service:" + udid + "'";
		System.out.println(executecommand);
		String token = RedisOperate.exec("101.37.228.23", "root", "q!xXMHjBmXTq8QMW", null, executecommand);
		System.out.println("token: " + token);
	}
}
