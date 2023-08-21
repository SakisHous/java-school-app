package gr.aueb.cf.schoolappsoa.viewcontroller;

import gr.aueb.cf.schoolappsoa.Main;
import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.LoginDTO;
import gr.aueb.cf.schoolappsoa.dto.UserInsertDTO;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.IUserService;
import gr.aueb.cf.schoolappsoa.service.UserServiceImpl;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserAlreadyExistsException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

/**
 * The first JFrame object that a user interacts.
 * A registered user can log in or a new user to be registered.
 * In addition, administrations can log in from this JFrame.
 * When a new user is registered, he has no role (teacher or student),
 * administration adds the roles for each.
 * 
 * @author Thanasis Chousiadas
 */
public class Login extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField usernameTxt;
	private JTextField usernameRegTxt;
	private JPasswordField passwordTxt;
	private JPasswordField passwordRegTxt;
	private JPasswordField confirmPassRegTxt;

	IUserDAO userDAO = new UserDAOImpl();
	IStudentDAO studentDAO = new StudentDAOImpl();
	ITeacherDAO teacherDAO = new TeacherDAOImpl();
	IUserService userService = new UserServiceImpl(userDAO, studentDAO, teacherDAO);

	/**
	 * The constructor of JFrame {@link Login} class.
	 */
	public Login() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("School Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 416, 184);
		contentPane.add(tabbedPane);
		
		JPanel login = new JPanel();
		tabbedPane.addTab("Login", null, login, null);
		login.setLayout(null);
		
		JLabel usernameLbl = new JLabel("Username:");
		usernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernameLbl.setBounds(27, 22, 89, 20);
		login.add(usernameLbl);
		
		usernameTxt = new JTextField();
		usernameTxt.setFont(new Font("Arial", Font.PLAIN, 15));
		usernameTxt.setBounds(125, 22, 136, 20);
		login.add(usernameTxt);
		usernameTxt.setColumns(10);
		
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setFont(new Font("Arial", Font.BOLD, 15));
		passwordLbl.setBounds(31, 54, 85, 20);
		login.add(passwordLbl);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {

			/**
			 * This callback makes use of the Service and checks
			 * if there is a registered user. If there is it set visible
			 * the next JFrame, otherwise it shows an appropriate message.
			 * 
			 * @param e		the event to be processed.
			 */
			public void actionPerformed(ActionEvent e) {
				String inputUsername = usernameTxt.getText().trim();
				String inputPasswd = String.valueOf(passwordTxt.getPassword()).trim();
				
				// checks if the user has filled the JTextFields
				if (inputUsername.equals("") || inputPasswd.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in username / password", "Login error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// checks if the user is an admin.
				// Admin has username "admin" and the password is saved in an 
				// env variable in the PC.
				if (inputUsername.matches("[aA]dmin")) {
					if (inputPasswd.equals(System.getenv("ST_ADMIN_PASSWD"))) {
						Main.getAdminMenu().setVisible(true);
						Main.getLoginForm().setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(null, "Password Error" , "Error" , JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					try {
						LoginDTO dto = new LoginDTO();
						dto.setUsername(inputUsername);
						dto.setPassword(inputPasswd);

						if (userService.login(dto)) {
							Main.getAdminMenu().setVisible(true);
							Main.getLoginForm().setEnabled(false);
						} else {
							JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Error", JOptionPane.PLAIN_MESSAGE);
							usernameTxt.setText("");
							passwordTxt.setText("");
						}
					} catch (UserDAOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Something went wrong with Insertion, please try again", "Error", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		loginBtn.setForeground(new Color(0, 0, 255));
		loginBtn.setFont(new Font("Arial", Font.BOLD, 15));
		loginBtn.setBounds(209, 98, 85, 30);
		login.add(loginBtn);
		
		passwordTxt = new JPasswordField();
		passwordTxt.setFont(new Font("Arial", Font.PLAIN, 15));
		passwordTxt.setBounds(125, 52, 136, 20);
		login.add(passwordTxt);
		
		JPanel register = new JPanel();
		tabbedPane.addTab("Register", null, register, null);
		register.setLayout(null);
		
		JLabel usernameRegLbl = new JLabel("Username:");
		usernameRegLbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernameRegLbl.setBounds(23, 30, 89, 20);
		register.add(usernameRegLbl);
		
		usernameRegTxt = new JTextField();
		usernameRegTxt.setFont(new Font("Arial", Font.PLAIN, 15));
		usernameRegTxt.setColumns(10);
		usernameRegTxt.setBounds(176, 30, 136, 20);
		register.add(usernameRegTxt);
		
		JLabel passwordRegLbl = new JLabel("Password:");
		passwordRegLbl.setFont(new Font("Arial", Font.BOLD, 15));
		passwordRegLbl.setBounds(23, 60, 89, 20);
		register.add(passwordRegLbl);
		
		JLabel confirmPassRegLbl = new JLabel("Confirm Password:");
		confirmPassRegLbl.setFont(new Font("Arial", Font.BOLD, 15));
		confirmPassRegLbl.setBounds(23, 90, 143, 20);
		register.add(confirmPassRegLbl);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.addActionListener(new ActionListener() {

			/**
			 * This callback makes uses of Service Layer
			 * for registration of a new user in the database.
			 * It makes a simple validation for the passwords.
			 * In addition, it checks if there is already a registered
			 * user with the same username. If there is one, it shows
			 * a message.
			 * 
			 * @param e		the received event from the user.
			 */
			public void actionPerformed(ActionEvent e) {
				String inputUsername = usernameRegTxt.getText().trim();
				String inputPasswd = String.valueOf(passwordRegTxt.getPassword());
				String confirmPasswd = String.valueOf(confirmPassRegTxt.getPassword());

				if (inputUsername.equals("") || inputPasswd.equals("") || confirmPasswd.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Register Error", JOptionPane.ERROR_MESSAGE);
				}

				if (!inputPasswd.equals(confirmPasswd)) {
					JOptionPane.showMessageDialog(null, "Passwords not match", "Password Error", JOptionPane.ERROR_MESSAGE);
				}

				try {
					UserInsertDTO dto = new UserInsertDTO(null, inputUsername, inputPasswd);
					User user = userService.insertUser(dto);

					if (user != null) {
						JOptionPane.showMessageDialog(null, "Successful registration", "Register", JOptionPane.INFORMATION_MESSAGE);
						usernameRegTxt.setText("");
						passwordRegTxt.setText("");
						confirmPassRegTxt.setText("");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "Error in register", "Error", JOptionPane.ERROR_MESSAGE);
					}

				} catch (UserAlreadyExistsException e1) {
					JOptionPane.showMessageDialog(null, "Username is already exists", "Register", JOptionPane.INFORMATION_MESSAGE);
				} catch (UserDAOException e2) {
					e2.printStackTrace();
				}
			}
		});
		registerBtn.setForeground(Color.BLUE);
		registerBtn.setFont(new Font("Arial", Font.BOLD, 15));
		registerBtn.setBounds(211, 120, 101, 30);
		register.add(registerBtn);
		
		passwordRegTxt = new JPasswordField();
		passwordRegTxt.setFont(new Font("Arial", Font.PLAIN, 15));
		passwordRegTxt.setBounds(176, 62, 136, 20);
		register.add(passwordRegTxt);
		
		confirmPassRegTxt = new JPasswordField();
		confirmPassRegTxt.setFont(new Font("Arial", Font.PLAIN, 15));
		confirmPassRegTxt.setBounds(176, 92, 136, 20);
		register.add(confirmPassRegTxt);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/resources/school.jpg")));
		lblNewLabel.setBounds(10, 200, 416, 63);
		contentPane.add(lblNewLabel);
	}
}
