package controller;


import model.Genders;
import service.GenderService;

import java.util.List;

public class GenderController {
    private final GenderService genderService = new GenderService();

    public List<Genders> getGenders() {
        try {
            return genderService.getGenders();
        } catch (Exception e) {
            System.out.println("Error getting genders: " + e.getMessage());
            return null;
        }
    }

}
