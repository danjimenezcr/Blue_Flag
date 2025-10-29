package dao;

import model.Country;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    public List<Country> getCountries(Int countryId, String name) {
        String sql = "{ call ? := CountryManager.getCountries(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            cs.setInt(2, countryId);
            cs.setString(3, name);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

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
