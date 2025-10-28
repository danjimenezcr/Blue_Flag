import model.UserXCollectionCenter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class UserXCollectionCenterDAO {

    public List<UserXCollectionCenter> getTMXCenter(String id, String Userid, String CollectionCenter, String PointsConvertionKey) {
        String sql = "{ ? = call adminUserXCollectionCenter.getUserXCollectionCenter(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, Userid);
            cs.setString(4, CollectionCenter);
            cs.setString(5, PointsConvertionKey);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<UserXCollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new UserXCollectionCenter(rs.getInt("id"),
                                    rs.getInt("kilograms"),
                                    rs.getString(" CREATEDBY"),
                                    rs.getDate("CREATEDDATE"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATE"),
                                    rs.getInt("Points"),
                                    rs.getString("Material"),
                                    rs.getString("CollectionCenter"),
                                    rs.getString("User")

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