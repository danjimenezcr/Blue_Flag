package dao;

import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class UsersDAO {

    public List<User> getUsers(String userId, String username, String name, String idNumber, String provinceId, String districtId, String cityId) {
        String sql = "{ ? = call USERMANAGER.GETUSERS(?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, userId);
            cs.setString(3, username);
            cs.setString(4, name);
            cs.setString(5, idNumber);
            cs.setString(6, provinceId);
            cs.setString(7, districtId);
            cs.setString(8, cityId);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new User(rs.getInt("id"),
                            rs.getString("FIRSTNAME"),
                                    rs.getString("SECONDNAME"),
                                    rs.getString("LASTNAME"),
                                    rs.getString("SECONDLASTNAME"),
                            rs.getDate("BIRTHDATE"),
                            rs.getString("username"),
                            rs.getString("PASSWORD"),
                            rs.getString("IDNUMBER"),
                            rs.getString("ADDRESS") + ", " +
                                    rs.getString("districtName") + ", " + rs.getString("cityName")
                            + ", " + rs.getString("countryName"),
                            rs.getString("PHOTOURL"),
                            rs.getString("CREATEDBY"),
                            rs.getDate("CREATEDDATE"),
                            rs.getString("UPDATEDBY"),
                            rs.getDate("UPDATEDDATE"),
                            rs.getString("genderName"),
                            rs.getString("idTypeName"),
                            rs.getString("userTypeName"),
                            rs.getInt("balance")
                    )
                    );
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
    
    public void addUser(User user){
        
        String sql = '{call USERMANAGER.INSERTUSER(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}';
    
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);
            
            //Input parameters
            cs.setString(1, user.getFirstName());
            cs.setString(1, user.getFirstName());
            
        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }
}
