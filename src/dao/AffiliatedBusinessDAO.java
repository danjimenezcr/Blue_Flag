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


    public List<AffiliatedBusiness> getAffiliatedBusiness(Integer id, String name, Integer businessTypeid, Integer districtId) {
        String sql = "{ ? = call adminAffiliatedBusiness.getAffiliatedBusiness(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if(name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);

            if(businessTypeid != null) cs.setInt(4, businessTypeid);
            else cs.setNull(4, OracleTypes.VARCHAR);

            if(districtId != null) cs.setInt(5, districtId);
            else cs.setNull(5, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<AffiliatedBusiness> list = new ArrayList<>();
                while (rs.next()) {
                    District district = new DistrictDAO().getDistricts(rs.getInt("DISTRICTID"), null, null).get(0);
                    BusinessType businessType = new BusinessTypeDAO().getBusinessType(rs.getInt("BusinessTypeId"), null).get(0);
                    list.add( new AffiliatedBusiness(rs.getInt("id"),
                                    rs.getString("NAME"),
                                    rs.getTimestamp("OPENHOUR"),
                                    rs.getTimestamp("CLOSEHOUR"),
                                    rs.getString("MANAGER"),
                                    rs.getString("CONTACT"),
                                    district,
                                    businessType,
                                    rs.getString("CREATEDBY"),
                                    rs.getDate("CREATEDDATETIME"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATETIME")
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

    public static void main(String[] args) {

        System.out.println(new UserXCollectionCenterDAO().getTMXCenter(0, 0, 0, 0));
    }
}

