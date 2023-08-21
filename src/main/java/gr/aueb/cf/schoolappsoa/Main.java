package gr.aueb.cf.schoolappsoa;

import gr.aueb.cf.schoolappsoa.viewcontroller.*;

import java.awt.EventQueue;

/**
 * The driver class for the school-app-pro.
 * Initializes every JFrame and set visible the
 * login frame.
 *
 * @author Thanasis Chousiadas
 */
public class Main {
    private static Login loginForm;
    private static AdminMenu adminMenu;
    private static StudentsMenu studentsMenu;
    private static AdminInsertStudentsForm adminInsertStudentsForm;
    private static AdminUpdateDeleteStudentsForm adminUpdateDeleteStudentsForm;
    private static UsersMenu usersMenu;
    private static AdminInsertUsersForm adminInsertUsersForm;
    private static AdminUpdateDeleteUsersForm adminUpdateDeleteUsersForm;
    private static TeachersMenu teachersMenu;
    private static AdminInsertTeachersForm adminInsertTeachersForm;
    private static AdminUpdateDeleteTeachersForm adminUpdateDeleteTeachersForm;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {

                loginForm = new Login();
                loginForm.setVisible(true);

                adminMenu = new AdminMenu();
                adminMenu.setVisible(false);

                studentsMenu = new StudentsMenu();
                studentsMenu.setVisible(false);

                adminInsertStudentsForm = new AdminInsertStudentsForm();
                adminInsertStudentsForm.setVisible(false);

                adminUpdateDeleteStudentsForm = new AdminUpdateDeleteStudentsForm();
                adminUpdateDeleteStudentsForm.setVisible(false);

                usersMenu = new UsersMenu();
                usersMenu.setVisible(false);

                adminInsertUsersForm = new AdminInsertUsersForm();
                adminInsertUsersForm.setVisible(false);

                adminUpdateDeleteUsersForm = new AdminUpdateDeleteUsersForm();
                adminUpdateDeleteUsersForm.setVisible(false);

                teachersMenu = new TeachersMenu();
                teachersMenu.setVisible(false);

                adminInsertTeachersForm = new AdminInsertTeachersForm();
                adminInsertStudentsForm.setVisible(false);

                adminUpdateDeleteTeachersForm = new AdminUpdateDeleteTeachersForm();
                adminUpdateDeleteTeachersForm.setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Getter for {@link Login} JFrame class.
     *
     * @return a JFrame object.
     */
    public static Login getLoginForm() {
        return loginForm;
    }

    /**
     * Getter for {@link AdminMenu} JFrame class.
     *
     * @return a JFrame object.
     */
    public static AdminMenu getAdminMenu() {
        return adminMenu;
    }

    /**
     * Getter for {@link StudentsMenu} JFrame class.
     *
     * @return a JFrame object.
     */
    public static StudentsMenu getStudentsMenu() {
        return studentsMenu;
    }

    /**
     * Getter for {@link AdminInsertStudentsForm} JFrame class.
     *
     * @return a JFrame object.
     */
    public static AdminInsertStudentsForm getAdminInsertStudentsForm() {
        return adminInsertStudentsForm;
    }

    /**
     * Getter for {@link AdminUpdateDeleteStudentsForm} JFrame class.
     *
     * @return a JFrame object.
     */
    public static AdminUpdateDeleteStudentsForm getAdminUpdateDeleteStudentsForm() {
        return adminUpdateDeleteStudentsForm;
    }

    /**
     * Getter for {@link UsersMenu} JFrame class.
     *
     * @return a JFrame object.
     */
    public static UsersMenu getUsersMenu() {
        return usersMenu;
    }

    /**
     * Getter for {@link AdminInsertUsersForm} JFrame class.
     *
     * @return a JFrame object.
     */
    public static AdminInsertUsersForm getAdminInsertUsersForm() {
        return adminInsertUsersForm;
    }

    /**
     * Getter for {@link AdminUpdateDeleteUsersForm} JFrame class.
     *
     * @return a JFrame object.
     */
    public static AdminUpdateDeleteUsersForm getAdminUpdateDeleteUsersForm() {
        return adminUpdateDeleteUsersForm;
    }

    /**
     * Getter for {@link TeachersMenu} JFrame class.
     *
     * @return a JFrame object.
     */
    public static TeachersMenu getTeachersMenu() {
        return teachersMenu;
    }

    /**
     * Getter for {@link AdminInsertTeachersForm} JFrame class.
     *
     * @return a JFrame object.
     */
    public static AdminInsertTeachersForm getAdminInsertTeachersForm() {
        return adminInsertTeachersForm;
    }

    /**
     * Getter for {@link AdminUpdateDeleteTeachersForm} JFrame object.
     *
     * @return a JFrame object.
     */
    public static AdminUpdateDeleteTeachersForm getAdminUpdateDeleteTeachersForm() {
        return adminUpdateDeleteTeachersForm;
    }
}
