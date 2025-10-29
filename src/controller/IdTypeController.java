package controller;

import model.IdType;
import service.IdTypeService;

import java.util.List;

public class IdTypeController {
    private final IdTypeService idTypeService = new IdTypeService();

    public List<IdType> getIdTypes() {
        try{
            return idTypeService.getIdTypes();
        } catch (Exception e){
            System.out.println("Error fetching Id Types: " + e.getMessage());
        }
        return null;
    }
}
