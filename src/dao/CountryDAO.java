package dao;

import model.Country;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    public List<Country> getCountries(String countryId, String name) {
        String sql = "{ call ? := CountryManager.getCountries(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            cs.setString(2, countryId);
            cs.setString(3, name);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<Country> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Country(
                            rs.getString("countryName"),
                            rs.getString("updatedBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("createdDateTime")
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
