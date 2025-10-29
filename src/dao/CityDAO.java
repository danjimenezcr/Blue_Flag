package dao;

import model.City;
import model.Province;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {

    public List<City> getCities(Integer cityId, String name, Integer provinceId) {
        String sql = "{ ? = call CITYMANAGER.GETCITIES(?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter 
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // Input Parameters
            cs.setInt(2, cityId);
            if (name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);
            if (provinceId != null) cs.setInt(4, provinceId);
            else cs.setNull(4, OracleTypes.INTEGER);

            cs.execute();
            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<City> list = new ArrayList<>();
                while (rs.next()) {
                    Province province = new ProvinceDAO().getProvinces(rs.getInt("PROVINCEID"), null, null).get(0);
                    list.add(new City(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy"),
                            province
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
