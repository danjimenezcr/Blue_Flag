package dao;

import model.MaterialType;
import model.MaterialTypeXCollectionCenter;
import model.PointsConvertion;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class PointsConvertionDAO {

    public int addPointsConvertion(PointsConvertion pointsConvertion) {
        String sql = "{ call PointsConvertionManager.insertPointsConvertion(?,?,?,?,?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, pointsConvertion.getName());
            cs.setDouble(2, pointsConvertion.getPointsPerKg());
            cs.setDouble(3, pointsConvertion.getValueInCurrency());
            cs.setInt(4, pointsConvertion.getCurrencyId().getId());
            cs.setInt(5, pointsConvertion.getMaterialTypeId().getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public List<PointsConvertion> getPointsConvertion(Integer id, String name, Integer CurrencyId, Integer MaterialTypeId) {
        String sql = "{ ? = call PointsConvertionManager.getPointsConvertion(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if (name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);

            if (CurrencyId != null) cs.setInt(4, CurrencyId);
            else cs.setNull(4, OracleTypes.INTEGER);

            if (MaterialTypeId != null) cs.setInt(5, MaterialTypeId);
            else cs.setNull(5, OracleTypes.INTEGER);


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

    public void deletePointsConvertion(PointsConvertion pointsConvertion){
        String sql = "{call PointsConvertionManager.deletePointsConvertion(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, pointsConvertion.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updatePointsConvertion(PointsConvertion pointsConvertion){
        String sql = "{call PointsConvertionManager.updatePointsConvertion(?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, pointsConvertion.getId());
            cs.setString(2, pointsConvertion.getName());
            cs.setDouble(3, pointsConvertion.getPointsPerKg());
            cs.setDouble(4, pointsConvertion.getValueInCurrency());
            cs.setInt(5, pointsConvertion.getCurrencyId().getId());
            cs.setInt(6, pointsConvertion.getMaterialTypeId().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}