package dao;

import model.AffiliatedBusiness;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class AffiliatedBusinessDAO {

    public int addAffiliatedBusiness(AffiliatedBusiness affiliatedBusiness) {
        String sql = "{ call adminAffiliatedBusiness.insertAffiliatedBusiness(?, ?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, affiliatedBusiness.getName());
            cs.setTimestamp(2, affiliatedBusiness.getOpenHour());
            cs.setTimestamp(3, affiliatedBusiness.getCloseHour());
            cs.setString(4, affiliatedBusiness.getManager());
            cs.setString(5, affiliatedBusiness.getContact());
            cs.setInt(6, affiliatedBusiness.getDistrictId().getId());
            cs.setInt(7, affiliatedBusiness.getBusinessTypeId().getId());
            cs.registerOutParameter(8, Types.INTEGER);

            cs.execute();

            int newId = cs.getInt(8);
            System.out.println("Nuevo ID de entidad afiliada: " + newId);
            return newId;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public List<AffiliatedBusiness> getAffiliatedBusiness(String id, String name, String BusinessType, String districtId) {
        String sql = "{ ? = call adminAffiliatedBusiness.getAffiliatedBusiness(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, name);
            cs.setString(4, BusinessType);
            cs.setString(5, districtId);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<AffiliatedBusiness> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new AffiliatedBusiness(rs.getInt("id"),
                                    rs.getString("NAME"),
                                    rs.getString("OPENHOUR"),
                                    rs.getString("CLOSEHOUR"),
                                    rs.getString("MANAGER"),
                                    rs.getString("CONTACT"),
                                    rs.getString("districtName"),
                                    rs.getInt("BusinessTypeId")
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

        public void deleteAffiliatedBusiness(AffiliatedBusiness affiliatedBusiness) {
            String sql = "{call ADMINAFFILIATEDBUSINESS.REMOVEAFFILIATEDBUSINESS(?)}}";
            try (Connection conn = DBConnection.getConnection()) {
                CallableStatement cs = conn.prepareCall(sql);

                //Input parameters
                cs.setInt(1, affiliatedBusiness.getAutorizedEntityId());

                cs.execute();

            } catch (Exception e) {
                System.out.println("Failed to connect to database: " + e.getMessage());
                e.printStackTrace();
            }
        }


        public void updateAffiliatedBusiness(AffiliatedBusiness affiliatedBusiness){
            String sql = "{call ADMINAFFILIATEDBUSINESS.UPDATEAFFILIATEDBUSINESS(?, ?, ?, ?, ?, ?, ?, ?)}";

            try (Connection conn = DBConnection.getConnection()){
                CallableStatement cs = conn.prepareCall(sql);

                //Input parameters
                cs.setInt(1, affiliatedBusiness.getAutorizedEntityId());
                cs.setString(2, affiliatedBusiness.getName());
                cs.setTimestamp(3, affiliatedBusiness.getOpenHour());
                cs.setTimestamp(4, affiliatedBusiness.getCloseHour());
                cs.setString(5, affiliatedBusiness.getManager());
                cs.setString(6, affiliatedBusiness.getContact());
                cs.setInt(7, affiliatedBusiness.getDistrictId().getId());
                cs.setInt(8, affiliatedBusiness.getBusinessTypeId().getId());

            } catch (Exception e){
                System.out.println("Failed to connect to database: " + e.getMessage());
                e.printStackTrace();
            }
        }
}

