package dao;

import model.UserTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class UserTypesDAO {

    public List<UserTypes> getUserTypes(int id) {
        String sql = "{ call ? = userTypesManager.getUserTypes(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT cursor
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parámetro
            cs.setInt(2, id);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<UserTypes> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new UserTypes(
                            rs.getInt("ID"),
                            rs.getString("NAME"),
                            rs.getString("CREATEDBY"),
                            rs.getDate("CREATEDDATE"),
                            rs.getString("UPDATEDBY"),
                            rs.getDate("UPDATEDDATE")
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
