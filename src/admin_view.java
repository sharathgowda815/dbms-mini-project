import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class admin_view extends JFrame {

	private JPanel contentPane;
	private Login login_page = new Login(0);
	private manage_customers manage_customers_page = new manage_customers();
	private manage_orders manage_orders_page = new manage_orders();
	private manage_products manage_products_page = new manage_products();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					admin_view frame = new admin_view();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public admin_view() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\login_frame_icon.png"));
		setTitle("ADMIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_ad_view_mngprd = new JButton("Manage Products");
		button_ad_view_mngprd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					dispose();
					manage_products_page.setVisible(true);
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_ad_view_mngprd.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_ad_view_mngprd.setBounds(576, 346, 286, 67);
		contentPane.add(button_ad_view_mngprd);
		
		JButton button_ad_view_mngord = new JButton("Manage Orders");
		button_ad_view_mngord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					dispose();
					manage_orders_page.setVisible(true);
					
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_ad_view_mngord.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_ad_view_mngord.setBounds(335, 217, 286, 67);
		contentPane.add(button_ad_view_mngord);
		
		JButton button_ad_view_mngcust = new JButton("Manage Customers");
		button_ad_view_mngcust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					dispose();
					manage_customers_page.setVisible(true);
						
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_ad_view_mngcust.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_ad_view_mngcust.setBounds(121, 96, 286, 67);
		contentPane.add(button_ad_view_mngcust);
		
		JLabel label_ad_view_overview = new JLabel("OVERVIEW");
		label_ad_view_overview.setForeground(Color.ORANGE);
		label_ad_view_overview.setFont(new Font("Tahoma", Font.BOLD, 30));
		label_ad_view_overview.setBounds(10, 10, 185, 41);
		contentPane.add(label_ad_view_overview);
		
		JButton button_ad_view_logout = new JButton("LOGOUT");
		button_ad_view_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int action = JOptionPane.showConfirmDialog(null, "Do You Really Want To LOGOUT", "Confirm", JOptionPane.YES_NO_OPTION);
					
					if(action == 0) {
						dispose();
						login_page.frame_login.setVisible(true);
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button_ad_view_logout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_ad_view_logout.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\customer_logout_icon_1.png"));
		button_ad_view_logout.setBounds(10, 525, 147, 28);
		contentPane.add(button_ad_view_logout);
		
		JLabel label_ad_view_background = new JLabel("");
		label_ad_view_background.setIcon(new ImageIcon("E:\\Users\\Sharath Gowda\\eclipse-workspace\\Liquor_Management_System\\img\\admin_view_background_1046x574.jpg"));
		label_ad_view_background.setBounds(0, 0, 1046, 574);
		contentPane.add(label_ad_view_background);
	}
}
