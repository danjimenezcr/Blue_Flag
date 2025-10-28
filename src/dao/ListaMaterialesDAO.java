import model.MaterialTypeXCollectionCenter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class MaterialTypeXCollectionCenterDAO {

    public List<MaterialTypeXCollectionCenter> getListaMateriales(String id) {
        String sql = "{ ? = call adminTMXCenter.getListaMateriales(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<MaterialTypeXCollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new MaterialTypeXCollectionCenter(rs.getInt("CollectionCenter"),
                                    rs.getInt("year"),
                                    rs.getInt("month"),
                                    rs.getInt("total_tipo_material"),
                                    rs.getInt("total_kilograms")
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