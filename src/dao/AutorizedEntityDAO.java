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

    public List<AutorizedEntity> getAutorizedEntity(Integer id, String name, String manager, Intege districtId) {
        String sql = "{ ? = call adminAutorizedEntity.getAutorizedEntity(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setInt(2, id);
            cs.setString(3, name);
            cs.setString(4, manager);
            cs.setInt(5, districtId);
            System.out.println(cs.toString());

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


}