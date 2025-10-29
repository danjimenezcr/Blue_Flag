package dao;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ProductXUserDAO {

    public void insertProductRedeem(Integer userId, Integer productId, Integer quantity) {

        String sql = "{call PRODUCTXUSERMANAGER.INSERTPRODUCTREDEEM(?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            if(userId != null)  cs.setInt(2, userId);
            else cs.setNull(2, OracleTypes.INTEGER);
            if  (productId != null) cs.setInt(3, productId);
            else cs.setNull(3, OracleTypes.VARCHAR);
            if (quantity != null) cs.setInt(4, quantity);
            else cs.setNull(4, OracleTypes.INTEGER);

            cs.execute();
            System.out.println("Product redeem inserted successfully.");

        } catch (Exception e) {
            System.out.println("Error inserting product redeem: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public List<String> getProductRedeems(int userId, int productId) {

        String sql = "{ ? = call PRODUCTXUSERMANAGER.GETPRODUCTREDEEMS(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.registerOutParameter(1, OracleTypes.CURSOR);

            cs.setInt(2, userId);
            cs.setInt(3, productId);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<String> list = new ArrayList<>();

                while (rs.next()) {
                    String result = "User: " + rs.getString("NAME") +
                            " | Product: " + rs.getString("DESCRIPTION") +
                            " | Quantity: " + rs.getInt("QUANTITY") +
                            " | Total Cost: " + rs.getDouble("TOTALCOST");

                    list.add(result);
                }

                return list;
            }

        } catch (Exception e) {
            System.out.println("Error fetching product redeems: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
