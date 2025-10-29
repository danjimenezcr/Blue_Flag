package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

import javax.xml.transform.Result;


public class UsersDAO {

    public List<User> getUsers(Integer userId, String username, String name, String idNumber, Integer provinceId, Integer districtId, Integer cityId) {
        String sql = "{ ? = call USERMANAGER.GETUSERS(?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            if (userId != null) cs.setInt(2, userId);
            else cs.setNull(2, OracleTypes.INTEGER);
            if (username != null) cs.setString(3, username);
            else cs.setNull(3, OracleTypes.VARCHAR);
            if (name != null) cs.setString(4, name);
            else cs.setNull(4, OracleTypes.VARCHAR);
            if (idNumber != null) cs.setString(5, idNumber);
            else cs.setNull(5, OracleTypes.VARCHAR);
            if (provinceId != null) cs.setInt(6, provinceId);
            else cs.setNull(6, OracleTypes.INTEGER);
            if (districtId != null) cs.setInt(7, districtId);
            else cs.setNull(7, OracleTypes.INTEGER);
            if (cityId != null) cs.setInt(8, cityId);
            else cs.setNull(8, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    District district = new DistrictDAO().getDistricts(rs.getInt("districtId"), null, null).get(0);
                    Genders gender = new GenderDAO().getGenders(rs.getInt("genderId")).get(0);
                    IdType idtype = new IdTypeDAO().getIdTypes(rs.getInt("idTypeId")).get(0);
                    UserTypes usertype = new UserTypesDAO().getUserTypes(rs.getInt("userTypeId")).get(0);

                    list.add( new User(rs.getInt("id"),
                                    rs.getString("FIRSTNAME"),
                                    rs.getString("SECONDNAME"),
                                    rs.getString("LASTNAME"),
                                    rs.getString("SECONDLASTNAME"),
                                    rs.getDate("BIRTHDATE"),
                                    rs.getString("username"),
                                    rs.getString("PASSWORD"),
                                    rs.getString("IDNUMBER"),
                                    rs.getString("ADDRESS"),
                                    rs.getString("PHOTOURL"),
                                    rs.getString("CREATEDBY"),
                                    rs.getDate("CREATEDDATE"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATE"),
                                    gender,
                                    idtype,
                                    usertype,
                                    rs.getInt("balance"),
                                    district
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
        String sql = "{call USERMANAGER.INSERTUSER(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);
            
            //Input parameters
            if(user.getFirstName() != null) cs.setString(1, user.getFirstName());
            else cs.setNull(1, OracleTypes.VARCHAR);
            if(user.getBirthDate() != null) cs.setDate(2, (java.sql.Date) user.getBirthDate());
            else cs.setNull(2, OracleTypes.DATE);
            if(user.getUsername() != null) cs.setString(3, user.getUsername());
            else cs.setNull(3, OracleTypes.VARCHAR);
            if(user.getSecondName() != null) cs.setString(4, user.getSecondName());
            else cs.setNull(4, OracleTypes.VARCHAR);
            if(user.getLastName() != null) cs.setString(5, user.getLastName());
            else cs.setNull(5, OracleTypes.VARCHAR);
            if(user.getSecondLastName() != null) cs.setString(6, user.getSecondLastName());
            else cs.setNull(6, OracleTypes.VARCHAR);
            if(user.getPassword() != null) cs.setString(7, user.getPassword());
            else cs.setNull(7, OracleTypes.VARCHAR);

            if(user.getPhotoUrl() != null) cs.setString(8, user.getPhotoUrl());
            else cs.setNull(8, OracleTypes.VARCHAR);
            if(user.getGender() != null) cs.setInt(9,user.getGender().getId());
            else cs.setNull(9, OracleTypes.INTEGER);
            if(user.getIdType() != null) cs.setInt(10,user.getIdType().getId());
            else cs.setNull(10, OracleTypes.INTEGER);
            if(user.getDistrict() != null) cs.setInt(11,user.getDistrict().getId());
            else cs.setNull(11,OracleTypes.INTEGER);
            if(user.getAddress() != null) cs.setString(12,user.getAddress());
            else cs.setNull(13, OracleTypes.VARCHAR);
            if(user.getIdNumber() != null) cs.setString(14, user.getIdNumber());
            else cs.setNull(14, OracleTypes.VARCHAR);

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
        
    public void deleteUser(User user){
        String sql = "{call USERMANAGER.DELETEUSER(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, user.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateUser(User user){
        String sql = "{call USERMANAGER.INSERTUSER(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, user.getId());
            cs.setString(2, user.getFirstName());
            cs.setDate(3, (Date) user.getBirthDate());
            cs.setString(4, user.getLastName());
            cs.setInt(5, user.getGender().getId());
            cs.setInt(6, user.getDistrict().getId());
            cs.setString(7, user.getAddress());
            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(new DistrictDAO().getDistricts(0, null,  null));
    }

}
