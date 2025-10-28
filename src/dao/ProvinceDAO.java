package dao;

import model.Province;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDAO {

    public List<Province> getProvinces(String provinceId, String name, String countryId) {
        String sql = "{ call ? := ProvinceManager.getProvinces(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            cs.setString(2, provinceId);
            cs.setString(3, name);
            cs.setString(4, countryId);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<Province> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Province(
                            rs.getString("provinceName"),
                            rs.getString("updatedBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("createdDateTime"),
                            rs.getInt("countryId")
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
