package service;

import dao.UsersDAO;
import model.User;

import java.util.List;

public class AuthService {
    private UsersDAO userDao = new UsersDAO();

    public AuthService() {}
    public User login(String username, String password){
        List<User> users = userDao.getUsers(null, username, null, null, null, null, null);
  
        if(users != null){
            if(users.get(0).getPassword().equals(password)) return users.get(0);
        }
        return null;
    }


}
