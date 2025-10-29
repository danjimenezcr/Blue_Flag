package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

import javax.xml.transform.Result;


public class UsersDAO {

    public List<User> getUsers(Integer userId, String username, String name, String idNumber, String provinceId, String districtId, String cityId) {
        String sql = "{ ? = call USERMANAGER.GETUSERS(?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setInt(2, userId);
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
            cs.setString(1, user.getFirstName());
            cs.setDate(2, (Date) user.getBirthDate());
            cs.setString(3, user.getUsername());
            cs.setString(4, user.getSecondName());
            cs.setString(5, user.getLastName());
            cs.setString(6, user.getSecondLastName());
            cs.setString(7, user.getPassword());
            cs.setString(8, user.getPhotoUrl());
            cs.setInt(9, user.getGender().getId());
            cs.setInt(10, user.getIdType().getId());
            cs.setInt(11, user.getUserType().getId());
            cs.setInt(12, user.getDistrict().getId());
            cs.setString(13, user.getAddress());
            cs.setString(14, user.getIdNumber());

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
//        String sql = "{? = call COUNTRYMANAGER.GETCOUNTRIES(0, null) }";
//        try (Connection conn = DBConnection.getConnection()){
//            CallableStatement cs = conn.prepareCall(sql);
//            cs.registerOutParameter(1, OracleTypes.CURSOR);
//
//            cs.execute();
//            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
//                while (rs.next()) {
//                    System.out.println(rs.getInt("id"));
//                }
//            }
//        } catch (Exception e){
//            System.out.println("Failed to connect to database: " + e.getMessage());
//            e.printStackTrace();
//        }
        System.out.println(new ProvinceDAO().getProvinces(null, null, null));
    }

}
