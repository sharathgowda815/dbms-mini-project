import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class customer_payment extends JFrame {

	public static int current_cid = 0;
	private JPanel contentPane;
	private JTextField textField_cust_pay_cid;
	private customer_view customer_view_page = new customer_view(current_cid);
	public Login login_page = new Login(current_cid);
	private JTextField textField_cust_pay_oid;
	private JTextField textField_cust_pay_pid;
	private JTable table_cust_pay_cart_final;
	private String current_oid, current_pyid, total_price, pay_method = "Select Payment Mode";
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customer_payment frame_customer_payment = new customer_payment(current_cid);
					frame_customer_payment.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	Connection connection = null;
	
	public String get_order_id() {
		
		int oid_now = 0;
		
		try {
			
			String query = "call max_oid(@last_oid)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			
			query = "select @last_oid";
			pst = connection.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				oid_now = rs.getInt(1);
				
			}
			
			pst.close();
			rs.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
//		finally{
//			 
//			 if(connection != null)
//				try {
//					connection.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			 
//		}
		
		oid_now++;
		current_oid = Integer.toString(oid_now);
		
		return current_oid;
		
	}
	
	public String get_payment_id() {
		
		int pyid_now = 0;
		
		try {
			
			String query = "call max_pyid(@last_pyid)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			
			query = "select @last_pyid";
			pst = connection.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				pyid_now = rs.getInt(1);
				
			}
			
			pst.close();
			rs.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
//		finally{
//			 
//			 if(connection != null)
//				try {
//					connection.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			 
//		}
		
		pyid_now++;
		current_pyid = Integer.toString(pyid_now);
		
		return current_pyid;
		
	}
	
//	public void refreshCart_remove(JTable table_now) {
//		
//		try {
//			
//			String query = "select * from lms.temp_cart";
//			PreparedStatement pst = connection.prepareStatement(query);
//			pst = connection.prepareStatement(query);
//			
//			ResultSet rs = pst.executeQuery();
//			
//			table_now.setModel(DbUtils.resultSetToTableModel(rs));
//			
//			pst.close();
//			rs.close();
//			
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
//	}
	
	public String get_total_price() {
		
		int total_now = 0;
		
		try {
			
			String query = "call total_price(@total_price_final)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			
			query = "select @total_price_final";
			pst = connection.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				total_now = rs.getInt(1);
				
			}
			
			pst.close();
			rs.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
//		finally{
//			 
//			 if(connection != null)
//				try {
//					connection.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			 
//		}
		
		total_price = Integer.toString(total_now);
		
		return total_price;
		
	}
	
	public void clear_cart() {
		
		try {
			
			String query = "delete from lms.temp_cart";
			PreparedStatement pst = connection.prepareStatement(query);
			
			pst.execute();
			pst.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
//		finally{ 
//			 if(connection != null)
//				try {
//					connection.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			 
//		}
		
	}
	
//	public void fill_includes() {
//		
//		int total_now = 0;
//		
//		try {
//			
//			String query = "call count_temp_cart(@no_rows)";
//			PreparedStatement pst = connection.prepareStatement(query);
//			pst.execute();
//			
//			query = "select @no_rows";
//			pst = connection.prepareStatement(query);
//			
//			ResultSet rs = pst.executeQuery();
//			
//			while(rs.next()) {
//				
//				total_now = rs.getInt(1);
//				
//			}
//			
//			pst.close();
//			rs.close();
//			
//			for (int i = 0; i < total_now; i++) {
//				
//				query = "insert into includes values (?,?,?)";
//				pst = connection.prepareStatement(query);
//				pst.setString(1, table_cust_pay_cart_final.getValueAt(i, 0).toString());
//				pst.setString(2, current_oid);
//				pst.setString(3, table_cust_pay_cart_final.getValueAt(i, 2).toString());
//				
//				pst.execute();
//				pst.close();
//				
//			}
//			
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} 
//		
//	}

	/**
	 * Create the frame.
	 */
	public customer_payment(int cid_now) {
		
		current_cid = cid_now;
		System.out.println("cid ->" + current_cid );
		
		connection = mysql_connection.mysql_Connector();
		
		String total_now = get_total_price();
		
		setTitle("Payments");
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_frame_icon.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_customer_backtoview = new JButton("Back");
		button_customer_backtoview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To Go Back", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if(action == 0) {
					dispose();
					customer_view_page.setVisible(true);
				}
				
			}
		});
		
		JButton button_payment_logout = new JButton("Logout");
		button_payment_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To LOGOUT", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if(action == 0) {
					dispose();
					clear_cart();
					login_page.frame_login.setVisible(true);
				}
				
			}
		});
		
		JButton button_cust_pay_pay = new JButton("PAY");
		button_cust_pay_pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (pay_method == "Select Payment Mode") {
						
						JOptionPane.showMessageDialog(null, "Please Select Pay Mode", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
						
//						payment
						String query = "insert into lms.payment (pyid, method, pystatus) values (?,?, 'Pend')";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, current_pyid);
						pst.setString(2, pay_method);
						
						pst.execute();
						pst.close();
						
//						orders
						query = "insert into lms.orders (oid, pyid, cid, odate, total, dl_date, ostatus) values (?,?,?,?,?,?, 'Processing')";
						pst = connection.prepareStatement(query);
						pst.setString(1, current_oid);
						pst.setString(2, current_pyid);
						pst.setInt(3, current_cid);
						pst.setString(4, (java.time.LocalDate.now()).toString());
						pst.setString(5, total_now);
						pst.setString(6, ((java.time.LocalDate.now()).plusDays(1)).toString());
						
						pst.execute();
						pst.close();
						
						query = "select o.oid, o.pyid, o.cid, o.odate, o.total, p.method from lms.orders o, lms.payment p";
						pst = connection.prepareStatement(query);
						
						ResultSet rs = pst.executeQuery();
						
						while(rs.next()) {
							
							JOptionPane.showMessageDialog(null, "Payment Successful\n\nDetails: \nORDER ID: "+ rs.getString(1)+ "\nPAYMENT ID: "+rs.getString(2)+ "\nCUSTOMER ID: "+rs.getString(3)+"\nDATE: "+rs.getString(4)+ "\nTOTAL AMOUNT: "+ rs.getString(5) + ".00\n PAY METHOD: " + rs.getString(6) , "THANK YOU...", JOptionPane.WARNING_MESSAGE);
							break;
						}
						
						pst.close();
						rs.close();
						
						int action = JOptionPane.showConfirmDialog(null, "Do You Want To LOGOUT (Click 'YES' If You Like To Do SO)", "Confirm", JOptionPane.YES_NO_OPTION);
						
//						fill_includes();
						clear_cart();
						
						if(action == 0) {
							dispose();
							login_page.frame_login.setVisible(true);
						} else if (action == 1) {
							dispose();
							customer_view_page.setVisible(true);
						}
						
					}
					
					
				} catch (Exception e1) {
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
				
			}
		});
		button_cust_pay_pay.setFont(new Font("Tahoma", Font.BOLD, 30));
		button_cust_pay_pay.setBounds(726, 361, 181, 69);
		contentPane.add(button_cust_pay_pay);
		
		JLabel label_total_amt_int = new JLabel(total_now + ".00");
		label_total_amt_int.setFont(new Font("Tahoma", Font.BOLD, 35));
		label_total_amt_int.setForeground(Color.RED);
		label_total_amt_int.setBounds(769, 218, 237, 50);
		contentPane.add(label_total_amt_int);
		
		JLabel label_cust_pay_paymode = new JLabel("Payment Mode");
		label_cust_pay_paymode.setForeground(Color.ORANGE);
		label_cust_pay_paymode.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_cust_pay_paymode.setBounds(534, 100, 169, 34);
		contentPane.add(label_cust_pay_paymode);
		
		JComboBox comboBox_select_paymode = new JComboBox();
		comboBox_select_paymode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pay_method = (String)comboBox_select_paymode.getSelectedItem();
