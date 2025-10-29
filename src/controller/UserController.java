package controller;

import model.User;
import service.UserService;
import view.SignUpWindow;

public class UserController {
    private final UserService userService = new UserService();

    public void handleRegister(User newUser, SignUpWindow signUpWindow) {
        try {
            boolean sucess = userService.registerUser(newUser);
            if (sucess) {
                signUpWindow.showMessage("User registered successfully");
                signUpWindow.clearFields();
            }else  {
                signUpWindow.showMessage("User registration failed");
            }
        } catch (Exception e){
            signUpWindow.showError("Error registering user: " + e.getMessage());
        }
    }
}
