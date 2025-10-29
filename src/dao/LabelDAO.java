package dao;

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
            if (id == null) {
                cs.setNull(2, Types.NUMERIC);
            } else {
                cs.setInt(2, id);
            }

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
}
