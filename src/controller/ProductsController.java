package controller;

import dao.ProductDAO;
import model.Product;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductsController {
    private final ProductDAO productDAO = new ProductDAO();

    public DefaultTableModel getProductsTable(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();
        try {
            tableModel.addColumn("ID");
            tableModel.addColumn("Description");
            tableModel.addColumn("cost");
            tableModel.addColumn("Authorized Entity");
            List<Product> products = productDAO.getProducts(null, null, null);
            for (Product product : products) {
                tableModel.addRow(new Object[]{product.getId(), product.getDescription(), product.getCost(), product.getAutorizedEntity().getName()});
            }
            return tableModel;
        } catch (Exception e) {
            mainWindow.showError("Error getting products table: " + e.getMessage());
            return null;
        }
    }
}
