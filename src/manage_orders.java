import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;

public class manage_orders extends JFrame {

	private JPanel contentPane;
	private JTable table_all_orders;
	private JTable table_ord_pro;
	private JTextField textField_search_oid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					manage_orders frame = new manage_orders();
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
			
			String query = "select o.oid as OID, o.cid as CID, py.pystatus as Pay_Status, o.odate as Ordered_Date, o.ostatus as Status_, o.dl_date as Delivery, o.total as Total from lms.orders o, lms.payment py where o.pyid = py.pyid";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			table_all_orders.setModel(DbUtils.resultSetToTableModel(rs));
			
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

	public void search_order(String oid_input) {
		
		try {
			
			String query = "select o.oid as OID, o.cid as CID, py.pystatus as Pay_Status, o.odate as Ordered_Date, o.ostatus as Status_, o.dl_date as Delivery, o.total as Total from lms.orders o, lms.payment py where o.oid = ? and o.pyid = py.pyid";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, oid_input);
			ResultSet rs = pst.executeQuery();
			
			table_all_orders.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void refreshProductsTabel(String oid_input) {
		
		try {
			
			String query = "select p.pid as PID, p.pname as Product_Name, p.unitprice as Price from lms.orders o, lms.product p, lms.includes inc where o.oid = ? and inc.oid = o.oid and p.pid = inc.pid";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, oid_input);
			ResultSet rs = pst.executeQuery();
			
			table_ord_pro.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
	}
	
	public void removeOrder(String oid_input) {
		
		try {
			
			String query = "delete from lms.orders where oid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, oid_input);
			pst.execute();
			
			pst.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public int approvePayment(String oid_input, String new_pay_stst) {
		
		try {
			
			String query = "update lms.payment set pystatus = ? where pyid = (select pyid from lms.orders where oid = ?);";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, new_pay_stst);
			pst.setString(2, oid_input);
			pst.execute();
			
			pst.close();
			
			return 1;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;
		}
		
	}
	
	public int approveOrder(String oid_input, String new_ord_stst) {
		
		try {
			
			String query = "update lms.orders set ostatus = ? where oid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, new_ord_stst);
			pst.setString(2, oid_input);
			pst.execute();
			
			pst.close();
			
			return 1;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	/**
	 * Create the frame.
	 */
	public manage_orders() {
		
		connection = mysql_connection.mysql_Connector();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_frame_icon.png"));
		setResizable(false);
		setTitle("MANAGE ORDERS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_mng_ord_logout = new JButton("LOGOUT");
		button_mng_ord_logout.addActionListener(new ActionListener() {
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
		
		JButton button_refresh_ord = new JButton("REFRESH");
		button_refresh_ord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshTable();
				
			}
		});
		
		JButton button_app_ord = new JButton("Approve Order");
		button_app_ord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_all_orders.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Please Select A Order To Approve ORDER", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
							
							String oid_now = (table_all_orders.getModel().getValueAt(row, 0)).toString();
							
							try {
								
							    JTextField ordstatus_now = new JTextField(10);

							    JPanel myPanel = new JPanel();
							    myPanel.add(new JLabel("New Order Status:"));
							    myPanel.add(ordstatus_now);
							    myPanel.add(Box.createVerticalStrut(15)); // a spacer
							    myPanel.add(new JLabel("Processing / Processed"));

							    int result = JOptionPane.showConfirmDialog(null, myPanel, "Approve Order", JOptionPane.OK_CANCEL_OPTION);
							    if (result == JOptionPane.OK_OPTION) {
							    	
							    	if (ordstatus_now.getText() == null || ordstatus_now.getText().trim().isEmpty()) {
										
										JOptionPane.showMessageDialog(null, "Please Enter A Valid Status","Alert",JOptionPane.WARNING_MESSAGE);
										
									} else {
										
										int action = approveOrder(oid_now, ordstatus_now.getText());
										
										if (action == 1) {
											JOptionPane.showMessageDialog(null, "Order Status Approved Successfully","Alert",JOptionPane.WARNING_MESSAGE);
										} else if (action == 0) {
											JOptionPane.showMessageDialog(null, "Order Status Approval Unsuccessfully","Alert",JOptionPane.WARNING_MESSAGE);
										}
										
								    	refreshTable();
										
									}
							    	
							    }	
								
							} catch (HeadlessException e1) {
								e1.printStackTrace();
							}
						
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_app_ord.setFont(new Font("Tahoma", Font.PLAIN, 18));
		button_app_ord.setBounds(768, 163, 192, 37);
		contentPane.add(button_app_ord);
		
		JButton button_app_pay = new JButton("Approve Payment");
		button_app_pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_all_orders.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Please Select A Order To Approve Payment", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
							
							String oid_now = (table_all_orders.getModel().getValueAt(row, 0)).toString();
							
							try {
								
							    JTextField pystatus_now = new JTextField(10);

							    JPanel myPanel = new JPanel();
							    myPanel.add(new JLabel("New Payment Status:"));
							    myPanel.add(pystatus_now);
							    myPanel.add(Box.createVerticalStrut(15)); // a spacer
							    myPanel.add(new JLabel("Pend / Done"));

							    int result = JOptionPane.showConfirmDialog(null, myPanel, "Approve Payment", JOptionPane.OK_CANCEL_OPTION);
							    if (result == JOptionPane.OK_OPTION) {
							    	
							    	if (pystatus_now.getText() == null || pystatus_now.getText().trim().isEmpty()) {
										
										JOptionPane.showMessageDialog(null, "Please Enter A Valid Status","Alert",JOptionPane.WARNING_MESSAGE);
										
									} else {
										
										int action = approvePayment(oid_now, pystatus_now.getText());
										
										if (action == 1) {
											JOptionPane.showMessageDialog(null, "Payment Status Approved Successfully","Alert",JOptionPane.WARNING_MESSAGE);
										} else if (action == 0) {
											JOptionPane.showMessageDialog(null, "Payment Status Approval Unsuccessfully","Alert",JOptionPane.WARNING_MESSAGE);
										}
										
								    	refreshTable();
										
									}
							    	
							    }	
								
							} catch (HeadlessException e1) {
								e1.printStackTrace();
							}
						
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_app_pay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		button_app_pay.setBounds(768, 106, 192, 37);
		contentPane.add(button_app_pay);
		button_refresh_ord.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_refresh_ord.setBounds(472, 45, 143, 37);
		contentPane.add(button_refresh_ord);
		
		JButton button_mng_ord_search = new JButton("SEARCH");
		button_mng_ord_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (textField_search_oid.getText() == null || textField_search_oid.getText().trim().isEmpty()) {
						
						JOptionPane.showMessageDialog(null, "Please Enter OID To Search For","Alert",JOptionPane.WARNING_MESSAGE);
						
					} else if (isInteger(textField_search_oid.getText()) != true) {
						
						JOptionPane.showMessageDialog(null, "Please Enter A Valid OID To Search For","Alert",JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						search_order(textField_search_oid.getText());
						
					}
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_mng_ord_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_mng_ord_search.setBounds(243, 424, 143, 37);
		contentPane.add(button_mng_ord_search);
		
		JLabel label_search_in_oid = new JLabel("Input Order ID");
		label_search_in_oid.setForeground(Color.ORANGE);
		label_search_in_oid.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_search_in_oid.setBounds(34, 386, 157, 28);
		contentPane.add(label_search_in_oid);
		
		textField_search_oid = new JTextField();
		textField_search_oid.setBounds(34, 424, 157, 31);
		contentPane.add(textField_search_oid);
		textField_search_oid.setColumns(10);
		
		JScrollPane scrollPane_ord_pro = new JScrollPane();
		scrollPane_ord_pro.setBounds(679, 376, 335, 169);
		contentPane.add(scrollPane_ord_pro);
		
		table_ord_pro = new JTable();
		scrollPane_ord_pro.setViewportView(table_ord_pro);
		
		JButton button_mng_ord_viewprod = new JButton("VIEW INCLUDED PRODUCTS");
		button_mng_ord_viewprod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_all_orders.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Please Select A Order To View Included Products", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						String oid_now = (table_all_orders.getModel().getValueAt(row, 0)).toString();
						refreshProductsTabel(oid_now);
						
					}
				
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_mng_ord_viewprod.setFont(new Font("Tahoma", Font.PLAIN, 17));
		button_mng_ord_viewprod.setBounds(733, 301, 281, 37);
		contentPane.add(button_mng_ord_viewprod);
		
		JButton button_mng_ord_delete = new JButton("REMOVE");
		button_mng_ord_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_all_orders.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Please Select A Order To REMOVE", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To REMOVE This ORDER ?", "Confirm", JOptionPane.YES_NO_OPTION);
						
						if(action == 0) {
							
							String oid_now = (table_all_orders.getModel().getValueAt(row, 0)).toString();
							removeOrder(oid_now);
							refreshTable();
							
						}
						
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_mng_ord_delete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_mng_ord_delete.setBounds(796, 236, 143, 37);
		contentPane.add(button_mng_ord_delete);
		
//		JButton button_mng_ord_edit = new JButton("EDIT");
//		button_mng_ord_edit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				try {
//					
//					int row = table_all_orders.getSelectedRow();			
//					
//					if(row < 0) {
//						
//						JOptionPane.showMessageDialog(null, "Please Select A Order To Approve", "Alert", JOptionPane.WARNING_MESSAGE);
//						
//					} else {
//							
//							String oid_now = (table_all_orders.getModel().getValueAt(row, 0)).toString();
//							editOrder(oid_now);
//							refreshTable();
//							
//							try {
//								
//								JTextField ostatus_now = new JTextField(10);
//							    JTextField pys_now = new JTextField(10);
//							    JTextField dob_now = new JTextField(10);
//							    JTextField ph_now = new JTextField(10);
//							    JTextField city_now = new JTextField(10);
//
//							    JPanel myPanel = new JPanel();
//							    myPanel.add(new JLabel("Customer Name:"));
//							    myPanel.add(cname_now);
//							    myPanel.add(Box.createVerticalStrut(15)); // a spacer
//							    myPanel.add(new JLabel("Password:"));
//							    myPanel.add(pass_now);
//							    myPanel.add(Box.createVerticalStrut(15)); // a spacer
//							    myPanel.add(new JLabel("DOB:"));
//							    myPanel.add(dob_now);
//							    myPanel.add(Box.createVerticalStrut(15)); // a spacer
//							    myPanel.add(new JLabel("Phone:"));
//							    myPanel.add(ph_now);
//							    myPanel.add(Box.createVerticalStrut(15)); // a spacer
//							    myPanel.add(new JLabel("City:"));
//							    myPanel.add(city_now);
//
//							    int result = JOptionPane.showConfirmDialog(null, myPanel, 
//							    		"Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
//							    if (result == JOptionPane.OK_OPTION) {
////							    	System.out.println("x value: " + cname_now.getText());
////							    	System.out.println("y value: " + pass_now.getText());
////							    	System.out.println("y value: " + dob_now.getText());
////							    	System.out.println("y value: " + ph_now.getText());
////							    	System.out.println("y value: " + city_now.getText());
//							    	
//							    	addCust(cname_now.getText(), pass_now.getText(), dob_now.getText(), ph_now.getText(), city_now.getText());
//							    	refreshTable();
//							    	
//							    }	
//								
//							} catch (HeadlessException e1) {
//								e1.printStackTrace();
//							}
//						
//					}
//					
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				
//			}
//		});
//		button_mng_ord_edit.setFont(new Font("Tahoma", Font.PLAIN, 20));
//		button_mng_ord_edit.setBounds(798, 100, 143, 37);
//		contentPane.add(button_mng_ord_edit);
		
		JLabel label_all_orders = new JLabel("ALL ORDERS");
		label_all_orders.setForeground(Color.ORANGE);
		label_all_orders.setFont(new Font("Tahoma", Font.BOLD, 30));
		label_all_orders.setBounds(34, 20, 250, 54);
		contentPane.add(label_all_orders);
		
		JScrollPane scrollPane_all_orders = new JScrollPane();
		scrollPane_all_orders.setBounds(34, 96, 581, 260);
		contentPane.add(scrollPane_all_orders);
		
		table_all_orders = new JTable();
		table_all_orders.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane_all_orders.setViewportView(table_all_orders);
		button_mng_ord_logout.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_logout_icon_1.png"));
		button_mng_ord_logout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_mng_ord_logout.setBounds(149, 531, 157, 31);
		contentPane.add(button_mng_ord_logout);
		
		JButton button_mng_ord_back = new JButton("BACK");
		button_mng_ord_back.addActionListener(new ActionListener() {
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
		button_mng_ord_back.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_mng_ord_back.setBounds(10, 531, 113, 31);
		contentPane.add(button_mng_ord_back);
		
		JLabel label_mng_ord_background = new JLabel("");
		label_mng_ord_background.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\admin_view_background_1046x574.jpg"));
		label_mng_ord_background.setBounds(0, 0, 1046, 574);
		contentPane.add(label_mng_ord_background);
		
		refreshTable();
		
	}
}