//				System.out.println(pay_method);
				
			}
		});
		comboBox_select_paymode.setModel(new DefaultComboBoxModel(new String[] {"Select Payment Mode", "Card", "Cash", "Online"}));
		comboBox_select_paymode.setMaximumRowCount(4);
		comboBox_select_paymode.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_select_paymode.setBounds(737, 100, 208, 34);
		contentPane.add(comboBox_select_paymode);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 348, 408, 131);
		contentPane.add(scrollPane);
		
		table_cust_pay_cart_final = new JTable();
		customer_view_page.refreshCart_remove(table_cust_pay_cart_final);
		scrollPane.setViewportView(table_cust_pay_cart_final);
		
		JLabel label_cust_pay_ordereditems_final = new JLabel("Ordered Items");
		label_cust_pay_ordereditems_final.setForeground(Color.ORANGE);
		label_cust_pay_ordereditems_final.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_cust_pay_ordereditems_final.setBounds(32, 295, 181, 34);
		contentPane.add(label_cust_pay_ordereditems_final);
		
		textField_cust_pay_pid = new JTextField(get_payment_id());
		textField_cust_pay_pid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_cust_pay_pid.setEditable(false);
		textField_cust_pay_pid.setBounds(211, 234, 195, 34);
		contentPane.add(textField_cust_pay_pid);
		textField_cust_pay_pid.setColumns(10);
		
		JLabel label_cust_pay_pId = new JLabel("Payment ID");
		label_cust_pay_pId.setForeground(Color.ORANGE);
		label_cust_pay_pId.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_cust_pay_pId.setBounds(32, 234, 138, 34);
		contentPane.add(label_cust_pay_pId);
		
		textField_cust_pay_oid = new JTextField(get_order_id());
		textField_cust_pay_oid.setEditable(false);
		textField_cust_pay_oid.setToolTipText("");
		textField_cust_pay_oid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_cust_pay_oid.setColumns(10);
		textField_cust_pay_oid.setBounds(211, 166, 195, 34);
		contentPane.add(textField_cust_pay_oid);
		
		JLabel label_cust_pay_oid = new JLabel("Order ID");
		label_cust_pay_oid.setForeground(Color.ORANGE);
		label_cust_pay_oid.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_cust_pay_oid.setBounds(32, 166, 138, 34);
		contentPane.add(label_cust_pay_oid);
		
		JLabel label_cust_pay_cid = new JLabel("Customer ID");
		label_cust_pay_cid.setForeground(Color.ORANGE);
		label_cust_pay_cid.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_cust_pay_cid.setBounds(32, 100, 138, 34);
		contentPane.add(label_cust_pay_cid);
		button_payment_logout.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_logout_icon_1.png"));
		button_payment_logout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_payment_logout.setBounds(125, 535, 120, 27);
		contentPane.add(button_payment_logout);
		button_customer_backtoview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_customer_backtoview.setBounds(22, 535, 92, 27);
		contentPane.add(button_customer_backtoview);
		
		textField_cust_pay_cid = new JTextField(Integer.toString(current_cid));
		textField_cust_pay_cid.setEditable(false);
		textField_cust_pay_cid.setToolTipText("CID");
		textField_cust_pay_cid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_cust_pay_cid.setBounds(211, 103, 195, 34);
		contentPane.add(textField_cust_pay_cid);
		textField_cust_pay_cid.setColumns(10);
		
		JLabel lblPaymentsSection = new JLabel("PAYMENTS SECTION");
		lblPaymentsSection.setForeground(new Color(255, 153, 51));
		lblPaymentsSection.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblPaymentsSection.setBounds(10, 10, 339, 69);
		contentPane.add(lblPaymentsSection);
		
		JLabel label_total_amt = new JLabel("Total Amount (INR):");
		label_total_amt.setForeground(Color.RED);
		label_total_amt.setFont(new Font("Tahoma", Font.BOLD, 25));
		label_total_amt.setBounds(534, 166, 277, 34);
		contentPane.add(label_total_amt);
		
		JLabel label_customer_payment_background = new JLabel("");
		label_customer_payment_background.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_payment_background.jpg"));
		label_customer_payment_background.setBounds(0, 0, 1046, 572);
		contentPane.add(label_customer_payment_background);
		
	}
}
