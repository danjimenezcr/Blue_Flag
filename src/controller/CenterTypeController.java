package controller;

import dao.CenterTypeDAO;
import model.CenterType;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CenterTypeController {
    private CenterTypeDAO centerTypeDAO = new CenterTypeDAO();

    public DefaultTableModel getCenterTypeModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();
        try {
            tableModel.addColumn("ID");
            tableModel.addColumn("Description");
            tableModel.addColumn("Created By");
            tableModel.addColumn("Created DateTime");

            List<CenterType> centerTypes = centerTypeDAO.getCenterType(null, null);
            for (CenterType centerType : centerTypes) {
                tableModel.addRow(new Object[]{centerType.getId(), centerType.getDescription(), centerType.getCreatedBy(), centerType.getCreatedDateTime()});
            }

            return tableModel;
        } catch (Exception e){
            mainWindow.showError("Error getting center type model: " + e.getMessage());
            return null;
        }
    }
}
