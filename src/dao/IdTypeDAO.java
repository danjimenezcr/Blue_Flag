 package dao;

import model.IdType;
import model.UserTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class IdTypeDAO {

    public List<IdType> getIdTypes(Integer id) {
        String sql = "{ call ? := idTypeManager.getIdTypes(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT cursor
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parámetro
            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<IdType> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new IdType(
                            rs.getInt("ID"),
                            rs.getString("NAME"),
                            rs.getString("CREATEDBY"),
                            rs.getDate("CREATEDDATE"),
                            rs.getString("UPDATEDBY"),
                            rs.getDate("UPDATEDDATE")
                    ));
                return list;         
            }

        } catch (Exception e){
            System.out.println("Error getting id types: " + e.getMessage());
        }
    } catch (SQLException e){
        System.out.println("Failed to connect to database! " + e.getMessage());
    }
        return null;
    }

    public void addIdType(IdType idType) {
        String sql = "{call IDTYPEMANAGER.INSERTIDTYPE(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setString(1, idType.getName());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteIdType(IdType idType) {
        String sql = "{call IDTYPEMANAGER.DELETEIDTYPE(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, idType.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateIdType(IdType idType) {
        String sql = "{call IDTYPESMANAGER.UPDATEIDTYPE(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, idType.getId());
            cs.setString(2, idType.getName());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
