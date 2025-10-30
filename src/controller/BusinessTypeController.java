package controller;

import dao.BusinessTypeDAO;
import model.BusinessType;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BusinessTypeController {
    private BusinessTypeDAO businessTypeDAO = new BusinessTypeDAO();

    public DefaultTableModel getBusinessTypeModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();
        try {
            tableModel.addColumn("ID");
            tableModel.addColumn("Description");
            tableModel.addColumn("Created By");
            tableModel.addColumn("Created DateTime");
            List<BusinessType> businessTypes = businessTypeDAO.getBusinessType(null, null);
            for (BusinessType businessType : businessTypes) {
                tableModel.addRow(new Object[]{businessType.getId(), businessType.getDescription(), businessType.getCreatedBy(), businessType.getCreatedDateTime()});
            }
            return tableModel;
        } catch (Exception e){
            mainWindow.showError("Error getting Business Type Model: " + e.getMessage());
            return null;
        }
    }
}
