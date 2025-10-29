package dao;

import model.AutorizedEntity;
import model.MaterialType;
import model.MaterialTypeXCollectionCenter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class MaterialTypeXCollectionCenterDAO {

    public List<MaterialTypeXCollectionCenter> getListaMateriales(Integer id, Integer authorizedEntityId, Integer materialTypeId) {
        String sql = "{ ? = call adminTMXCenter.getTMXCenter(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setInt(1, id);
            cs.setInt(2, authorizedEntityId);
            cs.setInt(3, materialTypeId);

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

}