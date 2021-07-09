import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.SwingConstants;

//import com.mysql.jdbc.Connection;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class Login {

	public JFrame frame_login;
	private JTextField text_login_user;
	private JPasswordField password_login;
	//private int who = -1;
	private int customer = 0;
	private int admin = 0;
	public int current_customer_id = 0;
	private int i = 0;
//	public customer_payment cust_pay_page = new customer_payment();
//	customer_view cust_view = new customer_view();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login(0);
					window.frame_login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;

	/**
	 * Create the application.
	 */
	public Login(int cid_now) {		
		initialize();
		
		connection = mysql_connection.mysql_Connector();
		
		current_customer_id = cid_now;
//		try {
//			connection = mysql_connection.mysql_Connector();
//			Statement stmt = connection.createStatement();  
//			ResultSet rs = stmt.executeQuery("select adminid from admins");  
//			while(rs.next())  
//				System.out.println(rs.getString(1));  
//			connection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
//		if (i == 0) {
//			
//			try {
//				
//				JTextField user_now = new JTextField(10);
//			    JTextField pass_now = new JTextField(10);
//
//			    JPanel myPanel = new JPanel();
//			    myPanel.add(new JLabel("User Name:"));
//			    myPanel.add(user_now);
//			    myPanel.add(Box.createVerticalStrut(15)); // a spacer
//			    myPanel.add(new JLabel("Password:"));
//			    myPanel.add(pass_now);
//
//			    int result = JOptionPane.showConfirmDialog(null, myPanel, 
//			    		"MySQL Connection", JOptionPane.OK_CANCEL_OPTION);
//			    if (result == JOptionPane.OK_OPTION) {
////			    	System.out.println("x value: " + cname_now.getText());
//
//			    	mysql_connection mysqlc = new mysql_connection();
//			    	mysqlc.user = user_now.getText();
//			    	mysqlc.pass = pass_now.getText();
//			    	
//			    }	
//				
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			
//			i = 1;
//		}
		
		frame_login = new JFrame();
//		frame_login.setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_frame_icon.png"));
		frame_login.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/login_frame_icon.png")));
//		Image img = new ImageIcon(this.getClass().getResource("/img/file_exit_1.png")).getImage();
//		frame_login.setIconImage(new ImageIcon(img6));
		frame_login.setTitle("LOGIN");
		frame_login.setResizable(false);
		frame_login.setBounds(100, 100, 1000, 600);
		frame_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_login.getContentPane().setLayout(null);
		
		JLabel label_password_icon = new JLabel("");
		label_password_icon.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_password_icon.png"));
		label_password_icon.setBounds(96, 216, 74, 80);
		frame_login.getContentPane().add(label_password_icon);
		
		JLabel label_user_icon = new JLabel("");
		label_user_icon.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_user_icon.png"));
		label_user_icon.setBounds(96, 149, 70, 58);
		frame_login.getContentPane().add(label_user_icon);
		
		text_login_user = new JTextField();
		text_login_user.setToolTipText("USER");
		text_login_user.setFont(new Font("Tahoma", Font.PLAIN, 20));
		text_login_user.setBounds(213, 161, 194, 35);
		frame_login.getContentPane().add(text_login_user);
		text_login_user.setColumns(10);
		
		password_login = new JPasswordField();
		password_login.setFont(new Font("Tahoma", Font.PLAIN, 15));
		password_login.setEchoChar('*');
		password_login.setToolTipText("PASSWORD");
		password_login.setBounds(213, 238, 194, 35);
		frame_login.getContentPane().add(password_login);
		
		JRadioButton radio_login_who_admin = new JRadioButton("Admin");
		radio_login_who_admin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				customer = 0;
				admin = 1;
				
			}
		});
		radio_login_who_admin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radio_login_who_admin.setToolTipText("Admin");
		radio_login_who_admin.setBounds(329, 297, 80, 21);
		frame_login.getContentPane().add(radio_login_who_admin);
		
		JRadioButton radio_login_who_customer = new JRadioButton("Customer");
		radio_login_who_customer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				admin = 0;
				customer = 1;
				
			}
		});
		radio_login_who_customer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radio_login_who_customer.setToolTipText("Customer");
		radio_login_who_customer.setBounds(213, 297, 97, 21);
		frame_login.getContentPane().add(radio_login_who_customer);
		
		// grouping radio buttons to select only one of either
		ButtonGroup test = new ButtonGroup();
		test.add(radio_login_who_customer);
		test.add(radio_login_who_admin);
		
		// if customer 1 else 0 (admin)
		//who = radio_login_who_customer.isSelected()? 1 : 0;
		
		JButton button_login_get_drunk = new JButton("GET DRUNK");
		button_login_get_drunk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (text_login_user.getText() == null || text_login_user.getText().trim().isEmpty() || password_login.getText() == null || password_login.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "All The Fields Should Be Filled","Alert",JOptionPane.WARNING_MESSAGE);
					}	
					
					// customer
					if (customer == 1) {
						String query = "select * from lms.customer where cname=? and pass=?";
						PreparedStatement pst = connection.prepareStatement(query); 
						pst.setString(1, text_login_user.getText());
						pst.setString(2, password_login.getText());
						
						ResultSet rs = pst.executeQuery();
						
						int count = 0;
						
//						while (rs.next()) {
//							count++;
//						}
						
						while (rs.next()) {
							count++;
//							System.out.println("->"+count);
							current_customer_id = rs.getInt("cid");
							System.out.println("cid "+current_customer_id);
						}
						
//						System.out.println("->"+count);
		
//						current_customer_id = rs.getInt(1);
//						System.out.println(current_customer_id);
						
//						if (count == 1) {
//						if (rs.next() == true) {
						if (count == 1) {
							JOptionPane.showMessageDialog(null, "Correct Customer");
							
//							cust_pay_page.current_cid = Integer.toString(current_customer_id);
							
//							current_customer_id = rs.getInt("cid");
//							System.out.println(current_customer_id);
							
							frame_login.dispose();
//							frame_login.disable();
							
							customer_view cust_view = new customer_view(current_customer_id);
//							cust_view.current_cid = current_customer_id;
							cust_view.setVisible(true);
							
						} else {
							JOptionPane.showMessageDialog(null, "Not Correct");
						}
					
						rs.close();
						pst.close();
					}
					
					// admin
					if (admin == 1) {
						String query = "select exists(select * from admins where adminid=? and pass=?)";
						PreparedStatement pst = connection.prepareStatement(query); 
						pst.setString(1, text_login_user.getText());
						pst.setString(2, password_login.getText());
						
						ResultSet rs = pst.executeQuery();
						
//						int count = 0;
//						
//						while (rs.next()) {
//							count++;
//						}
//						
//						if (count == 1) {
						if (rs.next() == true) {
							JOptionPane.showMessageDialog(null, "Correct Admin");
							
							frame_login.dispose();
							
							admin_view ad_view = new admin_view();
							ad_view.setVisible(true);
							
						} else {
							JOptionPane.showMessageDialog(null, "Not Correct");
						}
					
						rs.close();
						pst.close();
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
//				finally{
//					 
//					 if(connection != null)
//						try {
//							connection.close();
//						} catch (SQLException e1) {
//							e1.printStackTrace();
//						}
//					 
//				}
				
				System.out.println(" am drunk\n");
			}
		});
		button_login_get_drunk.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		button_login_get_drunk.setHorizontalAlignment(SwingConstants.LEFT);
		button_login_get_drunk.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\get_drunk_icon.png"));
		button_login_get_drunk.setBounds(213, 356, 147, 35);
		frame_login.getContentPane().add(button_login_get_drunk);
		
		JLabel label_login_main_title = new JLabel("LIQUOR MANAGEMENT SYSTEM");
		label_login_main_title.setForeground(Color.ORANGE);
		label_login_main_title.setFont(new Font("Tahoma", Font.BOLD, 35));
		label_login_main_title.setBounds(10, 10, 563, 75);
		frame_login.getContentPane().add(label_login_main_title);
		
		JLabel label_login_background = new JLabel("");
		label_login_background.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_login_background.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_background_1000x600.jpg"));
//		Image img = new ImageIcon(this.getClass().getResource("/img/login_background_1000x600.jpg")).getImage();
//		label_login_background.setIcon(new ImageIcon(img));
		label_login_background.setBounds(0, 0, 996, 572);
		frame_login.getContentPane().add(label_login_background);
	}
}
