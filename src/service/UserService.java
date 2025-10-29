package service;

import dao.UsersDAO;
import model.User;
import model.UserTypes;

public class UserService {
    private final UsersDAO usersDAO = new UsersDAO();

    public boolean registerUser(User user) throws Exception {
        if(user.getUsername() == null || user.getPassword() == null){
            throw new IllegalArgumentException("Username or password is null");
        }
        if(usersDAO.userExits(null, user.getUsername())){
            return false;
        }
        UserTypes ut = new UserTypes();
        ut.setId(1);
        user.setUserType(ut);
        usersDAO.addUser(user);
        return true;
    }
}
