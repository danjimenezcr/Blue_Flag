package dao;

import model.Emails;
import model.Labels;
import model.Phones;
import model.User;
import util.DBConnection;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class EmailDAO {

    public List<Emails> getEmails(Integer userId, Integer emailId) {
        String sql = "{ call ? := EmailManager.getEmails(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            if(userId != null)  cs.setInt(2, userId);
            else cs.setNull(2, OracleTypes.INTEGER);
            if  (emailId != null) cs.setInt(3, emailId);
            else cs.setNull(3, OracleTypes.VARCHAR);

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

    public void addEmails(Emails emails) {
        String sql = "{call EMAILMANAGER.INSERTEMAIL(?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setInt(1, emails.getUser().getId());
            cs.setString(2, emails.getEmailAddress());
            cs.setInt(3, emails.getLabel().getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteEmails(Emails emails) {
        String sql = "{call EMAILMANAGER.DELETEEMAIL(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, emails.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateEmails(Emails emails) {
        String sql = "{call EMAILMANAGER.UPDATEEMAIL(?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters

            cs.setInt(1, emails.getId());
            cs.setString(2, emails.getEmailAddress());
            cs.setInt(3, emails.getLabel().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
