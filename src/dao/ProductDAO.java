package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ProductDAO {

    public List<Product> getProducts(Integer productId, String description, Integer authorizedEntityId) {
        String sql = "{ ? = call PRODUCTMANAGER.GETPRODUCTS(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.registerOutParameter(1, OracleTypes.CURSOR);

            cs.setInt(2, productId);
            cs.setString(3, description);
            cs.setInt(4, authorizedEntityId);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<Product> list = new ArrayList<>();
                while (rs.next()) {
                    AutorizedEntity autorizedEntity = new AutorizedEntityDAO().getAutorizedEntity(rs.getInt("authorizedEntityId"), null, null, null).get(0);

                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getString("photoUrl"),
                            rs.getDouble("cost"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy"),
                            autorizedEntity
                    ));
                }
                return list;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to database! " + e.getMessage());
        }

        return null;
    }
    public static void main(String[] args) {

        System.out.println(new ProductDAO().getProducts(0, null, null));
    }
}


