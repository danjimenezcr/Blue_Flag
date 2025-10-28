package dao;

import model.District;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class DistrictDAO {

    public List<District> getDistricts(String districtId, String name, String cityId) {
        String sql = "{ call ? := DistrictManager.getDistricts(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter 
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // Input Parameters
            cs.setString(2, districtId);
            cs.setString(3, name);
            cs.setString(4, cityId);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<District> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new District(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy"),
                            rs.getInt("cityId")
                    ));
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
