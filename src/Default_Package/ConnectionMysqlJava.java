package Default_Package;
import java.sql.*;
import javax.swing.*;
public class ConnectionMysqlJava {
	
	Connection conn = null;
	public static Connection ConnecterDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:Mysql://localhost/gestionetud","root","");
			System.out.println("You are connected");
			return conn;
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
