package controller;

import com.sun.tools.javac.Main;
import dao.MaterialTypeDAO;
import model.MaterialType;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MaterialTypeController {
    private final MaterialTypeDAO materialTypeDAO = new MaterialTypeDAO();

    public DefaultTableModel getMaterialTypeModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();

        try {
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Created By");
            tableModel.addColumn("Created At");

            List<MaterialType> materialTypes = materialTypeDAO.getMaterialType(null, null);

            for (MaterialType materialType : materialTypes) {
                tableModel.addRow(new Object[]{materialType.getId(), materialType.getName(), materialType.getCreatedBy(), materialType.getCreatedDateTime()});
            }

            return tableModel;
        } catch (Exception e){
            mainWindow.showError("Error loading Material Type: " + e.getMessage());
            return null;
        }
    }
}
