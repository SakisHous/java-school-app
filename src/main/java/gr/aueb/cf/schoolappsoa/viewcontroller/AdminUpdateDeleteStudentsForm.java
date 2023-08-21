package gr.aueb.cf.schoolappsoa.viewcontroller;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolappsoa.Main;
import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.exceptions.*;
import gr.aueb.cf.schoolappsoa.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.model.Student;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.*;
import gr.aueb.cf.schoolappsoa.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolappsoa.service.util.DateUtil;
import gr.aueb.cf.schoolappsoa.validator.StudentValidator;

import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * This class shows the result of the searching from 
 * {@link AdminInsertStudentsForm} when admin has clicked 
 * the search button. In this JFrame, admin can update or 
 * delete a student.
 * 
 * @author Thanasis Chousiadas
 */
public class AdminUpdateDeleteStudentsForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private ButtonGroup buttonGroup = new ButtonGroup(); 
	private JComboBox<String> cityComboBox = new JComboBox<String>();
	private JComboBox<String> usernameComboBox = new JComboBox<String>();
	private JButton firstBtn;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton lastBtn;
	private Map<Long, String> citiesMap;
	private Map<Long, String> usernamesMap;
	private DefaultComboBoxModel<String> citiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private JRadioButton maleRdBtn = new JRadioButton("Male");
	private JRadioButton femaleRdBtn = new JRadioButton("Female");
	private JPanel contentPane;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField idTxt;
	private JLabel usernameLbl;
	private JTextField birthdateTxt;
	private List<Student> students;
	private int listSize;
	private int listPosition;


	IStudentDAO studentDAO = new StudentDAOImpl();
	ICityDAO cityDAO = new CityDAOImpl();
	IUserDAO userDAO = new UserDAOImpl();
	ITeacherDAO teacherDAO = new TeacherDAOImpl();
	IStudentService studentService = new StudentServiceImpl(studentDAO, cityDAO, userDAO);
	ICityService cityService = new CityServiceImpl(cityDAO);
	IUserService userService = new UserServiceImpl(userDAO, studentDAO, teacherDAO);

	/**
	 * The constructor of JFrame {@link AdminUpdateDeleteStudentsForm} class.
	 */
	public AdminUpdateDeleteStudentsForm() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		addWindowListener(new WindowAdapter() {
			
			/**
			 * This callback is called when window is opened.
			 * It makes use of Service Layer and outputs a student (or students) with
			 * the lastname given by the admin. It shows all the information
			 * for the student.
			 * 
			 * @param e			a {@link WindowEvent} object.
			 */
			@Override
			public void windowActivated(WindowEvent e) {
				citiesMap = new HashMap<>();
				usernamesMap = new HashMap<>();
				List<City> cities;
				List<User> users;
				try {
					students = studentService.getStudentByLastname(Main.getStudentsMenu().getLastname());
					listSize = students.size();
					if (listSize == 0) {
						JOptionPane.showMessageDialog(null, "Empty, no students found", "Search", JOptionPane.INFORMATION_MESSAGE);
						Main.getStudentsMenu().setEnabled(true);
						Main.getAdminUpdateDeleteStudentsForm().setVisible(false);
						return;
					}
					// For the Combo Boxes we need all Cities and Usernames in the DB
					cities = cityService.getAllCities();
					citiesModel = new DefaultComboBoxModel<>();
					for (City city : cities) {
						citiesMap.put(city.getId(), city.getCity());
						citiesModel.addElement(city.getCity());
					}
					cityComboBox.setModel(citiesModel);
					cityComboBox.setMaximumRowCount(5);

					usernamesModel = new DefaultComboBoxModel<>();
					users = userService.getAllUsers();
					for (User user : users) {
						usernamesMap.put(user.getId(), user.getUsername());
						usernamesModel.addElement(user.getUsername());
					}
					usernameComboBox.setModel(usernamesModel);
					usernameComboBox.setMaximumRowCount(5);

					// Display Student at position 0 in the ArrayList
					listPosition = 0;
					Student currentStudent = students.get(listPosition);
					idTxt.setText(Long.toString(currentStudent.getId()));
					firstnameTxt.setText(currentStudent.getFirstname());
					lastnameTxt.setText(currentStudent.getLastname());

					if (currentStudent.getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthdateTxt.setText(DateUtil.toString(currentStudent.getBirthDate()));
					cityComboBox.setSelectedItem(currentStudent.getStudentCity().getCity());
					usernameComboBox.setSelectedItem(currentStudent.getUser().getUsername());
				} catch (StudentDAOException | CityDAOException | UserDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		setTitle("Update - Delete Student");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 514);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		firstBtn = new JButton("");
		firstBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button for the
			 * first student in the list, if there are 
			 * more than one, given by the lastname parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = 0;
					Student currentStudent = students.get(listPosition);
					idTxt.setText(Long.toString(currentStudent.getId()));
					firstnameTxt.setText(currentStudent.getFirstname());
					lastnameTxt.setText(currentStudent.getLastname());

					if (currentStudent.getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthdateTxt.setText(DateUtil.toString(currentStudent.getBirthDate()));
					cityComboBox.setSelectedItem(currentStudent.getStudentCity().getCity());
					usernameComboBox.setSelectedItem(currentStudent.getUser().getUsername());
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/First_Record.png")));
		firstBtn.setBounds(87, 350, 49, 30);
		contentPane.add(firstBtn);
		
		prevBtn = new JButton("");
		prevBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button
			 * for the previous student in the list.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listPosition > 0) {
					listPosition--;
					Student currentStudent = students.get(listPosition);
					idTxt.setText(Long.toString(currentStudent.getId()));
					firstnameTxt.setText(currentStudent.getFirstname());
					lastnameTxt.setText(currentStudent.getLastname());

					if (currentStudent.getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthdateTxt.setText(DateUtil.toString(currentStudent.getBirthDate()));
					cityComboBox.setSelectedItem(currentStudent.getStudentCity().getCity());
					usernameComboBox.setSelectedItem(currentStudent.getUser().getUsername());
				}

			}
		});
		prevBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/Previous_Record.png")));
		prevBtn.setBounds(134, 350, 49, 30);
		contentPane.add(prevBtn);
		
		nextBtn = new JButton("");
		nextBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button for the
			 * next student in the list, if there are 
			 * more than one, given by the lastname parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listPosition <= listSize - 2) {
					listPosition++;
					Student currentStudent = students.get(listPosition);
					idTxt.setText(Long.toString(currentStudent.getId()));
					firstnameTxt.setText(currentStudent.getFirstname());
					lastnameTxt.setText(currentStudent.getLastname());

					if (currentStudent.getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthdateTxt.setText(DateUtil.toString(currentStudent.getBirthDate()));
					cityComboBox.setSelectedItem(currentStudent.getStudentCity().getCity());
					usernameComboBox.setSelectedItem(currentStudent.getUser().getUsername());
				}

			}
		});
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/Next_Record.png")));
		nextBtn.setBounds(179, 350, 49, 30);
		contentPane.add(nextBtn);
		
		lastBtn = new JButton("");
		lastBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button for the
			 * last student in the list, if there are 
			 * more than one, given by the lastname parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = listSize - 1;
					Student currentStudent = students.get(listPosition);
					idTxt.setText(Long.toString(currentStudent.getId()));
					firstnameTxt.setText(currentStudent.getFirstname());
					lastnameTxt.setText(currentStudent.getLastname());

					if (currentStudent.getGender().equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthdateTxt.setText(DateUtil.toString(currentStudent.getBirthDate()));
					cityComboBox.setSelectedItem(currentStudent.getStudentCity().getCity());
					usernameComboBox.setSelectedItem(currentStudent.getUser().getUsername());
				}

			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/Last_Record.png")));
		lastBtn.setBounds(227, 350, 49, 30);
		contentPane.add(lastBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(24, 324, 303, 2);
		contentPane.add(separator);
		
		JLabel firstnameLbl = new JLabel("Firstname");
		firstnameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		firstnameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		firstnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		firstnameLbl.setBounds(24, 67, 90, 20);
		contentPane.add(firstnameLbl);
		
		JLabel lastnameLbl = new JLabel("Lastname");
		lastnameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lastnameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		lastnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		lastnameLbl.setBounds(24, 109, 90, 20);
		contentPane.add(lastnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		firstnameTxt.setColumns(10);
		firstnameTxt.setBounds(136, 67, 180, 20);
		contentPane.add(firstnameTxt);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(134, 109, 180, 20);
		contentPane.add(lastnameTxt);
		
		JLabel idLbl = new JLabel("ID");
		idLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		idLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		idLbl.setFont(new Font("Arial", Font.BOLD, 15));
		idLbl.setBounds(24, 28, 90, 20);
		contentPane.add(idLbl);
		
		idTxt = new JTextField();
		idTxt.setBackground(new Color(255, 255, 205));
		idTxt.setEditable(false);
		idTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		idTxt.setColumns(10);
		idTxt.setBounds(136, 28, 59, 20);
		contentPane.add(idTxt);
		
		JLabel genderLbl = new JLabel("Gender");
		genderLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		genderLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		genderLbl.setFont(new Font("Arial", Font.BOLD, 15));
		genderLbl.setBounds(28, 157, 90, 20);
		contentPane.add(genderLbl);
		
		JLabel brithDateLbl = new JLabel("Birth Date");
		brithDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		brithDateLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		brithDateLbl.setFont(new Font("Arial", Font.BOLD, 15));
		brithDateLbl.setBounds(24, 200, 90, 20);
		contentPane.add(brithDateLbl);
		
		JLabel cityLbl = new JLabel("City");
		cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		cityLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		cityLbl.setFont(new Font("Arial", Font.BOLD, 15));
		cityLbl.setBounds(24, 240, 90, 20);
		contentPane.add(cityLbl);
		
		usernameLbl = new JLabel("Username");
		usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		usernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernameLbl.setBounds(24, 279, 90, 20);
		contentPane.add(usernameLbl);
		
		cityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		cityComboBox.setBackground(new Color(211, 211, 211));
		cityComboBox.setBounds(134, 241, 180, 21);
		contentPane.add(cityComboBox);
		usernameComboBox.setEnabled(false);
		
		usernameComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameComboBox.setBackground(new Color(211, 211, 211));
		usernameComboBox.setBounds(134, 280, 180, 21);
		contentPane.add(usernameComboBox);
		
		birthdateTxt = new JTextField();
		birthdateTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		birthdateTxt.setColumns(10);
		birthdateTxt.setBounds(134, 202, 180, 20);
		contentPane.add(birthdateTxt);
		
		maleRdBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		maleRdBtn.setActionCommand("M");
		maleRdBtn.setBounds(162, 156, 75, 21);
		contentPane.add(maleRdBtn);
		
		femaleRdBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		femaleRdBtn.setActionCommand("F");
		femaleRdBtn.setBounds(239, 156, 75, 21);
		contentPane.add(femaleRdBtn);
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback gets the services from Service Layer
			 * and updates a student in the database after
			 * performing some validations.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String gender = buttonGroup.getSelection().getActionCommand();
				String strDate = birthdateTxt.getText();
				String city = (String) cityComboBox.getSelectedItem();
				String username = (String) usernameComboBox.getSelectedItem();

				if (firstname == null || lastname == null) {
					JOptionPane.showMessageDialog(null, "Please fill firstname / lastname ", "Basic Info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Found CITY_ID
				long cityId = 0;
				for (Map.Entry<Long, String> entry : citiesMap.entrySet()) {
		            if (entry.getValue().equals(city)) {
		                cityId = entry.getKey();
		                break;
		            }
		        }
				// Found USER_ID
				long userId = 0;
				for (Map.Entry<Long, String> entry : usernamesMap.entrySet()) {
		            if (entry.getValue().equals(username)) {
		                userId = entry.getKey();
		                break;
		            }
		        }
				Map<String, String> teacherErrors;
				try {
					StudentUpdateDTO dto = new StudentUpdateDTO();
					dto.setId(Long.parseLong(idTxt.getText()));
					dto.setFirstname(firstname);
					dto.setLastname(lastname);
					dto.setGender(gender.charAt(0));
					dto.setBirthDate(DateUtil.toDate(strDate));
					dto.setCityId(cityId);
					dto.setUserId(userId);

					teacherErrors = StudentValidator.validate(dto);

					String firstnameMessage = (teacherErrors.get("firstname") != null) ? "Firstname: " + teacherErrors.get("firstname") : "";
					String lastnameMessage = (teacherErrors.get("lastname") != null) ? "Lastname: " + teacherErrors.get("lastname") : "";
					if(!teacherErrors.isEmpty()) {
						JOptionPane.showMessageDialog(null,  firstnameMessage + lastnameMessage ,"Validation errors", JOptionPane.ERROR_MESSAGE);
						return;
					}

					Student student = studentService.updateStudent(dto);
					if (student != null) {
						JOptionPane.showMessageDialog(null, "Successfully update the student", "Update", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (ParseException | StudentDAOException | StudentNotFoundException | CityDAOException | UserDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error Update Student", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		updateBtn.setForeground(Color.BLUE);
		updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
		updateBtn.setBounds(46, 418, 90, 30);
		contentPane.add(updateBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback gets the services from Service Layer
			 * and deletes a student from the database.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					int response;
					String idStr = idTxt.getText();
					long id = Long.parseLong(idStr);

					response = JOptionPane.showConfirmDialog(null, "Are you sure for deleting student?", "Warning", JOptionPane.YES_NO_OPTION);

					if (response == JOptionPane.YES_OPTION) {
						if (studentService.deleteStudent(id)) {
							JOptionPane.showMessageDialog(null, "Student was deleted successfully.", "Delete", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Student was not deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (StudentNotFoundException | UserDAOException | StudentDAOException | CityDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error Deletion", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(Color.BLUE);
		deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
		deleteBtn.setBounds(179, 418, 90, 30);
		contentPane.add(deleteBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback closes the {@link AdminUpdateDeleteStudentsForm}
			 * and opens the previous JFrame {@link StudentsMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenu().setEnabled(true);
				Main.getAdminUpdateDeleteStudentsForm().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(307, 418, 90, 30);
		contentPane.add(closeBtn);
	}
}
