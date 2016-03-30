package login;

import java.sql.*;
import javax.swing.*;

public class Polaczenie {
	static String data;
	Connection conn = null;
	
	public static Connection dbConn(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String polaczenieURL = "jdbc:mysql://localhost/projekt-java?user=admin&password=admin&useUnicode=true&characterEncoding=utf8";
			Connection conn = DriverManager.getConnection(polaczenieURL);
			//JOptionPane.showMessageDialog(null, "Po³¹czono");
			return conn;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;			
		}
		
	}	

}
