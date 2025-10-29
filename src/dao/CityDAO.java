package dao;

import model.City;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {

    public List<City> getCities(int cityId, String name, String provinceId) {
        String sql = "{ call ? := CityManager.getCities(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter 
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // Input Parameters
            cs.setInt(2, cityId);
            cs.setString(3, name);
            cs.setString(4, provinceId);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<City> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new City(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy"),
                            rs.getInt("provinceId")
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
