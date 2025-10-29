package dao;

import model.Emails;
import model.Labels;
import model.User;
import util.DBConnection;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class EmailDAO {

    public List<Emails> getEmails(String userId, String emailId) {
        String sql = "{ call ? := EmailManager.getEmails(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            cs.setString(2, userId);  
            cs.setString(3, emailId);  

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<Emails> list = new ArrayList<>();
                while (rs.next()) {
                    User user = new UsersDAO().getUsers(rs.getInt("userId"), null, null, null,null, null, null).get(0);
                    Labels label = new LabelDAO().getLabels(rs.getInt("labelId")).get(0);
                    list.add(new Emails(
                            rs.getInt("id"),
                            rs.getString("emailAddress"),
                            rs.getString("createdBy"),
                            rs.getDate("createdDate"),
                            rs.getString("updatedBy"),
                            rs.getDate("updatedDate"),
                            label,
                            user
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
