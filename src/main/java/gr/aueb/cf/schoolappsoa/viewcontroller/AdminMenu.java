package gr.aueb.cf.schoolappsoa.viewcontroller;


import gr.aueb.cf.schoolappsoa.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.net.URL;

/**
 * The main menu for the school app.
 * All users are logged in. For the next version,
 * only admin can be logged in this page.
 * Teachers and students are directed to a profile page.
 * 
 * @author Thanasis Chousiadas
 */
public class AdminMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * The constructor of JFrame {@link AdminMenu} class.
	 */
	public AdminMenu() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Admin Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton meetingsBtn = new JButton("");
		meetingsBtn.addActionListener(new ActionListener() {
			/**
			 * This callback opens the Meetings Menu.
			 */
			public void actionPerformed(ActionEvent e) {
				//TODO: Meetings Menu
			}
		});
		meetingsBtn.setBounds(32, 27, 35, 35);
		contentPane.add(meetingsBtn);
		
		JLabel lblNewLabel = new JLabel("Meetings");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(81, 27, 113, 35);
		contentPane.add(lblNewLabel);
		
		JButton teachersBtn = new JButton("");
		teachersBtn.addActionListener(new ActionListener() {

			/**
			 * This callback opens the Teachers Menu
			 * for searching teachers by lastname or
			 * inserting new teacher for a user without role.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersMenu().setVisible(true);
				Main.getAdminMenu().setEnabled(false);
			}
		});
		teachersBtn.setBounds(32, 72, 35, 35);
		contentPane.add(teachersBtn);
		
		JLabel lblTeachers = new JLabel("Teachers");
		lblTeachers.setForeground(new Color(0, 128, 0));
		lblTeachers.setFont(new Font("Arial", Font.BOLD, 14));
		lblTeachers.setBounds(81, 72, 113, 35);
		contentPane.add(lblTeachers);
		
		JButton studentsBtn = new JButton("");
		studentsBtn.addActionListener(new ActionListener() {

			/**
			 * This callback opens the Students Menu
			 * for searching students by lastname or
			 * inserting a new student.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenu().setVisible(true);
				Main.getAdminMenu().setEnabled(false);
			}
		});
		studentsBtn.setBounds(32, 117, 35, 35);
		contentPane.add(studentsBtn);
		
		JLabel lblStudents = new JLabel("Students");
		lblStudents.setForeground(new Color(0, 128, 0));
		lblStudents.setFont(new Font("Arial", Font.BOLD, 14));
		lblStudents.setBounds(81, 117, 113, 35);
		contentPane.add(lblStudents);
		
		JButton usersBtn = new JButton("");
		usersBtn.addActionListener(new ActionListener() {
			/**
			 * This callback opens the Users Menu
			 * for searching teachers by username or
			 * inserting new user.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setVisible(true);
				Main.getAdminMenu().setEnabled(false);
			}
		});
		usersBtn.setBounds(220, 27, 35, 35);
		contentPane.add(usersBtn);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setForeground(new Color(0, 128, 0));
		lblUsers.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsers.setBounds(280, 27, 113, 35);
		contentPane.add(lblUsers);
		
		JButton citiesBtn = new JButton("");
		citiesBtn.addActionListener(new ActionListener() {
			/**
			 * This callback opens the Cities Menu.
			 */
			public void actionPerformed(ActionEvent e) {
				//TODO: Cities Menu
			}
		});
		citiesBtn.setBounds(220, 72, 35, 35);
		contentPane.add(citiesBtn);
		
		JLabel lblCities = new JLabel("Cities");
		lblCities.setForeground(new Color(0, 128, 0));
		lblCities.setFont(new Font("Arial", Font.BOLD, 14));
		lblCities.setBounds(280, 72, 113, 35);
		contentPane.add(lblCities);
		
		JButton specialitiesBtn = new JButton("");
		specialitiesBtn.addActionListener(new ActionListener() {
			/**
			 * This callback opens the Specialities Menu.
			 * @param e			a {@link ActionEvent} object.
			 */
			public void actionPerformed(ActionEvent e) {
				//TODO: Speciality Menu
			}
		});
		specialitiesBtn.setBounds(220, 117, 35, 35);
		contentPane.add(specialitiesBtn);
		
		JLabel lblSpecialities = new JLabel("Specialities");
		lblSpecialities.setForeground(new Color(0, 128, 0));
		lblSpecialities.setFont(new Font("Arial", Font.BOLD, 14));
		lblSpecialities.setBounds(280, 117, 113, 35);
		contentPane.add(lblSpecialities);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 0));
		separator.setBounds(392, 189, -359, 1);
		contentPane.add(separator);
		
		JButton closeBtn = new JButton("Close App");
		closeBtn.addActionListener(new ActionListener() {
			/**
			 * This callback closes the app.
			 */
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(252, 227, 113, 35);
		contentPane.add(closeBtn);
	}
}
