package controller;

import dao.PointsConvertionDAO;
import model.PointsConvertion;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PointsController {
    private PointsConvertionDAO pointsConvertionDAO = new PointsConvertionDAO();

    public DefaultTableModel getPointsModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("ID");
        tableModel.addColumn("Description");
        tableModel.addColumn("Created By");
        tableModel.addColumn("Created DateTime");
        tableModel.addColumn("Material Type");
        tableModel.addColumn("Currency");
        try {
            List<PointsConvertion> pointsConvertions = pointsConvertionDAO.getPointsConvertion(null, null, null, null);
            for (PointsConvertion pointsConvertion : pointsConvertions) {
                tableModel.addRow(new Object[]{pointsConvertion.getId(), pointsConvertion.getName(), pointsConvertion.getCreatedBy(), pointsConvertion.getCreatedDateTime(),
                pointsConvertion.getMaterialTypeId().getName(), pointsConvertion.getCurrencyId().getCode()});
            }

            return tableModel;
        } catch (Exception e) {
            mainWindow.showError("Error getting points convertion: " + e.getMessage());
            return null;
        }

    }
}
