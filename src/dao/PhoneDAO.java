package dao;

import model.Phones;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhonesDAO {

    public List<Phones> getPhones(String userId, String phoneId) {
        String sql = "{ call ? := PhoneManager.getPhones(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            cs.setString(2, userId);
            cs.setString(3, phoneId);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<Phones> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Phones(
                            rs.getInt("id"),
                            rs.getInt("phoneNumber"),
                            rs.getString("createdBy"),
                            rs.getDate("createdDate"),
                            rs.getString("updatedBy"),
                            rs.getDate("updatedDate"),
                            rs.getInt("labelId"),
                            rs.getInt("userId")
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
