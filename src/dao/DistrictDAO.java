package dao;

import model.City;
import model.District;
import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class DistrictDAO {

    public List<District> getDistricts(Integer districtId, String name, Integer cityId) {
        String sql = "{  ? = call DISTRICTMANAGER.GETDISTRICTS(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter 
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // Input Parameters
            if(districtId != null)  cs.setInt(2, districtId);
            else cs.setNull(2, OracleTypes.INTEGER);
            if  (name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);
            if (cityId != null) cs.setInt(4, cityId);
            else cs.setNull(4, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<District> list = new ArrayList<>();
                while (rs.next()) {
                    City city = new CityDAO().getCities(rs.getInt("cityId"), null, null).get(0);
                    list.add(new District(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy"),
                            city
                    )
                    );
                    return list;
                }

            } catch (Exception e){
                System.out.println("Error getting the result for districts: " + e.getMessage());
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
        }
        return null;
    }


    public void addDistrict(District district) {
        String sql = "{call DISTRICTMANAGER.INSERTDISTRICT(?, ?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setString(1, district.getName());
            cs.setInt(2, district.getCity().getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDistrict(District district){
        String sql = "{call DISTRICTMANAGER.DELETEDISTRICT(?)}";
        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, district.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateDistrict(District district) {
        String sql = "{call CITYMANAGER.UPDATEDISTRICT(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, district.getId());
            cs.setString(2, district.getName());
            cs.setInt(3, district.getCity().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


