package gr.aueb.cf.schoolappsoa.viewcontroller;

import gr.aueb.cf.schoolappsoa.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.net.URL;

/**
 * This JFrame is called if the user has 
 * clicked the Users Button from the
 * {@link AdminMenu} JFrame class.
 * The user can search for a user with the username
 * in the database or add a new user.
 * 
 * @author Thanasis Chousiadas.
 */
public class UsersMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTxt;
	private String username;

	/**
	 * The constructor of JFrame {@link UsersMenu} class.
	 */
	public UsersMenu() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Users Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		searchPanel.setBounds(26, 30, 404, 159);
		contentPane.add(searchPanel);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLbl.setFont(new Font("Arial", Font.BOLD, 14));
		usernameLbl.setBounds(151, 24, 100, 17);
		searchPanel.add(usernameLbl);
		
		usernameTxt = new JTextField();
		usernameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameTxt.setColumns(10);
		usernameTxt.setBounds(76, 51, 250, 30);
		searchPanel.add(usernameTxt);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {

			/**
			 * This callback sets visible the {@link AdminUpdateDeleteUsersForm},
			 * where the results of the searching are shown.
			 *
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				username = usernameTxt.getText();
				Main.getUsersMenu().setEnabled(false);
				Main.getAdminUpdateDeleteUsersForm().setVisible(true);
			}
		});
		searchBtn.setForeground(Color.BLUE);
		searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
		searchBtn.setBounds(151, 106, 100, 30);
		searchPanel.add(searchBtn);
		
		JPanel insertPanel = new JPanel();
		insertPanel.setLayout(null);
		insertPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		insertPanel.setBounds(26, 215, 404, 78);
		contentPane.add(insertPanel);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {

			/**
			 * This callback set visible the {@link AdminInsertUsersForm}
			 * JFrame class where the admin can add a new user.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getAdminInsertUsersForm().setVisible(true);
				Main.getUsersMenu().setEnabled(false);
			}
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Arial", Font.BOLD, 14));
		insertBtn.setBounds(152, 24, 100, 30);
		insertPanel.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			/**
			 * This callback closes this JFrame and sets
			 * visible the {@link UsersMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getAdminMenu().setEnabled(true);
				Main.getUsersMenu().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(330, 314, 100, 30);
		contentPane.add(closeBtn);
	}
	
	/**
	 * Getter for user username.
	 * It can be used from other classes
	 * for executing queries with username parameter.
	 * 
	 * @return		a String with the user's input.
	 */
	public String getUsername() {
		return username;
	}
}
