package controller;

import dao.UsersDAO;
import model.User;
import view.MainWindow;
import view.LoginWindow;

import java.util.List;

public class LoginController {

    public void handleLogin(String username, String password, LoginWindow loginWindow){

        try{
            List<User> users = new UsersDAO().getUsers(null, username, null, null, null, null, null);
            User user = null;
            if(users != null){
                if(users.get(0).getPassword().equals(password)) user = users.get(0);
            }


            if (user != null) {
                loginWindow.dispose();
                new MainWindow(users.get(0)).setVisible(true);
            }
            else{
                loginWindow.showError("Username or password incorrect");
            }
        } catch (Exception e){
            loginWindow.showError("Unexpected error!");
        }
    }

}
