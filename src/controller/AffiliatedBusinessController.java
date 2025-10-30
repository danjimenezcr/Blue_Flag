package controller;

import dao.AffiliatedBusinessDAO;
import model.AffiliatedBusiness;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AffiliatedBusinessController {
    private final AffiliatedBusinessDAO business = new AffiliatedBusinessDAO();

    public DefaultTableModel getBusinessModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();
        try{
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("District");
            tableModel.addColumn("Manager");
            tableModel.addColumn("Contact");
            tableModel.addColumn("Open Hour");
            tableModel.addColumn("Close Hour");

            List<AffiliatedBusiness> affiliatedBusinesses = business.getAffiliatedBusiness(null, null, null, null);

            for (AffiliatedBusiness affiliatedBusiness : affiliatedBusinesses){
                tableModel.addRow(new Object[]{affiliatedBusiness.getAutorizedEntityId(), affiliatedBusiness.getName(), affiliatedBusiness.getDistrictId().getName(),
                        affiliatedBusiness.getManager(), affiliatedBusiness.getContact(), affiliatedBusiness.getOpenHour(), affiliatedBusiness.getCloseHour()});
            }

            return tableModel;
        }catch(Exception e){
            mainWindow.showError("Error getting Business Model: " + e.getMessage());
            return null;
        }
    }
}
