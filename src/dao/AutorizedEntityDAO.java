import model.AutorizedEntity;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class AutorizedEntityDAO {

    public int insertAutorizedEntity(String name, String openHour, String closeHour,
                                     String manager, String contact, String districtId) {
        String sql = "{ call adminAutorizedEntity.insertAutorizedEntity(?, ?, ?, ?, ?, ?, ?) }";
        int newId = -1;

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            // 🔹 1. Parámetros de entrada
            cs.setString(1, name);
            cs.setTimestamp(2, Timestamp.valueOf(openHour));  
            cs.setTimestamp(3, Timestamp.valueOf(closeHour));
            cs.setString(4, manager);
            cs.setString(5, contact);
            cs.setInt(6, Integer.parseInt(districtId));

            cs.registerOutParameter(7, Types.NUMERIC);

            System.out.println("Execution: " + cs.toString());
            cs.execute();

            newId = cs.getInt(7);
            System.out.println(" insert register with ID: " + newId);

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Incorrect time format, use 'YYYY-MM-DD HH:MI:SS");
        }

        return newId;
    }

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
                                    rs.getString("name"),
                                    rs.getString("openHour"),
                                    rs.getString("closeHour"),
                                    rs.getString("manager"),
                                    rs.getString("contact"),
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