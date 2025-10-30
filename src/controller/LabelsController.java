package controller;

import dao.LabelDAO;
import model.Labels;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LabelsController {
    private final LabelDAO labelDAO = new LabelDAO();

    public DefaultTableModel getLabels(MainWindow mainWindow) {
        DefaultTableModel model = new DefaultTableModel();

        try {
            List<Labels> labels = labelDAO.getLabels(null);
            model.addColumn("Id");
            model.addColumn("Description");
            model.addColumn("Created By");
            model.addColumn("Created At");
            for (Labels label : labels) {
                model.addRow(new Object[]{label.getId(), label.getDescription(), label.getCreatedBy(), label.getCreatedDate()});
            }

            return model;

        } catch (Exception e) {
            mainWindow.showError("Error loading labels: " + e.getMessage());
            return null;
        }
    }

}
