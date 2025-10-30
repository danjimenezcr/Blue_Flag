package controller;

import dao.UserTypesDAO;
import model.UserTypes;


import java.util.List;

public class UserTypesController {
    private final UserTypesDAO userTypesDAO = new UserTypesDAO();

    public List<UserTypes> getUserTypes() {
        try{
            return userTypesDAO.getUserTypes(null);
        } catch (Exception e){
            System.out.println("Error fetching user types: " + e.getMessage());
        }
        return null;
    }


}
