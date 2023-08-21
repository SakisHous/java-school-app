package gr.aueb.cf.schoolappsoa.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolappsoa.Main;
import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.exceptions.*;
import gr.aueb.cf.schoolappsoa.dto.UserUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.IUserService;
import gr.aueb.cf.schoolappsoa.service.UserServiceImpl;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserNotFoundException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.List;
import javax.swing.JPasswordField;

/**
 * This class shows the result of the searching from 
 * {@link AdminInsertUsersForm} when admin has clicked 
 * the search button. In this JFrame, admin can update or 
 * delete a user.
 * 
 * @author Thanasis Chousiadas
 */
public class AdminUpdateDeleteUsersForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idTxt;
	private JTextField usernameTxt;
	private JPasswordField inputPasswdTxt;
	private JPasswordField confirmPasswdTxt;
	private List<User> users;
	private int listSize;
	private int listPosition;

	IUserDAO userDAO = new UserDAOImpl();
	IStudentDAO studentDAO = new StudentDAOImpl();
	ITeacherDAO teacherDAO = new TeacherDAOImpl();
	IUserService userService = new UserServiceImpl(userDAO, studentDAO, teacherDAO);

	/**
	 * The constructor of JFrame {@link AdminUpdateDeleteUsersForm} class.
	 */
	public AdminUpdateDeleteUsersForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		addWindowListener(new WindowAdapter() {

			/**
			 * This callback is called when window is opened.
			 * It makes use of Service Layer and outputs a user (or users) with
			 * the lastname given by the admin. It shows all the information
			 * for the user.
			 *
			 * @param e			a {@link WindowEvent} object.
			 */
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					users = userService.getUsersByUsernameLike(Main.getUsersMenu().getUsername());
					listSize = users.size();
					
					if (listSize == 0) {
						JOptionPane.showMessageDialog(null, "Empty, no users found", "Users", JOptionPane.INFORMATION_MESSAGE);
						Main.getUsersMenu().setEnabled(true);
						Main.getAdminUpdateDeleteUsersForm().setVisible(false);
						return;
					}

					listPosition = 0;
					String username = users.get(listPosition).getUsername();
					Long id = users.get(listPosition).getId();
					
					idTxt.setText(Long.toString(id));
					usernameTxt.setText(username);
					
				} catch(UserDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 368);
		setTitle("Update - Delete Users");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null); 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel idLbl = new JLabel("ID:");
		idLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		idLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		idLbl.setFont(new Font("Arial", Font.BOLD, 15));
		idLbl.setBounds(77, 36, 90, 20);
		contentPane.add(idLbl);
		
		idTxt = new JTextField();
		idTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		idTxt.setEditable(false);
		idTxt.setColumns(10);
		idTxt.setBackground(new Color(255, 255, 205));
		idTxt.setBounds(198, 36, 59, 20);
		contentPane.add(idTxt);
		
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		passwordLbl.setFont(new Font("Arial", Font.BOLD, 15));
		passwordLbl.setBounds(27, 96, 140, 20);
		contentPane.add(passwordLbl);
		
		JLabel confirmPassLbl = new JLabel("Confirm Password:");
		confirmPassLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		confirmPassLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		confirmPassLbl.setFont(new Font("Arial", Font.BOLD, 15));
		confirmPassLbl.setBounds(10, 130, 157, 20);
		contentPane.add(confirmPassLbl);
		
		JButton firstBtn = new JButton("");
		firstBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button for the 
			 * first user in the list, if there are 
			 * more than one, given by the username parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = 0;
					String username = users.get(listPosition).getUsername();
					Long id = users.get(listPosition).getId();
					idTxt.setText(Long.toString(id));
					usernameTxt.setText(username);
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/First_Record.png")));
		firstBtn.setBounds(196, 173, 49, 30);
		contentPane.add(firstBtn);
		
		JButton prevBtn = new JButton("");
		prevBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button
			 * for the previous user in the list.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition --;
					String username = users.get(listPosition).getUsername();
					Long id = users.get(listPosition).getId();
					idTxt.setText(Long.toString(id));
					usernameTxt.setText(username);
				}
			}
		});
		prevBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Previous_Record.png")));
		prevBtn.setBounds(243, 173, 49, 30);
		contentPane.add(prevBtn);
		
		JButton nextBtn = new JButton("");
		nextBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button
			 * for the next teacher in the list, if there are 
			 * more than one, given by the username parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listPosition <= listSize - 2) {
					listPosition++;
					String username = users.get(listPosition).getUsername();
					Long id = users.get(listPosition).getId();
					idTxt.setText(Long.toString(id));
					usernameTxt.setText(username);
				}
			}
		});
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Next_Record.png")));
		nextBtn.setBounds(289, 173, 49, 30);
		contentPane.add(nextBtn);
		
		JButton lastBtn = new JButton("");
		lastBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button for the 
			 * last user in the list, if there are 
			 * more than one, given by the username parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = listSize - 1;
					String username = users.get(listPosition).getUsername();
					Long id = users.get(listPosition).getId();
					idTxt.setText(Long.toString(id));
					usernameTxt.setText(username);
				}
			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Last_Record.png")));
		lastBtn.setBounds(329, 173, 49, 30);
		contentPane.add(lastBtn);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {

			/**
			 * This callback gets the services from Service Layer
			 * and updates a user in the database after
			 * performing some validations.
			 *
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				String username = usernameTxt.getText().trim();
				String password = String.valueOf(inputPasswdTxt.getPassword());
				String confirmPass = String.valueOf(confirmPasswdTxt.getPassword());
				
				if (username.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in the username", "User Update", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (password.equals("") || confirmPass.equals("")) {
					JOptionPane.showMessageDialog(null,  "Please fill in the fields to update the password", "User Update",
							JOptionPane.INFORMATION_MESSAGE);
					inputPasswdTxt.setText("");
					confirmPasswdTxt.setText("");
					return;
				}

				if (!password.equals(confirmPass)) {
					JOptionPane.showMessageDialog(null, "Passwords not match", "Password Error", JOptionPane.ERROR_MESSAGE);
					inputPasswdTxt.setText("");
					confirmPasswdTxt.setText("");
					return;
				}

				try {
					UserUpdateDTO dto = new UserUpdateDTO();
					dto.setId(Long.parseLong(idTxt.getText()));
					dto.setUsername(username);
					dto.setPassword(password);

					User user = userService.updateUser(dto);
					if (user != null) {
						JOptionPane.showMessageDialog(null, "Successfully update the password", "User Update", JOptionPane.INFORMATION_MESSAGE);
					}
				}catch (UserDAOException | UserNotFoundException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateBtn.setForeground(Color.BLUE);
		updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
		updateBtn.setBounds(27, 268, 90, 30);
		contentPane.add(updateBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {

			/**
			 * This callback gets the services from Service Layer
			 * and deletes a user from the database.
			 *
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				long id = Long.parseLong(idTxt.getText());

				try {
					int response;

					response = JOptionPane.showConfirmDialog(null, "Are you sure for deleting the user?", "Warning", JOptionPane.YES_NO_OPTION);

					if (response == JOptionPane.YES_OPTION) {
						userService.deleteUser(id);
						JOptionPane.showMessageDialog(null, "User was deleted successfully", "DELETE", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (UserNotFoundException | TeacherDAOException | UserDAOException | StudentDAOException | CityDAOException |
						 SpecialityDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error Deletion", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(Color.BLUE);
		deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
		deleteBtn.setBounds(160, 268, 90, 30);
		contentPane.add(deleteBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {

			/**
			 * This callback closes the {@link AdminUpdateDeleteUsersForm}
			 * and opens the previous JFrame {@link UsersMenu}.
			 *
			 * @param e			an {@link ActionEvent} object,
			 *                  the event to be processed.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getAdminUpdateDeleteUsersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(288, 268, 90, 30);
		contentPane.add(closeBtn);
		
		JLabel usernameLbl = new JLabel("Username*:");
		usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		usernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernameLbl.setBounds(27, 66, 140, 20);
		contentPane.add(usernameLbl);
		
		usernameTxt = new JTextField();
		usernameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameTxt.setColumns(10);
		usernameTxt.setBounds(198, 66, 180, 20);
		contentPane.add(usernameTxt);
		
		inputPasswdTxt = new JPasswordField();
		inputPasswdTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		inputPasswdTxt.setBounds(198, 98, 180, 20);
		contentPane.add(inputPasswdTxt);
		
		confirmPasswdTxt = new JPasswordField();
		confirmPasswdTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		confirmPasswdTxt.setBounds(198, 132, 180, 20);
		contentPane.add(confirmPasswdTxt);

	}
}
