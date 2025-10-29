package service;

import dao.IdTypeDAO;
import model.IdType;

import java.util.List;

public class IdTypeService {
    private final IdTypeDAO idTypeDAO = new IdTypeDAO();

    public List<IdType> getIdTypes() {
        return idTypeDAO.getIdTypes(null);
    }
}
