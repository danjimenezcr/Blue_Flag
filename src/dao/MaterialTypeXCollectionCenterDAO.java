package dao;

import model.AutorizedEntity;
import model.CenterType;
import model.MaterialType;
import model.MaterialTypeXCollectionCenter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

import javax.swing.table.DefaultTableModel;


public class MaterialTypeXCollectionCenterDAO {

    public int addMaterialTypeXCollectionCenter(MaterialTypeXCollectionCenter materialTypeXCollectionCenter) {
        String sql = "{ call adminTMXCenter.insertTMXCenter(?,?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, materialTypeXCollectionCenter.getAuthorizedEntity().getId());
            cs.setInt(2, materialTypeXCollectionCenter.getMaterialType().getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public List<MaterialTypeXCollectionCenter> getTMXCenter(Integer id, Integer authorizedEntityId, Integer materialTypeId) {
        String sql = "{ ? = call adminTMXCenter.getTMXCenter(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if (authorizedEntityId != null) cs.setInt(3, authorizedEntityId);
            else cs.setNull(3, OracleTypes.INTEGER);

            if (materialTypeId!= null) cs.setInt(4, materialTypeId);
            else cs.setNull(4, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<MaterialTypeXCollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    AutorizedEntity autorizedEntity = new AutorizedEntityDAO().getAutorizedEntity(rs.getInt("AutorizedEntityid"), null, null, null).get(0);
                    MaterialType materialType = new MaterialTypeDAO().getMaterialType(rs.getInt("materialTypeId"), null).get(0);

                    list.add( new MaterialTypeXCollectionCenter(rs.getInt("id"),
                                    autorizedEntity,
                                    rs.getString("CREATEDBY"),
                                    rs.getDate("CREATEDDATETIME"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATETIME"),
                                    materialType
                            )
                    );

                }
                return list;

            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
        }
        return null;
    }

    public void deleteMaterialTypeXCollectionCenter(MaterialTypeXCollectionCenter materialTypeXCollectionCenter){
        String sql = "{call adminTMXCenter.removeTMXCenter(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, materialTypeXCollectionCenter.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateMaterialTypeXCollectionCenter(MaterialTypeXCollectionCenter materialTypeXCollectionCenter){
        String sql = "{call adminTMXCenter.removeTMXCenter(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, materialTypeXCollectionCenter.getId());
            cs.setInt(2, materialTypeXCollectionCenter.getAuthorizedEntity().getId());
            cs.setInt(3, materialTypeXCollectionCenter.getMaterialType().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public DefaultTableModel getListaMateriales(Integer id) {
        String sql = "{ ? = call adminTMXCenter.getListaMateriales(?) }";
        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{"Collection Center", "Year", "Month", "Total Material Type", "Total Kilograms"});
        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            if (id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<MaterialTypeXCollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("CollectionCenter"),
                            rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getInt("total_tipo_material"),
                            rs.getInt("total_kilograms")
                    };
                    model.addRow(row);
                }
                return model;

            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
        }
        return null;
    }
}