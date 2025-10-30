package controller;

import dao.UsersDAO;
import model.User;
import model.UserTypes;
import view.MainWindow;
import view.SignUpWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UserController {
    private final UsersDAO usersDAO = new UsersDAO();

    public void handleRegister(User newUser, SignUpWindow signUpWindow) {
        try {
            if(newUser.getUsername() == null || newUser.getPassword() == null){
                signUpWindow.showError("Username or password is null");
                return;

            }
            if(usersDAO.userExits(null, newUser.getUsername())){
                signUpWindow.showError("Username already exists");
                return;
            }
            UserTypes ut = new UserTypes();
            ut.setId(1);
            newUser.setUserType(ut);

            usersDAO.addUser(newUser);

            signUpWindow.showMessage("User registered successfully");
            signUpWindow.clearFields();

        } catch (Exception e){
            signUpWindow.showError("Error registering user: " + e.getMessage());
        }
    }

    public DefaultTableModel getUsersTable(MainWindow mainWindow) {
        try {
            List<User> users = usersDAO.getUsers(null, null, null, null, null, null, null);
            DefaultTableModel tableModel = new DefaultTableModel();

            tableModel.addColumn("Username");
            tableModel.addColumn("FirstName");
            tableModel.addColumn("LastName");
            tableModel.addColumn("User Type");
            tableModel.addColumn("Address");
            tableModel.addColumn("Gender");
            tableModel.addColumn("ID Number");
            tableModel.addColumn("Birth Date");
            for (User user : users) {
                tableModel.addRow(new Object[]{user.getUsername(), user.getFirstName(), user.getLastName(),
                        user.getUserType().getName(), user.getAddress(), user.getGender().getName(), user.getIdNumber(),
                        user.getBirthDate()});
            }
            return tableModel;
        } catch (Exception e) {
            mainWindow.showError("Error loading users: " + e.getMessage());
            return null;
        }
    }


}
