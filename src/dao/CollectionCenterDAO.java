import model.CollectionCenter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class CollectionCenterDAO {

    public List<CollectionCenter> getCollectionCenter(String id, String name, String CenterTypeId, String districtId) {
        String sql = "{ ? = call adminCollectionCenter.getCollectionCenter(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, name);
            cs.setString(4, CenterTypeId);
            cs.setString(5, districtId);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<CollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new CollectionCenter(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("OPENHOUR"),
                                    rs.getString("CLOSEHOUR"),
                                    rs.getString("MANAGER"),
                                    rs.getString("CONTACT"),
                                    rs.getString("districtName"),
                                    rs.getInt("CenterTypeId")
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