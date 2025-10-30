package controller;


import dao.GenderDAO;
import model.Genders;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GenderController {
    private final GenderDAO genderDAO = new GenderDAO();

    public List<Genders> getGenders() {
        try {
            return genderDAO.getGenders(null);
        } catch (Exception e) {
            System.out.println("Error getting genders: " + e.getMessage());
            return null;
        }
    }

    public DefaultTableModel getGenderModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();
        try {
            List<Genders> genders = genderDAO.getGenders(null);
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Created By");
            tableModel.addColumn("Created At");
            for (Genders gender : genders) {
                tableModel.addRow(new Object[]{gender.getId(), gender.getName(), gender.getCreatedBy(), gender.getCreatedDate()});
            }
        return tableModel;
        } catch (Exception e) {
            mainWindow.showError("Error loading genders: " + e.getMessage());
            return null;
        }
    }


}
