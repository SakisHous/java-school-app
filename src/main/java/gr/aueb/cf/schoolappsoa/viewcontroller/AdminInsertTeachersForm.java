package gr.aueb.cf.schoolappsoa.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolappsoa.Main;
import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.*;
import gr.aueb.cf.schoolappsoa.validator.TeacherValidator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This JFrame class adds a new Teacher in the 
 * database.
 * 
 * @author Thanasis Chousiadas
 */
public class AdminInsertTeachersForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField ssnTxt;
	private JComboBox<String> specialityComboBox = new JComboBox<String>();
	private Map<String, Long> specialtiesMap;
	private DefaultComboBoxModel<String> specialitiesModel;
	private JComboBox<String> usernameComboBox = new JComboBox<String>();
	private Map<String, Long> usernamesMap;
	private DefaultComboBoxModel<String> usernamesModel;

	ITeacherDAO teacherDAO = new TeacherDAOImpl();
	ISpecialityDAO specialtyDAO = new SpecialityDAOImpl();
	IUserDAO userDAO = new UserDAOImpl();
	IStudentDAO studentDAO = new StudentDAOImpl();
	ITeacherService teacherService = new TeacherServiceImpl(teacherDAO, specialtyDAO, userDAO);
	ISpecialityService specialtyService = new SpecialityServiceImpl(specialtyDAO);
	IUserService userService = new UserServiceImpl(userDAO, studentDAO, teacherDAO);

	/**
	 * The constructor of JFrame {@link AdminInsertTeachersForm} class.
	 */
	public AdminInsertTeachersForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Insert Teacher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel firstnameLbl = new JLabel("Firstname:");
		firstnameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		firstnameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		firstnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		firstnameLbl.setBounds(43, 31, 90, 20);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		firstnameTxt.setColumns(10);
		firstnameTxt.setBounds(160, 31, 180, 20);
		contentPane.add(firstnameTxt);
		
		JLabel lastnameLbl = new JLabel("Lastname:");
		lastnameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		lastnameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		lastnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		lastnameLbl.setBounds(43, 61, 90, 20);
		contentPane.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(160, 61, 180, 20);
		contentPane.add(lastnameTxt);
		
		JLabel ssnLbl = new JLabel("SSN:");
		ssnLbl.setHorizontalAlignment(SwingConstants.LEFT);
		ssnLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		ssnLbl.setFont(new Font("Arial", Font.BOLD, 15));
		ssnLbl.setBounds(43, 91, 90, 20);
		contentPane.add(ssnLbl);
		
		ssnTxt = new JTextField();
		ssnTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		ssnTxt.setColumns(10);
		ssnTxt.setBounds(160, 91, 180, 20);
		contentPane.add(ssnTxt);
		
		JLabel specialityLbl = new JLabel("Speciality:");
		specialityLbl.setHorizontalAlignment(SwingConstants.LEFT);
		specialityLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		specialityLbl.setFont(new Font("Arial", Font.BOLD, 15));
		specialityLbl.setBounds(43, 128, 90, 20);
		contentPane.add(specialityLbl);
		
		specialityComboBox.addFocusListener(new FocusAdapter() {

			/**
			 * This callback gets the services from Service Layer,
			 * regarding the search for specialities.
			 *
			 * @param e			an {@link FocusEvent} object, user's event.
			 */
			@Override
			public void focusGained(FocusEvent e) {
				List<Speciality> specialties;
				specialtiesMap = new HashMap<>();
				try{
					specialties = specialtyService.getAllSpecialities();
					specialitiesModel = new DefaultComboBoxModel<>();

					for (Speciality speciality : specialties) {
						specialtiesMap.put(speciality.getSpecialty(), speciality.getId());
						specialitiesModel.addElement(speciality.getSpecialty());
					}

					specialityComboBox.setModel(specialitiesModel);
					specialityComboBox.setMaximumRowCount(5);
				} catch (SpecialityDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		specialityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		specialityComboBox.setBackground(new Color(211, 211, 211));
		specialityComboBox.setBounds(160, 129, 180, 21);
		contentPane.add(specialityComboBox);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		usernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernameLbl.setBounds(43, 167, 90, 20);
		contentPane.add(usernameLbl);
		
		usernameComboBox.addFocusListener(new FocusAdapter() {

			/**
			 * This callback gets the services from Service Layer,
			 * regarding the search for users.
			 *
			 * @param e			an {@link FocusEvent} object, user's event.
			 */
			@Override
			public void focusGained(FocusEvent e) {
				List<User> users;
				usernamesMap = new HashMap<>();
				try {
					usernamesModel = new DefaultComboBoxModel<>();
					users = userService.getAllUsers();
					for (User user : users) {

						usernamesMap.put(user.getUsername(), user.getId());
						usernamesModel.addElement(user.getUsername());
					}
					usernameComboBox.setModel(usernamesModel);
					usernameComboBox.setMaximumRowCount(5);
					
				} catch (UserDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		usernameComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameComboBox.setBackground(new Color(211, 211, 211));
		usernameComboBox.setBounds(160, 168, 180, 21);
		contentPane.add(usernameComboBox);
		
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
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String ssn = ssnTxt.getText().trim();
				String speciality = (String) specialityComboBox.getSelectedItem();
				String username = (String) usernameComboBox.getSelectedItem();

				long specialityId = specialtiesMap.get(speciality);
				long userId = usernamesMap.get(username);

				Map<String, String> teacherErrors;
				
				if (firstname == null || lastname == null || ssn == null) {
					JOptionPane.showMessageDialog(null, "Please fill firstname / lastname / ssn", "Basic Info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					TeacherInsertDTO dto = new TeacherInsertDTO();
					dto.setSsn(Long.parseLong(ssn));
					dto.setFirstname(firstname);
					dto.setLastname(lastname);
					dto.setSpecialityId(specialityId);
					dto.setUserId(userId);

					teacherErrors = TeacherValidator.validate(dto);

					String firstnameMessage = (teacherErrors.get("firstname") != null) ? "Firstname: " + teacherErrors.get("firstname") : "";

					String lastnameMessage = (teacherErrors.get("lastname") != null) ? "Lastname: " + teacherErrors.get("lastname") : "";

					if(!teacherErrors.isEmpty()) {
						JOptionPane.showMessageDialog(null,  firstnameMessage + lastnameMessage ,"Validation errors", JOptionPane.ERROR_MESSAGE);
						return;
					}

					Teacher teacher = teacherService.insertTeacher(dto);
					JOptionPane.showMessageDialog(null, "Teacher " + teacher.getLastname() + " was inserted successfully", "INSERT", JOptionPane.PLAIN_MESSAGE);
					firstnameTxt.setText("");
					lastnameTxt.setText("");
					ssnTxt.setText("");
					specialityComboBox.setSelectedItem(null);
					usernameComboBox.setSelectedItem(null);
				}catch (TeacherDAOException | UserDAOException | SpecialityDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Insert Teacher Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Arial", Font.BOLD, 14));
		insertBtn.setBounds(160, 209, 90, 30);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback closes {@link AdminInsertTeachersForm}
			 * and enables {@link TeachersMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersMenu().setEnabled(true);
				Main.getAdminInsertTeachersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(298, 209, 90, 30);
		contentPane.add(closeBtn);
	}

}
