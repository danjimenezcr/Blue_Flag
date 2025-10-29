package controller;

import model.User;
import service.AuthService;
import view.MainWindow;
import view.LoginWindow;

public class LoginController {
    private final AuthService authService = new AuthService();

    public void handleLogin(String username, String password, LoginWindow loginWindow){
        User user = authService.login(username, password);

        if(user != null){
            loginWindow.dispose();
            new MainWindow(user).setVisible(true);
        } else{
            loginWindow.showError("Username or password are incorrect!!!");
        }
    }

}
