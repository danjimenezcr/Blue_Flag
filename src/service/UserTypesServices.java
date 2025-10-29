package service;

import dao.UserTypesDAO;
import model.UserTypes;

import java.util.List;

public class UserTypesServices {
    private final UserTypesDAO userTypesDAO = new UserTypesDAO();

    public List<UserTypes> getUserTypes() {
        return userTypesDAO.getUserTypes(null);
    }
}
