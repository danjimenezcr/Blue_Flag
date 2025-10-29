package dao;

import model.BusinessType;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class BusinessTypeDAO {

    public int addBusinessType(BusinessType businessType) {
    String sql = "{ call adminBusinessType.insertBusinessType(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
        CallableStatement cs = conn.prepareCall(sql);

        cs.setString(1, businessType.getDescription());

        cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public List<BusinessType> getBusinessType(Integer id, String description) {
        String sql = "{ ? = call adminBusinessType.getBusinessType(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if(description != null) cs.setString(3, description);
            else cs.setNull(3, OracleTypes.VARCHAR);

            //Parameters Input
            cs.setInt(2, id);
            cs.setString(3, description);
            System.out.println(cs);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<BusinessType> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new BusinessType(rs.getInt("id"),
                                    rs.getString("description"),
                                    rs.getString("createdBy"),
                                    rs.getDate("createdDateTime"),
                                    rs.getString("updatedBy"),
                                    rs.getDate("updatedDateTime")
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

    public void deleteBusinessType(BusinessType businessType){
            String sql = "{call adminBusinessType.removeBusinessType(?)}}";
            try (Connection conn = DBConnection.getConnection()){
                CallableStatement cs = conn.prepareCall(sql);

                //Input parameters
                cs.setInt(1, businessType.getId());

                cs.execute();

            } catch (Exception e){
                System.out.println("Failed to connect to database: " + e.getMessage());
                e.printStackTrace();
            }

        }

        public void updateBusinessType(BusinessType businessType){
            String sql = "{call adminBusinessType.updateBusinessType(?, ?)}";

            try (Connection conn = DBConnection.getConnection()){
                CallableStatement cs = conn.prepareCall(sql);

                //Input parameters
                cs.setInt(1, businessType.getId());
                cs.setString(2, businessType.getDescription());

            } catch (Exception e){
                System.out.println("Failed to connect to database: " + e.getMessage());
                e.printStackTrace();
            }
        }
}