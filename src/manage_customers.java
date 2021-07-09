import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Toolkit;


public class manage_customers extends JFrame {

	private JPanel contentPane;
	private JTable table_existing_cust;
	private JTable table_view_orders;
	private JTable table_arc_cust;
	private JTextField textField_search_cust;
//	private admin_view admin_view_page = new admin_view();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					manage_customers frame = new manage_customers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	
	public void refreshTable() {
		
		try {
			
			String query = "select cid as CID, cname as Customer_Name, pass as Password, dob as 'Date_Of_Birth', phone as Phone, address as City from lms.customer";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			table_existing_cust.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
	}
	
	public void refreshOrderTabel(String cid_input) {
		
		try {
			
			String query = "select o.oid as OID, o.ostatus as Ordered_Status, py.pyid as Payment_ID, py.pystatus as Payment from lms.orders o, lms.payment py where o.cid = ? and o.pyid = py.pyid;";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, cid_input);
			ResultSet rs = pst.executeQuery();
			
			table_view_orders.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
	}
	
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	public void search_cust(String cid_input) {
		
		try {
			
			String query = "select cid as CID, cname as Customer_Name, pass as Password, dob as Date_Of_Birth, phone as Phone, address as City from lms.customer where cid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, cid_input);
			ResultSet rs = pst.executeQuery();
			
			table_existing_cust.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void removeCust(String cid_input) {
		
		try {
			
			String query = "delete from lms.customer where cid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, cid_input);
			pst.execute();
			
			pst.close();
			
		} catch (SQLException e1) {
//			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable To Remove Customer\nHas Records In Orders","Alert",JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
//	public void updateCust(String cid_in, String cname_in, String pass_in, String dob_in, String ph_in, String city_in) {
//		
//		try {
//			
//			String query = "update lms.customer set cname = ?, pass = ?, dob = ?, phone = ?, address = ? where cid = ?";
//			PreparedStatement pst = connection.prepareStatement(query);
//			pst.setString(1, cname_in);
//			pst.setString(2, pass_in);
//			pst.setString(3, dob_in);
//			pst.setString(4, ph_in);
//			pst.setString(5, city_in);
//			pst.setString(6, cid_in);
//
//			pst.execute();
//			
//			pst.close();
//			
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		
//	}
	
	public void addCust(String cname_in, String pass_in, String dob_in, String ph_in, String city_in) {
		
		try {
			
			String query = "insert into lms.customer (cname, pass, dob, phone, address) values (?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, cname_in);
			pst.setString(2, pass_in);
			pst.setString(3, dob_in);
			pst.setString(4, ph_in);
			pst.setString(5, city_in);

			pst.execute();
			
			pst.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void viewArcCust() {
		
		try {
			
			String query = "select * from lms.customer_archive";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			table_arc_cust.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	

	/**
	 * Create the frame.
	 */
	public manage_customers() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_frame_icon.png"));
		
		connection = mysql_connection.mysql_Connector();
		
		setResizable(false);
		setTitle("MANAGE CUSTOMERS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_mng_cust_logout = new JButton("LOGOUT");
		button_mng_cust_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To LOGOUT", "Confirm", JOptionPane.YES_NO_OPTION);
					
					if(action == 0) {
						dispose();
						Login login_page = new Login(0);
						login_page.frame_login.setVisible(true);
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton button_search_cust = new JButton("SEARCH");
		button_search_cust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (textField_search_cust.getText() == null || textField_search_cust.getText().trim().isEmpty()) {
						
						JOptionPane.showMessageDialog(null, "Please Enter CID To Search For","Alert",JOptionPane.WARNING_MESSAGE);
						
					} else if (isInteger(textField_search_cust.getText()) != true) {
						
						JOptionPane.showMessageDialog(null, "Please Enter A Valid CID To Search For","Alert",JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						search_cust(textField_search_cust.getText());
						
					}
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
			}
					
		});
		
		JButton button_refresh = new JButton("REFRESH");
		button_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					refreshTable();
				
			}
		});
		
		JButton button_arc_rec = new JButton("Archived Recordes");
		button_arc_rec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					table_arc_cust = new JTable();
					table_arc_cust.setFont(new Font("Tahoma", Font.PLAIN, 15));
					JScrollPane scrollPane_arc_cust = new JScrollPane(table_arc_cust);
					scrollPane_arc_cust.setViewportView(table_arc_cust);

				    JPanel myPanel = new JPanel();
				    myPanel.setLayout(new BorderLayout());
				    myPanel.add(scrollPane_arc_cust, BorderLayout.CENTER);
				    
				    viewArcCust();
				    
				    JOptionPane.showMessageDialog(null, myPanel, "ARCHIVED CUSTOMERS", JOptionPane.OK_OPTION);	
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_arc_rec.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_arc_rec.setBounds(863, 10, 173, 31);
		contentPane.add(button_arc_rec);
		button_refresh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_refresh.setBounds(489, 40, 151, 26);
		contentPane.add(button_refresh);
		
		JLabel label_in_cid = new JLabel("Input Customer ID");
		label_in_cid.setForeground(Color.ORANGE);
		label_in_cid.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_in_cid.setBounds(28, 392, 157, 26);
		contentPane.add(label_in_cid);
		button_search_cust.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_search_cust.setBounds(225, 433, 126, 26);
		contentPane.add(button_search_cust);
		
		textField_search_cust = new JTextField();
		textField_search_cust.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_search_cust.setToolTipText("INPUT PID");
		textField_search_cust.setBounds(28, 428, 140, 31);
		contentPane.add(textField_search_cust);
		textField_search_cust.setColumns(10);
		
		JScrollPane scrollPane_view_orders = new JScrollPane();
		scrollPane_view_orders.setBounds(548, 382, 470, 168);
		contentPane.add(scrollPane_view_orders);
		
		table_view_orders = new JTable();
		table_view_orders.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane_view_orders.setViewportView(table_view_orders);
		
		JButton button_mng_cust_view_orders = new JButton("VIEW ORDERS");
		button_mng_cust_view_orders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_existing_cust.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Please Select A Customer To View Their Orders", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						String cid_now = (table_existing_cust.getModel().getValueAt(row, 0)).toString();
						refreshOrderTabel(cid_now);
						
					}
				
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_mng_cust_view_orders.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_mng_cust_view_orders.setBounds(781, 297, 173, 31);
		contentPane.add(button_mng_cust_view_orders);
		
//		JButton button_mng_cust_update = new JButton("UPDATE");
//		button_mng_cust_update.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				try {
//					
//					int row = table_existing_cust.getSelectedRow();			
//					
//					if(row < 0) {
//						
//						JOptionPane.showMessageDialog(null, "Please Select A Customer To Be UPDATED", "Alert", JOptionPane.WARNING_MESSAGE);
//						
//					} else {
//						
//						int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To Update This Customer Info ?", "Confirm", JOptionPane.YES_NO_OPTION);
//						
//						if(action == 0) {
//							
////							SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
////							String dob_now = f.format(dob_now_o);
//							
//							String cid_now = "", cname_now = "", pass_now = "", dob_now = "", ph_now = "", city_now = "";
//							
//							try {
//								cid_now = (table_existing_cust.getModel().getValueAt(row, 0)).toString();
//								cname_now = (table_existing_cust.getModel().getValueAt(row, 1)).toString();
//								pass_now = (table_existing_cust.getModel().getValueAt(row, 2)).toString();
//								dob_now = (table_existing_cust.getModel().getValueAt(row, 3)).toString();
//								ph_now = (table_existing_cust.getModel().getValueAt(row, 4)).toString();
//								city_now = (table_existing_cust.getModel().getValueAt(row, 5)).toString();
//							} catch (NullPointerException e1) {
////								e1.printStackTrace();
//								
//								if (cname_now == null) {
//									cname_now = "";
//								} else if (pass_now == null) {
//									pass_now = "";
//								} else if (dob_now == null) {
//									dob_now = "NULL";
//								} else if (ph_now == null) {
//									ph_now = "";
//								} else if (city_now == null) {
//									city_now = "";
//								}	
//							}
//							
//							updateCust(cid_now, cname_now, pass_now, dob_now, ph_now, city_now);
//							
//							refreshTable();
//							
////							String dob_now = (table_existing_cust.getModel().getValueAt(row, 3)).toString();
////							System.out.println(dob_now);
//							
//						}
//						
//					}
//				
//				} catch (HeadlessException e1) {
//					e1.printStackTrace();
//				}
//				
//			}
//		});
//		button_mng_cust_update.setFont(new Font("Tahoma", Font.PLAIN, 20));
//		button_mng_cust_update.setBounds(781, 165, 157, 31);
//		contentPane.add(button_mng_cust_update);
		
		JButton button_mng_cust_remove = new JButton("REMOVE");
		button_mng_cust_remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_existing_cust.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Please Select A Customer To REMOVE", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To REMOVE This Customer ?", "Confirm", JOptionPane.YES_NO_OPTION);
						
						if(action == 0) {
							
							String cid_now = (table_existing_cust.getModel().getValueAt(row, 0)).toString();
							removeCust(cid_now);
							refreshTable();
							
						}
						
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_mng_cust_remove.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_mng_cust_remove.setBounds(781, 198, 157, 31);
		contentPane.add(button_mng_cust_remove);
		
		JButton button_mng_cust_add = new JButton("ADD");
		button_mng_cust_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					JTextField cname_now = new JTextField(10);
				    JTextField pass_now = new JTextField(10);
				    JTextField dob_now = new JTextField(10);
				    JTextField ph_now = new JTextField(10);
				    JTextField city_now = new JTextField(10);

				    JPanel myPanel = new JPanel();
				    myPanel.add(new JLabel("Customer Name:"));
				    myPanel.add(cname_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Password:"));
				    myPanel.add(pass_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("DOB:"));
				    myPanel.add(dob_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Phone:"));
				    myPanel.add(ph_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("City:"));
				    myPanel.add(city_now);

				    int result = JOptionPane.showConfirmDialog(null, myPanel, 
				    		"Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
				    if (result == JOptionPane.OK_OPTION) {
//				    	System.out.println("x value: " + cname_now.getText());
//				    	System.out.println("y value: " + pass_now.getText());
//				    	System.out.println("y value: " + dob_now.getText());
//				    	System.out.println("y value: " + ph_now.getText());
//				    	System.out.println("y value: " + city_now.getText());
				    	
				    	addCust(cname_now.getText(), pass_now.getText(), dob_now.getText(), ph_now.getText(), city_now.getText());
				    	refreshTable();
				    	
				    }	
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_mng_cust_add.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_mng_cust_add.setBounds(781, 137, 157, 31);
		contentPane.add(button_mng_cust_add);
		
		JLabel label_existing_cust = new JLabel("EXISTING CUSTOMERS");
		label_existing_cust.setForeground(Color.ORANGE);
		label_existing_cust.setFont(new Font("Tahoma", Font.BOLD, 30));
		label_existing_cust.setBounds(28, 10, 360, 55);
		contentPane.add(label_existing_cust);
		
		JScrollPane scrollPane_existing_cust = new JScrollPane();
		scrollPane_existing_cust.setBounds(29, 74, 613, 287);
		contentPane.add(scrollPane_existing_cust);
		
		table_existing_cust = new JTable();
		table_existing_cust.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane_existing_cust.setViewportView(table_existing_cust);
		button_mng_cust_logout.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_logout_icon_1.png"));
		button_mng_cust_logout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_mng_cust_logout.setBounds(149, 531, 157, 31);
		contentPane.add(button_mng_cust_logout);
		
		JButton button_mng_cust_back = new JButton("BACK");
		button_mng_cust_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					dispose();
					admin_view admin_view_page = new admin_view();
					admin_view_page.setVisible(true);
						
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_mng_cust_back.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_mng_cust_back.setBounds(10, 531, 113, 31);
		contentPane.add(button_mng_cust_back);
		
		JLabel label_mng_cust_background = new JLabel("");
		label_mng_cust_background.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\admin_view_background_1046x574.jpg"));
		label_mng_cust_background.setBounds(0, 0, 1046, 574);
		contentPane.add(label_mng_cust_background);
		
		refreshTable();
		
	}
}
