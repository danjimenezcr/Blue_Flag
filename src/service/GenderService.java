package service;

import dao.GenderDAO;
import model.Genders;

import java.util.List;

public class GenderService {
    private final GenderDAO genderDAO = new GenderDAO();

    public List<Genders> getGenders() {
        return genderDAO.getGenders(null);
    }

}
