package dao;

import model.MaterialType;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class MaterialTypeDAO {

    public int addMaterialType(MaterialType materialType) {
        String sql = "{ call adminMaterialType.insertMaterialType(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, materialType.getName());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public List<MaterialType> getMaterialType(Integer id, String name) {
        String sql = "{ ? = call adminMaterialType.getMaterialType(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if (name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);


            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<MaterialType> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new MaterialType(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("createdBy"),
                                    rs.getDate("createdDateTime"),
                                    rs.getString("updatedBy"),
                                    rs.getDate("updatedDateTime")
                            )

                    );
                }
                    return list;
                } catch(Exception e){
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                System.out.println("Failed to connect to database! " + e.getMessage());
            }
            return null;
    }

    public void deleteMaterialType(MaterialType materialType){
        String sql = "{call adminMaterialType.removeMaterialType(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, materialType.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateMaterialType(MaterialType materialType){
        String sql = "{call adminCenterType.updateCenterType(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, materialType.getId());
            cs.setString(2, materialType.getName());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        System.out.println(new MaterialTypeDAO().getMaterialType(0,null));
    }
}

