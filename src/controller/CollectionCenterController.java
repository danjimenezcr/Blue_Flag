package controller;

import dao.CollectionCenterDAO;
import model.CollectionCenter;
import view.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CollectionCenterController {
    private final CollectionCenterDAO collectionCenterDAO = new CollectionCenterDAO();

    public DefaultTableModel getCollectionCenterModel(MainWindow mainWindow) {
        DefaultTableModel collectionCenterModel = new DefaultTableModel();

        try {
            collectionCenterModel.addColumn("ID");
            collectionCenterModel.addColumn("Name");
            collectionCenterModel.addColumn("District");
            collectionCenterModel.addColumn("Manager");
            collectionCenterModel.addColumn("Contact");
            collectionCenterModel.addColumn("Open Hour");
            collectionCenterModel.addColumn("Close Hour");

            List<CollectionCenter> centers = collectionCenterDAO.getCollectionCenter(null, null, null, null);
            for (CollectionCenter center: centers) {
                collectionCenterModel.addRow(new Object[]{center.getAutorizedEntityId(), center.getName(), center.getDistrict().getName(), center.getManager(), center.getContact()});
            }
            return collectionCenterModel;


        } catch (Exception e) {
            mainWindow.showError("Error getting collection center model: " + e.getMessage());
            return null;
        }
    };
}
