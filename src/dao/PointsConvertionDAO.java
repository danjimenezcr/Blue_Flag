package dao;

import model.MaterialType;
import model.PointsConvertion;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class PointsConvertionDAO {

    public List<PointsConvertion> getPointsConvertion(Integer id, String name, Integer CurrencyId, Integer MaterialTypeId) {
        String sql = "{ ? = call PointsConvertionManager.getPointsConvertion(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setInt(2, id);
            cs.setString(3, name);
            cs.setInt(4, CurrencyId);
            cs.setInt(5, MaterialTypeId);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<PointsConvertion> list = new ArrayList<>();
                while (rs.next()) {
                    model.Currency currency = new CurrencyDAO().getCurrencies(rs.getInt("currencyCode"), null, null).get(0);
                    MaterialType materialType = new MaterialTypeDAO().getMaterialType(rs.getInt("materialTypeId"), null).get(0);
                    list.add( new PointsConvertion(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("createdBy"),
                                    rs.getDate("createdDateTime"),
                                    rs.getString("updatedBy"),
                                    rs.getDate("updatedDateTime"),
                                    rs.getInt("pointsPerKg"),
                                    rs.getInt("valueInCurrency"),
                                    currency,
                                    materialType
                            )
                    );
                    return list;
                }

            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
        }
        return null;
    }

}