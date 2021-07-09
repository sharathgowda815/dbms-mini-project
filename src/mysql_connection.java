import java.sql.*;

import javax.swing.JOptionPane;


public class mysql_connection {

//	public static String user = "";
//	public static String pass = "";
	
//	public void setCred(String user_now, String pass_now) {
//		this.user = user_now;
//		this.pass = pass_now;
//	}
	//Connection conn = null;
	
//	public static void main(String[] args) {
//		mysql_connection c1 = new mysql_connection();
//		
//		Connection conn = null;
//		conn = c1.mysql_Connector();
//		
//		System.out.println("Yes");
//	}
	
	public static Connection mysql_Connector() {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","123456");
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms",user,pass);  
			
//			Statement stmt = conn.createStatement();  
//			ResultSet rs = stmt.executeQuery("select adminid from admins");  
//			while(rs.next())  
//				System.out.println(rs.getString(1));  
//			conn.close(); 
			
			return conn;
			
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

	}

}
