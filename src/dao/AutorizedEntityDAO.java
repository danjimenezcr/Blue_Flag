package dao;

import model.AutorizedEntity;
import model.District;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class AutorizedEntityDAO {

    public void addAutorizedEntity(AutorizedEntity autorizedEntity) {
        String sql = "{call ADMINAUTORIZEDENTITY.INSERTAUTORIZEDENTITY(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setString(1, autorizedEntity.getName());
            cs.setTimestamp(2, autorizedEntity.getOpenHour());
            cs.setTimestamp(3, autorizedEntity.getCloseHour());
            cs.setString(4, autorizedEntity.getManager());
            cs.setString(5, autorizedEntity.getContact());
            cs.setInt(6, autorizedEntity.getDistrict().getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public List<AutorizedEntity> getAutorizedEntity(Integer id, String name, String manager, Integer districtId) {
        String sql = "{ ? = call adminAutorizedEntity.getAutorizedEntity(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if(name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);

            if(manager != null) cs.setString(4, manager);
            else cs.setNull(4, OracleTypes.VARCHAR);

            if(districtId != null) cs.setInt(5, districtId);
            else cs.setNull(4, OracleTypes.INTEGER);

            System.out.println(cs);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<AutorizedEntity> list = new ArrayList<>();
                while (rs.next()) {
                    District district = new DistrictDAO().getDistricts(rs.getInt("DISTRICTID"), null, null).get(0);
                    list.add( new AutorizedEntity(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getTimestamp("openHour"),
                                    rs.getTimestamp("closeHour"),
                                    rs.getString("manager"),
                                    rs.getString("contact"),
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

public void deleteAutorizedEntity(AutorizedEntity autorizedEntity){
        String sql = "{call ADMINAUTORIZEDENTITY.INSERTAUTORIZEDENTITY(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, autorizedEntity.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

public void updateAutorizedEntity(AutorizedEntity autorizedEntity){
        String sql = "{call ADMINAUTORIZEDENTITY.UPDATEAUTORIZEDENTITY(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, autorizedEntity.getId());
            cs.setString(2, autorizedEntity.getName());
            cs.setTimestamp(3, autorizedEntity.getOpenHour());
            cs.setTimestamp(4, autorizedEntity.getCloseHour());
            cs.setString(5, autorizedEntity.getManager());
            cs.setString(6, autorizedEntity.getContact());
            cs.setInt(7, autorizedEntity.getDistrict().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}