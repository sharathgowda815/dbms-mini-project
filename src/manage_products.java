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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class manage_products extends JFrame {

	private JPanel contentPane;
	private JTable table_listed_products;
	private JTextField textField_search_pid;
	private JTextField textField_search_pname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					manage_products frame = new manage_products();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTable table_mng_cat;
	private JTable table_mng_chart;
	
	public void refreshTable() {
		
		try {
			
			String query = "select p.pid as PID, cat.catname as CATEGORY, ch.bname as BRAND, p.pname as PRODUCT, p.unitprice as 'UNIT_PRICE(INR)', p.instock as In_Stock, p.netqt as 'NET_QUANTITY(ml)', p.alc_per as 'ALCHOL(%)' from lms.product p, lms.category cat, lms.chart ch where cat.catid = p.catid and ch.bid = p.bid order by p.pid";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			table_listed_products.setModel(DbUtils.resultSetToTableModel(rs));
			setTableSize();
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
	}
	
	public void refreshTablecat() {
		
		try {
			
			String query = "select catid as CATID, catname as CATEGORY from lms.category order by catid";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			table_mng_cat.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
	}
	
	public void refreshTablechart() {
		
		try {
			
			String query = "select bid as BID, bname as BRAND, world_con as 'WORLD CONSUMPTION (%)' from lms.chart order by bid";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			table_mng_chart.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
	}
	
	public void setTableSize() {
		
		try {
			table_listed_products.getColumnModel().getColumn(0).setPreferredWidth(5);
			table_listed_products.getColumnModel().getColumn(1).setPreferredWidth(115);
			table_listed_products.getColumnModel().getColumn(2).setPreferredWidth(94);
			table_listed_products.getColumnModel().getColumn(3).setPreferredWidth(140);
			table_listed_products.getColumnModel().getColumn(4).setPreferredWidth(40);
			table_listed_products.getColumnModel().getColumn(5).setPreferredWidth(10);
//			table_listed_products.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void searchPid(String search_in) {
		
		try {
			
			String query = "select p.pid as PID, cat.catname as CATEGORY, ch.bname as BRAND, p.pname as PRODUCT, p.unitprice as 'UNIT_PRICE(INR)', p.instock as In_Stock, p.netqt as 'NET_QUANTITY(ml)', p.alc_per as 'ALCHOL(%)' from lms.product p, lms.category cat, lms.chart ch where p.pid = ? and cat.catid = p.catid and ch.bid = p.bid";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, search_in);
			
			ResultSet rs = pst.executeQuery();
			
			table_listed_products.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void searchPname(String search_in) {
		
		try {
			
			String query = "select p.pid as PID, cat.catname as CATEGORY, ch.bname as BRAND, p.pname as PRODUCT, p.unitprice as 'UNIT_PRICE(INR)', p.instock as In_Stock, p.netqt as 'NET_QUANTITY(ml)', p.alc_per as 'ALCOHOL(%)' from lms.product p, lms.category cat, lms.chart ch where p.pname = ? and cat.catid = p.catid and ch.bid = p.bid";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, search_in);
			
			ResultSet rs = pst.executeQuery();
			
			table_listed_products.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void removeProd(String pid_input) {
		
		try {
			
			String query = "delete from lms.product where pid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, pid_input);
			pst.execute();
			
			pst.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
//	public int removeCat(String catid_input) {
//		
//		try {
//			
//			String query = "delete from lms.category where catid = ?";
//			PreparedStatement pst = connection.prepareStatement(query);
//			pst.setString(1, catid_input);
//			pst.execute();
//			
//			pst.close();
//			
//			return 1;
//			
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			return 0;
//		}
//		
//	}
	
	public int addProd(String pid_in, String pname_in, String bid_in, String catid_in, String unitprice_in, String net_in, String alc_in) {
		
		try {
			
			String query = "insert into lms.product (pname, bid, catid, unitprice, netqt, alc_per, instock, pid) values (?,?,?,?,?,?,'y',?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, pname_in);
			pst.setString(2, bid_in);
			pst.setString(3, catid_in);
			pst.setString(4, unitprice_in);
			pst.setString(5, net_in);
			pst.setString(6, alc_in);
			pst.setString(7, pid_in);

			pst.execute();
			
			pst.close();
			
			return 1;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;
		}
		
	}

	public int addCat(String catid_in, String catname_in) {
		
		try {
			
			String query = "insert into lms.category (catid, catname) values (?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, catid_in);
			pst.setString(2, catname_in);

			pst.execute();
			
			pst.close();
			
			return 1;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;
		}
		
	}
	
	public int addChart(String bid_in, String bname_in, String w_c) {
		
		try {
			
			String query = "insert into lms.chart values (?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, bid_in);
			pst.setString(2, bname_in);
			pst.setString(3, w_c);

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
	public manage_products() {
		
		connection = mysql_connection.mysql_Connector();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_frame_icon.png"));
		setResizable(false);
		setTitle("MANAGE PRODUCTS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_mng_prd_logout = new JButton("LOGOUT");
		button_mng_prd_logout.addActionListener(new ActionListener() {
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
		
		JScrollPane scrollPane_mng_chart = new JScrollPane();
		scrollPane_mng_chart.setBounds(776, 313, 249, 137);
		contentPane.add(scrollPane_mng_chart);
		
		table_mng_chart = new JTable();
		scrollPane_mng_chart.setViewportView(table_mng_chart);
		
		JLabel label_mng_charts = new JLabel("MANAGE CHARTS");
		label_mng_charts.setForeground(Color.ORANGE);
		label_mng_charts.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_mng_charts.setBounds(776, 279, 224, 31);
		contentPane.add(label_mng_charts);
		
		JButton btnAddBrand = new JButton("ADD BRAND");
		btnAddBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					JTextField bid_now = new JTextField(10);
				    JTextField bname_now = new JTextField(10);
				    JTextField world_con_now = new JTextField(10);

				    JPanel myPanel = new JPanel();
				    myPanel.add(new JLabel("Brand ID:"));
				    myPanel.add(bid_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Brand Name:"));
				    myPanel.add(bname_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("World Consumption:"));
				    myPanel.add(world_con_now);

				    int result = JOptionPane.showConfirmDialog(null, myPanel, "Add New Brand Chart", JOptionPane.OK_CANCEL_OPTION);
				    if (result == JOptionPane.OK_OPTION) {
				    	
				    	int action = addChart(bid_now.getText(), bname_now.getText(), world_con_now.getText());
				    	
				    	if (action == 1) {
							JOptionPane.showMessageDialog(null, "Brand Chart Added Successfully","Alert",JOptionPane.WARNING_MESSAGE);
						} else if (action == 0) {
							JOptionPane.showMessageDialog(null, "Addition Unsuccessfully","Alert",JOptionPane.WARNING_MESSAGE);
						}
				    	
				    	refreshTablechart();
				    	
				    }	
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnAddBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddBrand.setBounds(776, 468, 131, 31);
		contentPane.add(btnAddBrand);
		
		JLabel lblNewLabel = new JLabel("MANAGE CATEGORY");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(775, 22, 224, 31);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane_mng_cat = new JScrollPane();
		scrollPane_mng_cat.setBounds(777, 58, 248, 137);
		contentPane.add(scrollPane_mng_cat);
		
		table_mng_cat = new JTable();
		scrollPane_mng_cat.setViewportView(table_mng_cat);
		
//		JButton button_remove_cat = new JButton("REMOVE CATEGORY");
//		button_remove_cat.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				try {
//					
//					int row = table_mng_cat.getSelectedRow();			
//					
//					if(row < 0) {
//						
//						JOptionPane.showMessageDialog(null, "Please Select A Category To REMOVE", "Alert", JOptionPane.WARNING_MESSAGE);
//						
//					} else {
//						
//						int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To REMOVE This CATEGORY ?", "Confirm", JOptionPane.YES_NO_OPTION);
//						
//						if(action == 0) {
//							
//							String catid_now = (table_mng_cat.getModel().getValueAt(row, 0)).toString();
//							int result = removeCat(catid_now);
//							
//							if (result == 1) {
//								JOptionPane.showMessageDialog(null, "Category Removed Successfully","Alert",JOptionPane.WARNING_MESSAGE);
//							} else if (result == 0) {
//								JOptionPane.showMessageDialog(null, "Removal Unsuccessfully\nCheck Related Products","Alert",JOptionPane.WARNING_MESSAGE);
//							}
//							refreshTablecat();
//							
//						}
//						
//					}
//					
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				
//			}
//		});
//		button_remove_cat.setFont(new Font("Tahoma", Font.PLAIN, 15));
//		button_remove_cat.setBounds(774, 239, 184, 31);
//		contentPane.add(button_remove_cat);
		
		JButton button_add_cat = new JButton("ADD CATEGORY");
		button_add_cat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					JTextField catid_now = new JTextField(10);
				    JTextField catname_now = new JTextField(10);

				    JPanel myPanel = new JPanel();
				    myPanel.add(new JLabel("Category ID:"));
				    myPanel.add(catid_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Category Name:"));
				    myPanel.add(catname_now);

				    int result = JOptionPane.showConfirmDialog(null, myPanel, "Add New Category", JOptionPane.OK_CANCEL_OPTION);
				    if (result == JOptionPane.OK_OPTION) {
				    	
				    	int action = addCat(catid_now.getText(), catname_now.getText());
				    	
				    	if (action == 1) {
							JOptionPane.showMessageDialog(null, "Category Added Successfully","Alert",JOptionPane.WARNING_MESSAGE);
						} else if (action == 0) {
							JOptionPane.showMessageDialog(null, "Addition Unsuccessfully","Alert",JOptionPane.WARNING_MESSAGE);
						}
				    	
				    	refreshTablecat();
				    	
				    }	
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_add_cat.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_add_cat.setBounds(775, 210, 157, 31);
		contentPane.add(button_add_cat);
		
		JButton button_remove_prod = new JButton("REMOVE");
		button_remove_prod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int row = table_listed_products.getSelectedRow();			
					
					if(row < 0) {
						
						JOptionPane.showMessageDialog(null, "Please Select A Product To REMOVE", "Alert", JOptionPane.WARNING_MESSAGE);
						
					} else {
						
						int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To REMOVE This PRODUCT ?", "Confirm", JOptionPane.YES_NO_OPTION);
						
						if(action == 0) {
							
							String pid_now = (table_listed_products.getModel().getValueAt(row, 0)).toString();
							removeProd(pid_now);
							refreshTable();
							
						}
						
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_remove_prod.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_remove_prod.setBounds(597, 430, 141, 31);
		contentPane.add(button_remove_prod);
		
		JButton button_add_prod = new JButton("ADD");
		button_add_prod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					JTextField pname_now = new JTextField(10);
				    JTextField bid_now = new JTextField(10);
				    JTextField cat_now = new JTextField(10);
				    JTextField unitprice_now = new JTextField(10);
				    JTextField netqt_now = new JTextField(10);
				    JTextField alc_per_now = new JTextField(10);
				    JTextField pid_now = new JTextField(10);

				    JPanel myPanel = new JPanel();
				    myPanel.add(new JLabel("Product ID:"));
				    myPanel.add(pid_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Product Name:"));
				    myPanel.add(pname_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Brand ID:"));
				    myPanel.add(bid_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Category ID:"));
				    myPanel.add(cat_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Unit Price:"));
				    myPanel.add(unitprice_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Net Quantity(ml):"));
				    myPanel.add(netqt_now);
				    myPanel.add(Box.createVerticalStrut(15)); // a spacer
				    myPanel.add(new JLabel("Alcohol (%):"));
				    myPanel.add(alc_per_now);

				    int result = JOptionPane.showConfirmDialog(null, myPanel, "Add New Product", JOptionPane.OK_CANCEL_OPTION);
				    if (result == JOptionPane.OK_OPTION) {
				    	
				    	int action = addProd(pid_now.getText(), pname_now.getText(), bid_now.getText(), cat_now.getText(), unitprice_now.getText(), netqt_now.getText(), alc_per_now.getText());
				    	
				    	if (action == 1) {
							JOptionPane.showMessageDialog(null, "Product Added Successfully","Alert",JOptionPane.WARNING_MESSAGE);
						} else if (action == 0) {
							JOptionPane.showMessageDialog(null, "Addition Unsuccessfully\n Check PID or BID or CATID","Alert",JOptionPane.WARNING_MESSAGE);
						}
				    	
				    	refreshTable();
				    	
				    }	
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_add_prod.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_add_prod.setBounds(446, 430, 141, 31);
		contentPane.add(button_add_prod);
		
		JButton button_refresh_prod = new JButton("REFRESH");
		button_refresh_prod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshTable();
				
			}
		});
		button_refresh_prod.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_refresh_prod.setBounds(609, 69, 141, 31);
		contentPane.add(button_refresh_prod);
		
		JLabel label_search_pname = new JLabel("Product Name");
		label_search_pname.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_search_pname.setForeground(Color.YELLOW);
		label_search_pname.setBounds(228, 398, 146, 24);
		contentPane.add(label_search_pname);
		
		JLabel label_search_pid = new JLabel("Search Through PID");
		label_search_pid.setForeground(Color.YELLOW);
		label_search_pid.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_search_pid.setBounds(32, 398, 175, 24);
		contentPane.add(label_search_pid);
		
		textField_search_pname = new JTextField();
		textField_search_pname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					searchPname(textField_search_pname.getText());
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		textField_search_pname.setColumns(10);
		textField_search_pname.setBounds(228, 432, 198, 31);
		contentPane.add(textField_search_pname);
		
		textField_search_pid = new JTextField();
		textField_search_pid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					searchPid(textField_search_pid.getText());
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		textField_search_pid.setBounds(32, 432, 175, 31);
		contentPane.add(textField_search_pid);
		textField_search_pid.setColumns(10);
		
		JLabel label_listed_prod = new JLabel("LISTED PRODUCTS");
		label_listed_prod.setForeground(Color.ORANGE);
		label_listed_prod.setFont(new Font("Tahoma", Font.BOLD, 30));
		label_listed_prod.setBounds(32, 33, 352, 55);
		contentPane.add(label_listed_prod);
		
		JScrollPane scrollPane_listed_products = new JScrollPane();
		scrollPane_listed_products.setBounds(32, 110, 718, 270);
		contentPane.add(scrollPane_listed_products);
		
		table_listed_products = new JTable();
		scrollPane_listed_products.setViewportView(table_listed_products);
//		table_listed_products.getColumnModel().getColumn(0).setPreferredWidth(5);
//		table_listed_products.getColumnModel().getColumn(1).setPreferredWidth(115);
//		table_listed_products.getColumnModel().getColumn(2).setPreferredWidth(94);
//		table_listed_products.getColumnModel().getColumn(3).setPreferredWidth(140);
//		table_listed_products.getColumnModel().getColumn(4).setPreferredWidth(40);
//		table_listed_products.getColumnModel().getColumn(5).setPreferredWidth(10);
		
		
		button_mng_prd_logout.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_logout_icon_1.png"));
		button_mng_prd_logout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_mng_prd_logout.setBounds(149, 531, 157, 31);
		contentPane.add(button_mng_prd_logout);
		
		JButton button_mng_prd_back = new JButton("BACK");
		button_mng_prd_back.addActionListener(new ActionListener() {
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
		button_mng_prd_back.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_mng_prd_back.setBounds(10, 531, 113, 31);
		contentPane.add(button_mng_prd_back);
		
		JLabel label_mng_prd_background = new JLabel("");
		label_mng_prd_background.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\admin_view_background_1046x574.jpg"));
		label_mng_prd_background.setBounds(0, 0, 1046, 574);
		contentPane.add(label_mng_prd_background);
		
		refreshTable();
		refreshTablecat();
		refreshTablechart();
		
	}
}
