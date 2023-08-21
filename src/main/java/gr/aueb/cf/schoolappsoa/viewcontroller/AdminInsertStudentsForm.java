package gr.aueb.cf.schoolappsoa.viewcontroller;

import gr.aueb.cf.schoolappsoa.Main;
import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.StudentInsertDTO;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.model.Student;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.*;
import gr.aueb.cf.schoolappsoa.service.util.DateUtil;
import gr.aueb.cf.schoolappsoa.validator.StudentValidator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This JFrame class adds a new Student in the 
 * database.
 * 
 * @author Thanasis Chousiadas
 */
public class AdminInsertStudentsForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField birthDateTxt;
	private JComboBox<String> cityComboBox = new JComboBox<>();
	private JComboBox<String> usernameComboBox = new JComboBox<>();
	private DefaultComboBoxModel<String> citiesModel;
	private Map<String, Long> citiesMap;
	private DefaultComboBoxModel<String> usernamesModel;
	private Map<String, Long> usernamesMap;
	private ButtonGroup buttonGroup = new ButtonGroup();

	IStudentDAO studentDAO = new StudentDAOImpl();
	IUserDAO userDAO = new UserDAOImpl();
	ICityDAO cityDAO = new CityDAOImpl();
	ITeacherDAO teacherDAO = new TeacherDAOImpl();
	IStudentService studentService = new StudentServiceImpl(studentDAO, cityDAO, userDAO);
	ICityService cityService = new CityServiceImpl(cityDAO);
	IUserService userService= new UserServiceImpl(userDAO, studentDAO, teacherDAO);
	
	/**
	 * The constructor of JFrame {@link AdminInsertStudentsForm} class.
	 */
	public AdminInsertStudentsForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Insert Student");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel firstnameLbl = new JLabel("Firstname");
		firstnameLbl.setForeground(new Color(105, 105, 105));
		firstnameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		firstnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		firstnameLbl.setBounds(56, 27, 90, 20);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		firstnameTxt.setBounds(173, 27, 180, 20);
		contentPane.add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		JLabel lastnameLbl = new JLabel("Lastname");
		lastnameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lastnameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		lastnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		lastnameLbl.setBounds(56, 57, 90, 20);
		contentPane.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(173, 57, 180, 20);
		contentPane.add(lastnameTxt);
		
		JLabel genderLbl = new JLabel("Gender");
		genderLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		genderLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		genderLbl.setFont(new Font("Arial", Font.BOLD, 15));
		genderLbl.setBounds(56, 87, 90, 20);
		contentPane.add(genderLbl);
		
		JRadioButton maleRdBtn = new JRadioButton("Male");
		maleRdBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		maleRdBtn.setBounds(173, 87, 75, 21);
		contentPane.add(maleRdBtn);
		
		JRadioButton femaleRdBtn = new JRadioButton("Female");
		femaleRdBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		femaleRdBtn.setBounds(250, 87, 75, 21);
		contentPane.add(femaleRdBtn);
		
		maleRdBtn.setActionCommand("M");
		femaleRdBtn.setActionCommand("F");
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JLabel birthDateLbl = new JLabel("Birth Date");
		birthDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		birthDateLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		birthDateLbl.setFont(new Font("Arial", Font.BOLD, 15));
		birthDateLbl.setBounds(56, 117, 90, 20);
		contentPane.add(birthDateLbl);
		
		birthDateTxt = new JTextField();
		birthDateTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		birthDateTxt.setColumns(10);
		birthDateTxt.setBounds(173, 117, 180, 20);
		contentPane.add(birthDateTxt);
		
		JLabel cityLbl = new JLabel("City");
		cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		cityLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		cityLbl.setFont(new Font("Arial", Font.BOLD, 15));
		cityLbl.setBounds(56, 147, 90, 20);
		contentPane.add(cityLbl);
		
		cityComboBox.addFocusListener(new FocusAdapter() {
			
			/**
			 * This callback gets the services from Service Layer,
			 * regarding the search for the cities.
			 * 
			 * @param e			an {@link FocusEvent} object, user's event.
			 */
			@Override
			public void focusGained(FocusEvent e) {
				List<City> cities;
				citiesMap = new HashMap<>();
				try {
					citiesModel = new DefaultComboBoxModel<>();
					cities = cityService.getAllCities();

					for (City city : cities) {
						citiesMap.put(city.getCity(), city.getId());
						citiesModel.addElement(city.getCity());
					}
					cityComboBox.setModel(citiesModel);
					cityComboBox.setMaximumRowCount(5);
				} catch (CityDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cityComboBox.setBackground(new Color(211, 211, 211));
		cityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		cityComboBox.setBounds(173, 146, 180, 21);
		contentPane.add(cityComboBox);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		usernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernameLbl.setBounds(56, 177, 90, 20);
		contentPane.add(usernameLbl);
		usernameComboBox.addFocusListener(new FocusAdapter() {

			/**
			 * This callback gets the services from Service Layer,
			 * regarding the search for the users.
			 *
			 * @param e			an {@link FocusEvent} object, user's event.
			 */
			@Override
			public void focusGained(FocusEvent e) {
				usernamesMap = new HashMap<>();
				List<User> users;
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
		
		usernameComboBox.setBackground(new Color(211, 211, 211));
		usernameComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameComboBox.setBounds(173, 178, 180, 21);
		contentPane.add(usernameComboBox);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {

			/**
			 * This callback gets the services from Service Layer,
			 * for adding a new user.
			 *
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (buttonGroup.getSelection() == null || cityComboBox.getSelectedItem() == null
						|| usernameComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select gender / city / username", "Gender", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String gender = buttonGroup.getSelection().getActionCommand();
				String city = (String) cityComboBox.getSelectedItem();
				String username = (String) usernameComboBox.getSelectedItem();
				long cityId = citiesMap.get(city);
				long userId = usernamesMap.get(username);
				
				java.util.Date utilBirthDate;
				try {
					utilBirthDate = DateUtil.toDate(birthDateTxt.getText());
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(null, "Please insert a valid date (dd-MM-yyyy)", "Date", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Map<String, String> studentErrors;

				try {
					StudentInsertDTO dto = new StudentInsertDTO();
					dto.setFirstname(firstname);
					dto.setLastname(lastname);
					dto.setGender(gender.charAt(0));
					dto.setBirthDate(utilBirthDate);
					dto.setCityId(cityId);
					dto.setUserId(userId);

					studentErrors = StudentValidator.validate(dto);

					String firstnameMessage = (studentErrors.get("firstname") != null) ? "Firstname: " + studentErrors.get("firstname") : "";

					String lastnameMessage = (studentErrors.get("lastname") != null) ? "Lastname: " + studentErrors.get("lastname") : "";

					if (!studentErrors.isEmpty()) {
						JOptionPane.showMessageDialog(null, firstnameMessage + lastnameMessage, "Validation Errors",  JOptionPane.ERROR_MESSAGE);
						return;
					}

					Student student = studentService.insertStudent(dto);
					JOptionPane.showMessageDialog(null, "Successful new record inserted", "Insert", JOptionPane.INFORMATION_MESSAGE);
					firstnameTxt.setText("");
					lastnameTxt.setText("");
					buttonGroup.clearSelection();
					birthDateTxt.setText("");
					cityComboBox.setSelectedItem(null);
					usernameComboBox.setSelectedItem(null);
				} catch(StudentDAOException | CityDAOException | UserDAOException e1) {
					e1.printStackTrace();
				}
			}
		});
		insertBtn.setForeground(new Color(0, 0, 255));
		insertBtn.setFont(new Font("Arial", Font.BOLD, 14));
		insertBtn.setBounds(173, 219, 90, 30);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback closes {@link AdminInsertStudentsForm}
			 * and enables {@link StudentsMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenu().setEnabled(true);
				Main.getAdminInsertStudentsForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(311, 219, 90, 30);
		contentPane.add(closeBtn);
	}
}
