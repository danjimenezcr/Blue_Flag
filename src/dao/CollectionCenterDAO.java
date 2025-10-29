package dao;

import model.CenterType;
import model.CollectionCenter;
import model.District;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class CollectionCenterDAO {

    public List<CollectionCenter> getCollectionCenter(Integer id, String name, Integer CenterTypeId, Integer districtId) {
        String sql = "{ ? = call adminCollectionCenter.getCollectionCenter(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setInt(2, id);
            cs.setString(3, name);
            cs.setInt(4, CenterTypeId);
            cs.setInt(5, districtId);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<CollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    CenterType centerType = new CenterTypeDAO().getCenterType(rs.getInt("CenterTypeId"), null).get(0);
                    District district = new DistrictDAO().getDistricts(rs.getInt("DISTRICTID"), null, null).get(0);
                    list.add( new CollectionCenter(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("OPENHOUR"),
                                    rs.getString("CLOSEHOUR"),
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

}