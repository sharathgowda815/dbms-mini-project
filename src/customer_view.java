import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.JComboBox;

public class customer_view extends JFrame {

	public int current_cid = 0;
	private JPanel contentPane;
	public Login login_page = new Login(current_cid);
	private JTable table_customers_products_list;
	public String current_pid = null;
	public String current_pname;
	private JComboBox comboBox_search_cat = new JComboBox();
	


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customer_view frame_customer_view = new customer_view(0);
					frame_customer_view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTable table_customer_cart;
	
	public void refreshTable() {
		
		try {
			
			String query = "select pid as 'No.', pname as 'Product_Name', alc_per as 'Alcohol_Percentage(%)', unitprice as 'Unit_Price(Rs.)', instock as In_Stock, netqt as 'Net_Quantity(ml)' from product";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			table_customers_products_list.setModel(DbUtils.resultSetToTableModel(rs));
			
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
		
	}
	
	public void refreshCart_add(String no_units) {
		
		try {
			
			String query = "insert into lms.temp_cart values (?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, current_pid);
			pst.setString(2, current_pname);
			pst.setString(3, no_units);
			
			pst.execute();
			pst.close();
			
			query = "select pid as 'No.', pname as Product, no_units as No_Units from lms.temp_cart";
			pst = connection.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			
			table_customer_cart.setModel(DbUtils.resultSetToTableModel(rs));
			
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
		
	}
	
	public void refreshCart_remove(JTable table_now) {
		
		try {
			
			String query = "select * from lms.temp_cart";
			PreparedStatement pst = connection.prepareStatement(query);
			pst = connection.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			
			table_now.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
//		 finally{
//			 
//			 if(connection != null)
//				try {
//					connection.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			 
//		}
		
	}
	
	public void clear_cart() {
		
		try {
			
			String query = "delete from lms.temp_cart";
			PreparedStatement pst = connection.prepareStatement(query);
			
			pst.execute();
			pst.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{ 
			 if(connection != null)
				try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			 
		}
	}
	
	public void fillComboBox() {
		
		try {
			
			String query = "select catname from lms.category";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				comboBox_search_cat.addItem(rs.getString(1));
				
			}
			
			pst.close();
			rs.close();
			
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public customer_view(int cid_now) {
		
		current_cid = cid_now;
		System.out.println("cid ->" + current_cid );

		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_frame_icon.png"));
		
		connection = mysql_connection.mysql_Connector();
		
		setResizable(false);
		setTitle("Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_customer_logout = new JButton("Logout");
		button_customer_logout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_customer_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To LOGOUT", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if(action == 0) {
					dispose();
					clear_cart();
					login_page.frame_login.setVisible(true);
				}
				
			}
		});
		
		JButton button_customer_removefromcart = new JButton("Remove From Cart");
		button_customer_removefromcart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_customer_cart.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Cart Empty \nOR\n Cart Item Not Selected ", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						String PID_ = (table_customer_cart.getModel().getValueAt(row, 0)).toString();
						
						String query = "delete from lms.temp_cart where pid = '"+PID_+"' ";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.execute();
						
						pst.close();
						
						refreshCart_remove(table_customer_cart);
						
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
		
		JButton button_refresh = new JButton("REFRESH");
		button_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshTable();
				
			}
		});
		button_refresh.setFont(new Font("Tahoma", Font.PLAIN, 17));
		button_refresh.setBounds(675, 436, 142, 30);
		contentPane.add(button_refresh);
		
		JLabel lblNewLabel = new JLabel("SEARCH BY CATEGORY");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(26, 431, 267, 42);
		contentPane.add(lblNewLabel);
		
		comboBox_search_cat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select p.pid as 'No.', p.pname as 'Product_Name', p.alc_per as 'Alcohol_Percentage(%)', p.unitprice as 'Unit_Price(Rs.)', p.instock as In_Stock, p.netqt as 'Net_Quantity(ml)' from lms.product p , lms.category cat where cat.catname = ? and p.catid = cat.catid";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, (String)comboBox_search_cat.getSelectedItem());
					ResultSet rs = pst.executeQuery();
					
					table_customers_products_list.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		comboBox_search_cat.setBounds(324, 436, 208, 37);
		contentPane.add(comboBox_search_cat);
		button_customer_removefromcart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_customer_removefromcart.setBounds(855, 375, 134, 21);
		contentPane.add(button_customer_removefromcart);
		
		JLabel label_customer_yourcart = new JLabel("Your Cart");
		label_customer_yourcart.setForeground(new Color(204, 204, 0));
		label_customer_yourcart.setFont(new Font("Tahoma", Font.ITALIC, 25));
		label_customer_yourcart.setBounds(853, 59, 111, 21);
		contentPane.add(label_customer_yourcart);
		
