package dao;

import model.Country;
import model.Province;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    public List<Country> getCountries(Integer countryId, String name) {
        String sql = "{ ? = call COUNTRYMANAGER.GETCOUNTRIES(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            if(countryId != null) cs.setInt(2, countryId);
            else cs.setNull(2, OracleTypes.INTEGER);

            if (name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                System.out.println("getCountries");

                List<Country> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Country(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy")
                    ));

                }
                return list;
            } catch (Exception e){
                System.out.println("Error getting the result for cities: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void addCountry(Country country) {
        String sql = "{call COUNTRYMANAGER.INSERTCOUNTRY(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setString(1, country.getName());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCountry(Country country) {
        String sql = "{call COUNTRYMANAGER.DELETECOUNTRY(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, country.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateCountry(Country country) {
        String sql = "{call COUNTRYMANAGER.UPDATECOUNTRY(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, country.getId());
            cs.setString(2, country.getName());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
