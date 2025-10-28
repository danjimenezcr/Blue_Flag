import model.AutorizedEntity;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class AutorizedEntityDAO {

    public List<AutorizedEntity> getAutorizedEntity(String id, String name, String manager, String districtId) {
        String sql = "{ ? = call adminAutorizedEntity.getAutorizedEntity(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, name);
            cs.setString(4, manager);
            cs.setString(5, districtId);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<AutorizedEntity> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new AutorizedEntity(rs.getInt("id"),
                                    rs.getString("NAME"),
                                    rs.getString("OPENHOUR"),
                                    rs.getString("CLOSEHOUR"),
                                    rs.getString("MANAGER"),
                                    rs.getString("CONTACT"),
                                    rs.getString("districtName")
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