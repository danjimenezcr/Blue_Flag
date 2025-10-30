package controller;

import dao.IdTypeDAO;
import model.IdType;

import java.util.List;

public class IdTypeController {
    private final IdTypeDAO idTypeDAO = new IdTypeDAO();

    public List<IdType> getIdTypes() {
        try{
            return idTypeDAO.getIdTypes(null);
        } catch (Exception e){
            System.out.println("Error fetching Id Types: " + e.getMessage());
        }
        return null;
    }
}
