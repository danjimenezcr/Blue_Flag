package dao;

import model.Genders;
import model.UserTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class UserTypesDAO {

    public List<UserTypes> getUserTypes(Integer id) {
        String sql = "{ ? = call userTypesManager.getUserTypes(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT cursor
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parámetro
            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

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

    public void addUserTypes(UserTypes userTypes) {
        String sql = "{call USERTYPESMANAGER.INSERTUSERTYPE(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setString(1, userTypes.getName());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUserTypes(UserTypes userTypes) {
        String sql = "{call USERTYPESMANAGER.DELETEUSERTYPE(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, userTypes.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateUserTypes(UserTypes userTypes) {
        String sql = "{call USERTYPESMANAGER.UPDATEUSERTYPE(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, userTypes.getId());
            cs.setString(2, userTypes.getName());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
