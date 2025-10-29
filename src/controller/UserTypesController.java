package controller;

import model.UserTypes;
import service.UserTypesServices;

import java.util.List;

public class UserTypesController {
    private final UserTypesServices userTypesServices = new UserTypesServices();

    public List<UserTypes> getUserTypes() {
        try{
            return userTypesServices.getUserTypes();
        } catch (Exception e){
            System.out.println("Error fetching user types: " + e.getMessage());
        }
        return null;
    }
}
