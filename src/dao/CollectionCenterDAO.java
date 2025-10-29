package dao;

import model.AffiliatedBusiness;
import model.CenterType;
import model.CollectionCenter;
import model.District;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class CollectionCenterDAO {

    public int addCollectionCenter(CollectionCenter collectionCenter) {
        String sql = "{ call adminCollectionCenter.insertCollectionCenter(?, ?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, collectionCenter.getName());
            cs.setTimestamp(2, collectionCenter.getOpenHour());
            cs.setTimestamp(3, collectionCenter.getCloseHour());
            cs.setString(4, collectionCenter.getManager());
            cs.setString(5, collectionCenter.getContact());
            cs.setInt(6, collectionCenter.getDistrict().getId());
            cs.setInt(7, collectionCenter.getCenterType().getId());
            cs.registerOutParameter(8, Types.INTEGER);

            cs.execute();

            int newId = cs.getInt(8);
            System.out.println("New id collection center: " + newId);
            return newId;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<CollectionCenter> getCollectionCenter(Integer id, String name, Integer CenterTypeId, Integer districtId) {
        String sql = "{ ? = call adminCollectionCenter.getCollectionCenter(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if (name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);

            if (CenterTypeId != null) cs.setInt(4, CenterTypeId);
            else cs.setNull(4, OracleTypes.INTEGER);

            if (districtId != null) cs.setInt(5, districtId);
            else cs.setNull(5, OracleTypes.INTEGER);


            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<CollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    CenterType centerType = new CenterTypeDAO().getCenterType(rs.getInt("CenterTypeId"), null).get(0);
                    District district = new DistrictDAO().getDistricts(rs.getInt("DISTRICTID"), null, null).get(0);
                    list.add( new CollectionCenter(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getTimestamp("OPENHOUR"),
                                    rs.getTimestamp("CLOSEHOUR"),
                                    rs.getString("MANAGER"),
                                    rs.getString("CONTACT"),
                                    centerType,
                                    rs.getString("CREATEDBY"),
                                    rs.getDate("CREATEDDATETIME"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATETIME"),
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

    public void deleteCollectionCenter(CollectionCenter collectionCenter) {
        String sql = "{call adminCollectionCenter.removeCollectionCenter(?)}}";
        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, collectionCenter.getAutorizedEntityId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void updateCollectionCenter(CollectionCenter collectionCenter){
        String sql = "{call adminCollectionCenter.updateCollectionCenter(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, collectionCenter.getAutorizedEntityId());
            cs.setString(2, collectionCenter.getName());
            cs.setTimestamp(3, collectionCenter.getOpenHour());
            cs.setTimestamp(4, collectionCenter.getCloseHour());
            cs.setString(5, collectionCenter.getManager());
            cs.setString(6, collectionCenter.getContact());
            cs.setInt(7, collectionCenter.getDistrict().getId());
            cs.setInt(8, collectionCenter.getCenterType().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}