package dao;

import model.AffiliatedBusiness;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class AffiliatedBusinessDAO {

    public List<AffiliatedBusiness> getAffiliatedBusiness(String id, String name, String businessTypeId, String districtId) {
        String sql = "{ call ? := adminAffiliatedBusiness.getAffiliatedBusiness(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters 
            cs.setString(2, id);
            cs.setString(3, name);
            cs.setString(4, businessTypeId);
            cs.setString(5, districtId);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<AffiliatedBusiness> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new AffiliatedBusiness(
                            rs.getInt("id"),                
                            rs.getString("name"),          
                            rs.getString("openHour"),       
                            rs.getString("closeHour"),       
                            rs.getString("manager"),         
                            rs.getString("contact"),         
                            rs.getInt("districtId"),       
                            rs.getInt("businessTypeId"),      
                            rs.getString("createdBy"),        
                            rs.getDate("createdDateTime"),    
                            rs.getString("updatedBy"),        
                            rs.getDate("updatedDateTime")    
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