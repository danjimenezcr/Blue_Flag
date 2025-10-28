package controller;

import model.User;
import service.AuthService;
import view.AdminWindow;
import view.LoginWindow;

public class LoginController {
    private final AuthService authService = new AuthService();

    public void handleLogin(String username, String password, LoginWindow loginWindow){
        User user = authService.login(username, password);

        if(user != null){
            loginWindow.dispose();
            new AdminWindow(user).setVisible(true);
        }
        loginWindow.showError("Username or password are incorrect!!!");
        
    };

}
