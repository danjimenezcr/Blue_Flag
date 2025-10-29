package dao;

import model.Labels;
import model.Phones;
import model.User;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhonesDAO {

    public List<Phones> getPhones(Integer userId, Integer phoneId) {
        String sql = "{ call ? := PhoneManager.getPhones(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            if(userId != null)  cs.setInt(2, userId);
            else cs.setNull(2, OracleTypes.INTEGER);
            if  (phoneId != null) cs.setInt(3, phoneId);
            else cs.setNull(3, OracleTypes.VARCHAR);
            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {

                List<Phones> list = new ArrayList<>();
                while (rs.next()) {
                    Labels label = new LabelDAO().getLabels(rs.getInt("labelId")).get(0);
                    User user = new UsersDAO().getUsers(rs.getInt("userId"), null, null, null, null, null, null).get(0);
                    list.add(new Phones(
                            rs.getInt("id"),
                            rs.getInt("phoneNumber"),
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

    public void addPhone(Phones phones) {
        String sql = "{call PHONESMANAGER.INSERTPHONES(?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setInt(1, phones.getUserId().getId());
            cs.setInt(2, phones.getPhoneNumber());
            cs.setInt(3, phones.getLabels().getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletePhone(Phones phones) {
        String sql = "{call PHONESMANAGER.DELETEPHONES(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, phones.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updatePhone(Phones phones) {
        String sql = "{call PHONESMANAGER.UPDATEPHONES(?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setInt(1, phones.getId());
            cs.setInt(2, phones.getPhoneNumber());
            cs.setInt(3, phones.getLabels().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
