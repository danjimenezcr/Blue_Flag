import model.MaterialTypeXCollectionCenter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class MaterialTypeXCollectionCenterDAO {

    public List<MaterialTypeXCollectionCenter> getTMXCenter(String id, String AutorizedEntityid, String MaterialTypeid) {
        String sql = "{ ? = call adminTMXCenter.getTMXCenter(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, AutorizedEntityid);
            cs.setString(4, MaterialTypeid);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<MaterialTypeXCollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new MaterialTypeXCollectionCenter(rs.getInt("id"),
                                    rs.getInt("AutorizedEntityid"),
                                    rs.getString(" CREATEDBY"),
                                    rs.getDate("CREATEDDATE"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATE"),
                                    rs.getString("CollectionCenter"),
                                    rs.getString("materialType")
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