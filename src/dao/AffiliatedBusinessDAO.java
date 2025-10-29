package dao;

import model.AffiliatedBusiness;
import model.BusinessType;
import model.District;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class AffiliatedBusinessDAO {

    public List<AffiliatedBusiness> getAffiliatedBusiness(Integer id, String name, Integer businessTypeId, Integer districtId) {
        String sql = "{ call ? := adminAffiliatedBusiness.getAffiliatedBusiness(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters 
            cs.setInt(2, id);
            cs.setString(3, name);
            cs.setInt(4, businessTypeId);
            cs.setInt(5, districtId);

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<AffiliatedBusiness> list = new ArrayList<>();
                while (rs.next()) {
                    District district = new DistrictDAO().getDistricts(rs.getInt("districtId"), null, null).get(0);
                    BusinessType businessType = new BusinessTypeDAO().getBusinessType(rs.getInt("businessTypeId"), null).get(0);
                    list.add(new AffiliatedBusiness(
                            rs.getInt("id"),                
                            rs.getString("name"),          
                            rs.getString("openHour"),       
                            rs.getString("closeHour"),       
                            rs.getString("manager"),         
                            rs.getString("contact"),         
                            district,
                            businessType,
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