		JButton button_customer_addtocart = new JButton("Add To Cart");
		button_customer_addtocart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (current_pid != null) {
						
						String query = "select * from lms.temp_cart where pid=?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, current_pid);
						
						ResultSet rs = pst.executeQuery();
						
						int count = 0;
						
						while(rs.next()) {
							count++;
						}
						
						if(count == 1) {
							
							JOptionPane.showMessageDialog(null, "Duplicate Addition", "Alert", JOptionPane.WARNING_MESSAGE);
						
						} else {
							
							String no_units = JOptionPane.showInputDialog(null,"Enter Number Of Units");
//							System.out.println(no_units);
							refreshCart_add(no_units);
							
						}
						
						pst.close();
						rs.close();
						
					} else {
						
						JOptionPane.showMessageDialog(null, "Please Select A Item", "Alert", JOptionPane.WARNING_MESSAGE);
						
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
		button_customer_addtocart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_customer_addtocart.setBounds(855, 334, 122, 21);
		contentPane.add(button_customer_addtocart);
		
		JScrollPane scrollPane_customer_cart = new JScrollPane();
		scrollPane_customer_cart.setBounds(853, 104, 170, 171);
		contentPane.add(scrollPane_customer_cart);
		
		table_customer_cart = new JTable();
		scrollPane_customer_cart.setViewportView(table_customer_cart);
		
		JButton button_customer_buy = new JButton("BUY");
		button_customer_buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select pid from lms.temp_cart";
					PreparedStatement pst = connection.prepareStatement(query);
					
					ResultSet rs = pst.executeQuery();
					
					int count = 0;
					
					while(rs.next()) {
						count++;
					}
					
					if(count > 0) {
						
//						JOptionPane.showMessageDialog(null, "Proceeding To Payment");
						int action = JOptionPane.showConfirmDialog(null, "Proceeding To Payment\n(Click 'YES' To Continue)", "Confirm", JOptionPane.YES_NO_OPTION);
						
						if (action == 0) {
							
							dispose();
							customer_payment cust_pay = new customer_payment(current_cid);
							cust_pay.setVisible(true);
							
						}
					
					} else {
						
						JOptionPane.showMessageDialog(null, "Cart Empty", "Alert", JOptionPane.WARNING_MESSAGE);
						
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally{
					 
					 if(connection != null)
						try {
							connection.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					 
				}
				
			}
		});
		button_customer_buy.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_customer_buy.setBounds(860, 471, 153, 37);
		contentPane.add(button_customer_buy);
		
		JScrollPane scrollPane_customer_product_list = new JScrollPane();
		scrollPane_customer_product_list.setBounds(26, 104, 791, 302);
		contentPane.add(scrollPane_customer_product_list);
		
		table_customers_products_list = new JTable();
		table_customers_products_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					
					int row = table_customers_products_list.getSelectedRow();
					String PID_ = (table_customers_products_list.getModel().getValueAt(row, 0)).toString();
					
					current_pid = PID_;
					current_pname = (table_customers_products_list.getModel().getValueAt(row, 1)).toString();
					
//					String query = "select catid, bid from product where pid = '"+PID_+"'  ";
//					PreparedStatement pst = connection.prepareStatement(query);
//					
//					ResultSet rs = pst.executeQuery();
//					
//					while(rs.next()) {
//						
//						int cat_id = rs.getInt(1);
//						int b_id = rs.getInt(2);
//						
//					}
					
					String query = "select catname, bname, world_con from category ca, chart ch, product p  where p.pid = '"+PID_+"' and ca.catid = p.catid and ch.bid = p.bid ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						
						JOptionPane.showMessageDialog(null, "DETAILS\nCATEGORY: " + rs.getString(1) + "\nBRAND: " + rs.getString(2) + "\nWORLD CONSUMPTION: " + rs.getString(3) + " %");
						
					}
					
					rs.close();
					pst.close();
					
				} catch(Exception e1) {
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
		scrollPane_customer_product_list.setViewportView(table_customers_products_list);
		table_customers_products_list.setColumnSelectionAllowed(true);
		table_customers_products_list.setCellSelectionEnabled(true);
		
		JLabel label_customer_products = new JLabel("Products");
		label_customer_products.setForeground(new Color(210, 105, 30));
		label_customer_products.setFont(new Font("Tahoma", Font.BOLD, 30));
		label_customer_products.setBounds(26, 10, 142, 54);
		contentPane.add(label_customer_products);
		button_customer_logout.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_logout_icon_1.png"));
		button_customer_logout.setBounds(10, 532, 114, 30);
		contentPane.add(button_customer_logout);
		
		JLabel label_customer_view_background = new JLabel("");
		label_customer_view_background.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_view_background_1046x572.jpg"));
		label_customer_view_background.setBounds(0, 0, 1046, 572);
		contentPane.add(label_customer_view_background);
		
		fillComboBox();
		refreshTable();
		refreshCart_remove(table_customer_cart);
		
	}
}
