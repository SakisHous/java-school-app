package gr.aueb.cf.schoolappsoa.viewcontroller;

import gr.aueb.cf.schoolappsoa.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 * This JFrame is called if the user has 
 * clicked the Students Button from the
 * {@link AdminMenu} JFrame class.
 * The user can search for a student with the lastname
 * in the database or add a new student.
 * 
 * @author Thanasis Chousiadas.
 */
public class StudentsMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private String lastname;
	private JPanel contentPane;
	private JTextField lastnameTxt;

	/**
	 * The constructor of JFrame {@link StudentsMenu} class.
	 */
	public StudentsMenu() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Students Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		searchPanel.setBounds(16, 25, 404, 159);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);
		
		JLabel lastnameLbl = new JLabel("Lastname");
		lastnameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		lastnameLbl.setBounds(167, 24, 69, 17);
		lastnameLbl.setFont(new Font("Arial", Font.BOLD, 14));
		searchPanel.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setBounds(76, 51, 250, 30);
		lastnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		searchPanel.add(lastnameTxt);
		lastnameTxt.setColumns(10);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {

			/**
			 * This callback sets visible the {@link AdminUpdateDeleteStudentsForm},
			 * where the results of searching are shown.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				lastname = lastnameTxt.getText();
				Main.getAdminUpdateDeleteStudentsForm().setVisible(true);
				Main.getStudentsMenu().setEnabled(false);
			}
		});
		searchBtn.setForeground(new Color(0, 0, 255));
		searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
		searchBtn.setBounds(151, 106, 100, 30);
		searchPanel.add(searchBtn);
		
		JPanel insertPanel = new JPanel();
		insertPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		insertPanel.setBounds(16, 210, 404, 78);
		contentPane.add(insertPanel);
		insertPanel.setLayout(null);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {
			/**
			 * This callback set visible the {@link AdminInsertStudentsForm}
			 * JFrame class where the user can add a new student.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getAdminInsertStudentsForm().setVisible(true);
				Main.getStudentsMenu().setEnabled(false);
			}
		});
		insertBtn.setBounds(152, 24, 100, 30);
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Arial", Font.BOLD, 14));
		insertPanel.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			/**
			 * This callback closes this JFrame and sets
			 * visible the {@link StudentsMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getAdminMenu().setEnabled(true);
				Main.getStudentsMenu().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(320, 309, 100, 30);
		contentPane.add(closeBtn);
	}
	
	/**
	 * Getter for Student lastname.
	 * It can be used from other classes
	 * for executing queries with lastname parameter.
	 * 
	 * @return		a String with the user's input.
	 */
	public String getLastname() {
		return lastname;
	}
}
