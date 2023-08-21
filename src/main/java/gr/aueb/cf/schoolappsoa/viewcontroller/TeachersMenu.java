package gr.aueb.cf.schoolappsoa.viewcontroller;

import gr.aueb.cf.schoolappsoa.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 * This JFrame is called if the user has 
 * clicked the Teachers Button from the
 * {@link AdminMenu} JFrame class.
 * The user can search for a teacher with the lastname
 * in the database or add a new teacher.
 * 
 * @author Thanasis Chousiadas.
 */
public class TeachersMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField lastnameTxt;
	private String lastname;

	/**
	 * The constructor of JFrame {@link TeachersMenu} class.
	 */
	public TeachersMenu() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("eduv2.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		setTitle("Teachers Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		searchPanel.setBounds(22, 10, 404, 159);
		contentPane.add(searchPanel);
		
		JLabel lastnameLbl = new JLabel("Lastname");
		lastnameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		lastnameLbl.setFont(new Font("Arial", Font.BOLD, 14));
		lastnameLbl.setBounds(167, 24, 69, 17);
		searchPanel.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(76, 51, 250, 30);
		searchPanel.add(lastnameTxt);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			/**
			 * This callback sets visible the {@link AdminUpdateDeleteTeachersForm},
			 * where the results of searching are shown.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				lastname = lastnameTxt.getText().trim();
				Main.getAdminUpdateDeleteTeachersForm().setVisible(true);
				Main.getTeachersMenu().setEnabled(false);
			}
		});
		searchBtn.setForeground(Color.BLUE);
		searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
		searchBtn.setBounds(151, 106, 100, 30);
		searchPanel.add(searchBtn);
		
		JPanel insertPanel = new JPanel();
		insertPanel.setLayout(null);
		insertPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		insertPanel.setBounds(22, 195, 404, 78);
		contentPane.add(insertPanel);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {
			/**
			 * This callback set visible the {@link AdminInsertTeachersForm}
			 * JFrame class where the user can add a new Teacher.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getAdminInsertTeachersForm().setVisible(true);
				Main.getTeachersMenu().setEnabled(false);
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
			 * visible the {@link TeachersMenu}.
			 * 
			 * @param e			an {@link ActionEvent} object, user's event.
			 */
			public void actionPerformed(ActionEvent e) {
				Main.getAdminMenu().setEnabled(true);
				Main.getTeachersMenu().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
		closeBtn.setBounds(326, 294, 100, 30);
		contentPane.add(closeBtn);
	}
	
	/**
	 * Getter for Teacher lastname.
	 * It can be used from other classes
	 * for executing queries with lastname parameter.
	 * 
	 * @return		a String with the user's input.
	 */
	public String getLastname() {
		return lastname;
	}
}
