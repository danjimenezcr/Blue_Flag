package dao;

import model.CenterType;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class CenterTypeDAO {

    public int addCenterType(CenterType centerType) {
        String sql = "{ call adminCenterType.insertCenterType(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, centerType.getDescription());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public List<CenterType> getCenterType(Integer id, String description) {
        String sql = "{ ? = call adminCenterType.getCenterType(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if(description != null) cs.setString(3, description);
            else cs.setNull(3, OracleTypes.VARCHAR);

            //Parameters Input
            cs.setInt(2, id);
            cs.setString(3, description);
            System.out.println(cs);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<CenterType> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new CenterType(rs.getInt("id"),
                                    rs.getString("description"),
                                    rs.getString("createdBy"),
                                    rs.getDate("createdDateTime"),
                                    rs.getString("updatedBy"),
                                    rs.getDate("updatedDateTime")
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

    public void deleteCenterType(CenterType centerType){
        String sql = "{call adminCenterType.removeCenterType(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, centerType.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateCenterType(CenterType centerType){
        String sql = "{call adminCenterType.updateCenterType(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, centerType.getId());
            cs.setString(2, centerType.getDescription());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}