package dao;

import model.IdType;
import model.Labels;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class LabelDAO {

    public List<Labels> getLabels(Integer id) {
        String sql = "{ call ? := labelsManager.getLabels(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parámetro 
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parámetro
            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<Labels> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Labels(
                            rs.getInt("ID"),
                            rs.getString("DESCRIPTION"),
                            rs.getString("CREATEDBY"),
                            rs.getDate("CREATEDDATE"),
                            rs.getString("UPDATEDBY"),
                            rs.getDate("UPDATEDDATE")
                    ));
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

    public void addLabel(Labels labels) {
        String sql = "{call LABELSMANAGER.INSERTLABELS(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setString(1, labels.getDescription());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteLabel(Labels labels) {
        String sql = "{call LABELSMANAGER.DELETELABELS(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, labels.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateLabel(Labels labels) {
        String sql = "{call LABELSMANAGER.UPDATELABELS(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, labels.getId());
            cs.setString(2, labels.getDescription());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
