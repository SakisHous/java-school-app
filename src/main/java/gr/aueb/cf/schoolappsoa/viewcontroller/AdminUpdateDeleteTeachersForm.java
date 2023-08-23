package gr.aueb.cf.schoolappsoa.viewcontroller;

import gr.aueb.cf.schoolappsoa.Main;
import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.*;
import gr.aueb.cf.schoolappsoa.service.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolappsoa.validator.TeacherValidator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * This class shows the result of the searching from 
 * {@link AdminInsertTeachersForm} when admin has clicked 
 * the search button. In this JFrame, admin can update or 
 * delete a teacher.
 * 
 * @author Thanasis Chousiadas
 */
public class AdminUpdateDeleteTeachersForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idTxt;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField ssnTxt;
	private JComboBox<String> specialityComboBox = new JComboBox<>();
	private JComboBox<String> usernameComboBox = new JComboBox<>();
	private Map<Long, String> specialtiesMap;
	private Map<Long, String> usernamesMap;
	private DefaultComboBoxModel<String> specialitiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private List<Teacher> teachers;
	private int listSize;
	private int listPosition;

	ITeacherDAO teacherDAO = new TeacherDAOImpl();
	ISpecialityDAO specialtyDAO = new SpecialityDAOImpl();
	IUserDAO userDAO = new UserDAOImpl();
	IStudentDAO studentDAO = new StudentDAOImpl();
	ITeacherService teacherService = new TeacherServiceImpl(teacherDAO, specialtyDAO, userDAO);
	ISpecialityService specialtyService = new SpecialityServiceImpl(specialtyDAO);
	IUserService userService = new UserServiceImpl(userDAO, studentDAO, teacherDAO);

	/**
	 * The constructor of JFrame {@link AdminUpdateDeleteTeachersForm} class.
	 */
	public AdminUpdateDeleteTeachersForm() {
		addWindowListener(new WindowAdapter() {
			
			/**
			 * This callback is called when window is opened.
			 * It makes use of Service Layer and outputs a teacher (or teachers) with
			 * the lastname given by the admin. It shows all the information
			 * for the teacher.
			 * 
			 * @param e			a {@link WindowEvent} object.
			 */
			@Override
			public void windowActivated(WindowEvent e) {
				List<Speciality> specialties;
				specialtiesMap = new HashMap<>();
				List<User> users;
				usernamesMap = new HashMap<>();

				try {
					teachers = teacherService.getTeacherByLastname(Main.getTeachersMenu().getLastname());
					listSize = teachers.size();
					if (listSize == 0) {
						JOptionPane.showMessageDialog(null, "Empty, no teachers found", "Search", JOptionPane.INFORMATION_MESSAGE);
						Main.getTeachersMenu().setEnabled(true);
						Main.getAdminUpdateDeleteTeachersForm().setVisible(false);
						return;
					}

					// For the Combo Boxes we need all Specialties and Usernames in the DB
					// Updating a Teacher, admin can update specialty and username
					specialties = specialtyService.getAllSpecialities();
					specialitiesModel = new DefaultComboBoxModel<>();

					for (Speciality speciality : specialties) {
						specialtiesMap.put(speciality.getId(), speciality.getSpeciality());
						specialitiesModel.addElement(speciality.getSpeciality());
					}

					specialityComboBox.setModel(specialitiesModel);
					specialityComboBox.setMaximumRowCount(5);

					usernamesModel = new DefaultComboBoxModel<>();
					users = userService.getAllUsers();
					for (User user : users) {
						usernamesMap.put(user.getId(), user.getUsername());
						usernamesModel.addElement(user.getUsername());
					}
					usernameComboBox.setModel(usernamesModel);
					usernameComboBox.setMaximumRowCount(5);

					listPosition = 0;
					idTxt.setText(Long.toString(teachers.get(listPosition).getId()));
					firstnameTxt.setText(teachers.get(listPosition).getFirstname());
					lastnameTxt.setText(teachers.get(listPosition).getLastname());

					// Display the specialty of the current Teacher in the Specialty Combo Box
					Teacher currentTeacher = teachers.get(listPosition);
					specialityComboBox.setSelectedItem(currentTeacher.getTeacherSpeciality().getSpeciality());

					// Display the username of the current Teacher in the Username Combo Box
					usernameComboBox.setSelectedItem(currentTeacher.getUser().getUsername());

				} catch (TeacherDAOException | UserDAOException | SpecialityDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Update - Delete Teachers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel idLbl = new JLabel("ID:");
		idLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		idLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		idLbl.setFont(new Font("Arial", Font.BOLD, 15));
		idLbl.setBounds(45, 16, 90, 20);
		contentPane.add(idLbl);
		
		idTxt = new JTextField();
		idTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		idTxt.setEditable(false);
		idTxt.setColumns(10);
		idTxt.setBackground(new Color(255, 255, 205));
		idTxt.setBounds(156, 16, 59, 20);
		contentPane.add(idTxt);
		
		JLabel firstnameLbl = new JLabel("Firstname:");
		firstnameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		firstnameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		firstnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		firstnameLbl.setBounds(45, 49, 90, 20);
		contentPane.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		firstnameTxt.setColumns(10);
		firstnameTxt.setBounds(156, 49, 180, 20);
		contentPane.add(firstnameTxt);
		
		JLabel lastnameLbl = new JLabel("Lastname:");
		lastnameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lastnameLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		lastnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		lastnameLbl.setBounds(45, 91, 90, 20);
		contentPane.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(156, 91, 180, 20);
		contentPane.add(lastnameTxt);
		
		JLabel specialityLbl = new JLabel("Speciality:");
		specialityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		specialityLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		specialityLbl.setFont(new Font("Arial", Font.BOLD, 15));
		specialityLbl.setBounds(45, 169, 90, 20);
		contentPane.add(specialityLbl);
		
		
		specialityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		specialityComboBox.setBackground(new Color(211, 211, 211));
		specialityComboBox.setBounds(156, 169, 180, 21);
		contentPane.add(specialityComboBox);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setForeground(UIManager.getColor("Button.darkShadow"));
		lblUsername.setFont(new Font("Arial", Font.BOLD, 15));
		lblUsername.setBounds(45, 199, 90, 20);
		contentPane.add(lblUsername);
		
		usernameComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		usernameComboBox.setEnabled(false);
		usernameComboBox.setBackground(new Color(211, 211, 211));
		usernameComboBox.setBounds(156, 199, 180, 21);
		contentPane.add(usernameComboBox);
		
		JButton firstBtn = new JButton("");
		firstBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button for the
			 * first teacher in the list, if there are 
			 * more than one, given by the lastname parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = 0;
					idTxt.setText(Long.toString(teachers.get(listPosition).getId()));
					firstnameTxt.setText(teachers.get(listPosition).getFirstname());
					lastnameTxt.setText(teachers.get(listPosition).getLastname());
					// Display the specialty of the current Teacher in the Specialty Combo Box
					Teacher currentTeacher = teachers.get(listPosition);
					specialityComboBox.setSelectedItem(currentTeacher.getTeacherSpeciality().getSpeciality());
					// Display the username of the current Teacher in the Username Combo Box
					usernameComboBox.setSelectedItem(currentTeacher.getUser().getUsername());
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteStudentsForm.class.getResource("/resources/First_Record.png")));
		firstBtn.setBounds(145, 271, 49, 30);
		contentPane.add(firstBtn);
		
		JButton prevBtn = new JButton("");
		prevBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button
			 * for the previous teacher in the list.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listPosition > 0) {
					listPosition--;
					idTxt.setText(Long.toString(teachers.get(listPosition).getId()));
					firstnameTxt.setText(teachers.get(listPosition).getFirstname());
					lastnameTxt.setText(teachers.get(listPosition).getLastname());
					// Display the specialty of the current Teacher in the Specialty Combo Box
					Teacher currentTeacher = teachers.get(listPosition);
					specialityComboBox.setSelectedItem(currentTeacher.getTeacherSpeciality().getSpeciality());
					// Display the username of the current Teacher in the Username Combo Box
					usernameComboBox.setSelectedItem(currentTeacher.getUser().getUsername());
				}
			}
		});
		prevBtn.setIcon(new ImageIcon(AdminUpdateDeleteTeachersForm.class.getResource("/resources/Previous_Record.png")));
		prevBtn.setBounds(192, 271, 49, 30);
		contentPane.add(prevBtn);
		
		JButton nextBtn = new JButton("");
		nextBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button
			 * for the next teacher in the list, if there are 
			 * more than one, given by the lastname parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listPosition <= listSize - 2) {
					listPosition++;
					idTxt.setText(Long.toString(teachers.get(listPosition).getId()));
					firstnameTxt.setText(teachers.get(listPosition).getFirstname());
					lastnameTxt.setText(teachers.get(listPosition).getLastname());
					// Display the specialty of the current Teacher in the Specialty Combo Box
					Teacher currentTeacher = teachers.get(listPosition);
					specialityComboBox.setSelectedItem(currentTeacher.getTeacherSpeciality().getSpeciality());
					// Display the username of the current Teacher in the Username Combo Box
					usernameComboBox.setSelectedItem(currentTeacher.getUser().getUsername());
				}
			}
		});
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteTeachersForm.class.getResource("/resources/Next_Record.png")));
		nextBtn.setBounds(237, 271, 49, 30);
		contentPane.add(nextBtn);
		
		JButton lastBtn = new JButton("");
		lastBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback implements a button for the
			 * last teacher in the list, if there are 
			 * more than one, given by the lastname parameter.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				if (listSize > 0) {
					listPosition = listSize - 1;
					idTxt.setText(Long.toString(teachers.get(listPosition).getId()));
					firstnameTxt.setText(teachers.get(listPosition).getFirstname());
					lastnameTxt.setText(teachers.get(listPosition).getLastname());
					// Display the specialty of the current Teacher in the Specialty Combo Box
					Teacher currentTeacher = teachers.get(listPosition);
					specialityComboBox.setSelectedItem(currentTeacher.getTeacherSpeciality().getSpeciality());
					// Display the username of the current Teacher in the Username Combo Box
					usernameComboBox.setSelectedItem(currentTeacher.getUser().getUsername());
				}
			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteTeachersForm.class.getResource("/resources/Last_Record.png")));
		lastBtn.setBounds(285, 271, 49, 30);
		contentPane.add(lastBtn);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {

			/**
			 * This callback gets the services from Service Layer
			 * and updates a teacher in the database after
			 * performing some validations.
			 *
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				long id = Long.parseLong(idTxt.getText());
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String ssn = ssnTxt.getText().trim();
				String speciality = (String) specialityComboBox.getSelectedItem();
				String username = (String) usernameComboBox.getSelectedItem();

				// Found SPECIALTY_ID
				long specialityId = 0L;
				
				for (Map.Entry<Long, String> entry : specialtiesMap.entrySet()) {
		            if (entry.getValue().equals(speciality)) {
		                specialityId = entry.getKey();
		                break;
		            }
		        }
				
				// Found USER_ID
				long userId = 0L;
				
				for (Map.Entry<Long, String> entry : usernamesMap.entrySet()) {
		            if (entry.getValue().equals(username)) {
		                userId = entry.getKey();
		                break;
		            }
		        }
				Map<String, String> teacherErrors;
				try {
					TeacherUpdateDTO dto = new TeacherUpdateDTO();
					dto.setId(id);
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

					Teacher teacher = teacherService.updateTeacher(dto);
				} catch(TeacherNotFoundException | TeacherDAOException | UserDAOException | SpecialityDAOException ex) {
					String msg = ex.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateBtn.setForeground(Color.BLUE);
		updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
		updateBtn.setBounds(42, 321, 90, 30);
		contentPane.add(updateBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {

			/**
			 * This callback gets the services from Service Layer
			 * and deletes a teacher from the database.
			 *
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					int response;
					String idStr = idTxt.getText();
					long id = Long.parseLong(idStr);

					response = JOptionPane.showConfirmDialog(null, "Are you for deleting the teacher?", "Warning", JOptionPane.YES_NO_OPTION);

					if (response == JOptionPane.YES_OPTION) {
						if (teacherService.deleteTeacher(id)) {
							JOptionPane.showMessageDialog(null, "Teacher was deleted successfully.", "Delete", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Teacher was not deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (TeacherNotFoundException | UserDAOException | SpecialityDAOException | TeacherDAOException e1) {
					String msg = e1.getMessage();
					JOptionPane.showMessageDialog(null, msg, "Error Deletion", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(Color.BLUE);
		deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
		deleteBtn.setBounds(175, 321, 90, 30);
		contentPane.add(deleteBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			
			/**
			 * This callback closes the {@link AdminUpdateDeleteTeachersForm}
			 * and opens the previous JFrame {@link TeachersMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersMenu().setEnabled(true);
				Main.getAdminUpdateDeleteTeachersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(303, 321, 90, 30);
		contentPane.add(closeBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(42, 242, 303, 2);
		contentPane.add(separator);
		
		JLabel ssnLbl = new JLabel("SSN:");
		ssnLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		ssnLbl.setForeground(UIManager.getColor("Button.darkShadow"));
		ssnLbl.setFont(new Font("Arial", Font.BOLD, 15));
		ssnLbl.setBounds(45, 129, 90, 20);
		contentPane.add(ssnLbl);
		
		ssnTxt = new JTextField();
		ssnTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		ssnTxt.setColumns(10);
		ssnTxt.setBounds(156, 131, 180, 20);
		contentPane.add(ssnTxt);
	}
}
