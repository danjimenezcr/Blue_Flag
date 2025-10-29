import model.PointsConvertion;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class PointsConvertionDAO {

    public List<PointsConvertion> getPointsConvertion(String id, String name, String CurrencyId, String MaterialTypeId) {
        String sql = "{ ? = call PointsConvertionManager.getPointsConvertion(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, name);
            cs.setString(4, CurrencyId);
            cs.setString(5, MaterialTypeId);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<PointsConvertion> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new PointsConvertion(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("createdBy"),
                                    rs.getDate("createdDateTime"),
                                    rs.getString("updatedBy"),
                                    rs.getDate("updatedDateTime"),
                                    rs.getInt("pointsPerKg"),
                                    rs.getInt("valueInCurrency"),
                                    rs.getString("currencyCode"),
                                    rs.getString("materialTypeName")
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