package gr.aueb.cf.schoolappsoa.viewcontroller;

import gr.aueb.cf.schoolappsoa.Main;
import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.UserInsertDTO;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.IUserService;
import gr.aueb.cf.schoolappsoa.service.UserServiceImpl;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserAlreadyExistsException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 * This JFrame class adds a new User in the 
 * database.
 * 
 * @author Thanasis Chousiadas
 */
public class AdminInsertUsersForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTxt;
	private JPasswordField passwordTxt;
	private JPasswordField confirmPasswdTxt;

	IUserDAO userDAO = new UserDAOImpl();
	IStudentDAO studentDAO = new StudentDAOImpl();
	ITeacherDAO teacherDAO = new TeacherDAOImpl();
	IUserService userService = new UserServiceImpl(userDAO, studentDAO, teacherDAO);

	/**
	 * The constructor of JFrame {@link AdminInsertUsersForm} class.
	 */
	public AdminInsertUsersForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Insert User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel usernameLbl = new JLabel("Username:");
		usernameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		usernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernameLbl.setBounds(45, 31, 90, 20);
		contentPane.add(usernameLbl);
		
		usernameTxt = new JTextField();
		usernameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameTxt.setColumns(10);
		usernameTxt.setBounds(210, 31, 180, 20);
		contentPane.add(usernameTxt);
		
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		passwordLbl.setFont(new Font("Arial", Font.BOLD, 15));
		passwordLbl.setBounds(45, 89, 90, 20);
		contentPane.add(passwordLbl);
		
		JLabel confirmPasswdLbl = new JLabel("Confirm Password:");
		confirmPasswdLbl.setHorizontalAlignment(SwingConstants.LEFT);
		confirmPasswdLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		confirmPasswdLbl.setFont(new Font("Arial", Font.BOLD, 15));
		confirmPasswdLbl.setBounds(45, 119, 143, 20);
		contentPane.add(confirmPasswdLbl);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {

			/**
			 * This callback gets the services from Service Layer
			 * for adding a new teacher in the DB.
			 * It makes validations for the inputs in order to proceed.
			 *
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {

				String inputUsername = usernameTxt.getText().trim();
				String inputPasswd = String.valueOf(passwordTxt.getPassword());
				String confirmPasswd = String.valueOf(confirmPasswdTxt.getPassword());
				
				if (inputUsername.equals("") || inputPasswd.equals("") || confirmPasswd.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Insert Error", JOptionPane.ERROR_MESSAGE);
				}
				
				try {
					UserInsertDTO dto = new UserInsertDTO();

					if (!inputPasswd.equals(confirmPasswd)) {
						JOptionPane.showMessageDialog(null, "Passwords not match", "Password Error", JOptionPane.ERROR_MESSAGE);
					}
					dto.setUsername(inputUsername);
					dto.setPassword(inputPasswd);

					User user = userService.insertUser(dto);
					if (user != null) {
						JOptionPane.showMessageDialog(null, "User was successfully inserted",
								"User Inser", JOptionPane.INFORMATION_MESSAGE);
						usernameTxt.setText("");
						passwordTxt.setText("");
						confirmPasswdTxt.setText("");
					}
				} catch (UserAlreadyExistsException | UserDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error Insert", JOptionPane.ERROR_MESSAGE);
					usernameTxt.setText("");
					passwordTxt.setText("");
					confirmPasswdTxt.setText("");
				}
			}
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Arial", Font.BOLD, 14));
		insertBtn.setBounds(162, 209, 90, 30);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback closes {@link AdminInsertUsersForm}
			 * and enables {@link UsersMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getAdminInsertUsersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(300, 209, 90, 30);
		contentPane.add(closeBtn);
		
		passwordTxt = new JPasswordField();
		passwordTxt.setBounds(210, 89, 180, 20);
		contentPane.add(passwordTxt);
		
		confirmPasswdTxt = new JPasswordField();
		confirmPasswdTxt.setBounds(210, 121, 180, 20);
		contentPane.add(confirmPasswdTxt);
	}
}
